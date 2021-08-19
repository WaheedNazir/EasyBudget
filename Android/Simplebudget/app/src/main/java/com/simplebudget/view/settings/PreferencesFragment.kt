/*
 *   Copyright 2021 Benoit LETONDOR
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.simplebudget.view.settings

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.simplebudget.iab.Iab
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.roomorama.caldroid.CaldroidFragment
import com.simplemobiletools.commons.dialogs.SecurityDialog
import com.simplemobiletools.commons.helpers.SHOW_PIN
import com.simplebudget.BuildConfig
import com.simplebudget.R
import com.simplebudget.helper.CurrencyHelper
import com.simplebudget.helper.Logger
import com.simplebudget.helper.getUserCurrency
import com.simplebudget.prefs.*
import com.simplebudget.view.RatingPopup
import com.simplebudget.view.premium.PremiumActivity
import com.simplebudget.view.selectcurrency.SelectCurrencyFragment
import com.simplebudget.view.settings.SettingsActivity.Companion.SHOW_BACKUP_INTENT_KEY
import com.simplebudget.view.settings.backup.BackupSettingsActivity
import com.simplebudget.view.settings.openSource.OpenSourceDisclaimerActivity
import com.simplebudget.view.settings.releaseHistory.ReleaseHistoryTimelineActivity
import org.koin.android.ext.android.inject

/**
 * Fragment to display preferences
 *
 * @author Benoit LETONDOR
 */
class PreferencesFragment : PreferenceFragmentCompat() {

    /**
     * The dialog to select a new currency (will be null if not shown)
     */
    private var selectCurrencyDialog: SelectCurrencyFragment? = null

    /**
     * Broadcast receiver (used for currency selection)
     */
    private lateinit var receiver: BroadcastReceiver


    /**
     *
     */
    private val iab: Iab by inject()

    private val appPreferences: AppPreferences by inject()


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * App version
         */
        val prefAppVersion: Preference =
            findPreference<Preference>(resources.getString(R.string.setting_app_version_key))!!
        prefAppVersion.title = getString(R.string.setting_app_version)
        prefAppVersion.summary = String.format(
            "%d(%s)",
            BuildConfig.VERSION_CODE,
            BuildConfig.VERSION_NAME
        )
        /*
         * Rating button
         */
        findPreference<Preference>(resources.getString(R.string.setting_category_rate_button_key))?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                activity?.let { activity ->
                    RatingPopup(activity, appPreferences).show(true)
                }
                false
            }

        /*
         * Start day of week
         */
        val firstDayOfWeekPref =
            findPreference<Preference>(getString(R.string.setting_category_start_day_of_week_key))
        firstDayOfWeekPref?.summary = resources.getString(
            R.string.setting_category_start_day_of_week_summary,
            getWeekDaysName(appPreferences.getCaldroidFirstDayOfWeek())
        )
        firstDayOfWeekPref?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val weeks = arrayOf(
                "SUNDAY",
                "MONDAY",
                "TUESDAY",
                "WEDNESDAY",
                "THURSDAY",
                "FRIDAY",
                "SATURDAY"
            )
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.setting_category_start_day_of_week_title))
                .setItems(weeks) { dialog, which ->
                    appPreferences.setCaldroidFirstDayOfWeek(getWeekDayId(weeks[which]))
                    firstDayOfWeekPref?.summary = resources.getString(
                        R.string.setting_category_start_day_of_week_summary,
                        getWeekDaysName(appPreferences.getCaldroidFirstDayOfWeek())
                    )
                    dialog.dismiss()
                }
                .show()
            true
        }

        /*
         * Full app lock
         */
        val enableAppPasswordProtection =
            findPreference<Preference>(getString(R.string.setting_enable_app_protection_key))
        enableAppPasswordProtection?.summary =
            String.format("%s", if (appPreferences.isAppPasswordProtectionOn()) "ON" else "OFF")
        enableAppPasswordProtection?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {

                if (!iab.isUserPremium()) (activity as SettingsActivity).loadInterstitial()

                val tabToShow =
                    if (appPreferences.isAppPasswordProtectionOn()) appPreferences.hiddenProtectionType() else SHOW_PIN
                SecurityDialog(
                    requireActivity(),
                    appPreferences.appPasswordHash(),
                    tabToShow
                ) { hash, type, success ->
                    if (success) {
                        val hasPasswordProtection = appPreferences.isAppPasswordProtectionOn()
                        enableAppPasswordProtection?.summary =
                            String.format("%s", if (!hasPasswordProtection) "ON" else "OFF")
                        appPreferences.setAppPasswordProtectionOn(!hasPasswordProtection)
                        appPreferences.setAppPasswordHash((if (hasPasswordProtection) "" else hash))
                        appPreferences.setHiddenProtectionType(type)

                        if (!hasPasswordProtection) {
                            displayPasswordProtectionDisclaimer()
                        }
                    }
                }
                true
            }


        /*
         * Backup
         */
        findPreference<Preference>(getString(R.string.setting_category_backup))?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                startActivity(Intent(context, BackupSettingsActivity::class.java))
                false
            }
        updateBackupPreferences()

        /*
         * Share app
         */
        findPreference<Preference>(resources.getString(R.string.setting_category_share_app_key))?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                try {
                    val sendIntent = Intent()
                    sendIntent.action = Intent.ACTION_SEND
                    sendIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        resources.getString(R.string.app_invite_message) + "\n" + "https://play.google.com/store/apps/details?id=gplx.simple.budgetapp"
                    )
                    sendIntent.type = "text/plain"
                    startActivity(sendIntent)
                } catch (e: Exception) {
                    Logger.error("An error occurred during sharing app activity start", e)
                }

                false
            }

        /*
         * App version click
         */
        findPreference<Preference>(resources.getString(R.string.setting_app_version_key))?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                val sendIntent =
                    Intent(requireActivity(), ReleaseHistoryTimelineActivity::class.java)
                startActivity(sendIntent)
                false
            }
        /*
         * Open copyright disclaimer
         */
        findPreference<Preference>(resources.getString(R.string.setting_app_open_source_key))?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                val sendIntent =
                    Intent(requireActivity(), OpenSourceDisclaimerActivity::class.java)
                startActivity(sendIntent)
                false
            }

        /*
         * Currency change button
         */
        findPreference<Preference>(resources.getString(R.string.setting_category_currency_change_button_key))?.let { currencyPreference ->
            currencyPreference.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                selectCurrencyDialog = SelectCurrencyFragment()
                selectCurrencyDialog!!.show(
                    (activity as SettingsActivity).supportFragmentManager,
                    "SelectCurrency"
                )

                false
            }
            setCurrencyPreferenceTitle(currencyPreference)
        }


        /*
         * Warning limit button
         */
        findPreference<Preference>(resources.getString(R.string.setting_category_limit_set_button_key))?.let { limitWarningPreference ->
            limitWarningPreference.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    val dialogView =
                        activity?.layoutInflater?.inflate(R.layout.dialog_set_warning_limit, null)
                    val limitEditText =
                        dialogView?.findViewById<View>(R.id.warning_limit) as EditText
                    limitEditText.setText(appPreferences.getLowMoneyWarningAmount().toString())
                    limitEditText.setSelection(limitEditText.text.length) // Put focus at the end of the text

                    context?.let { context ->
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle(R.string.adjust_limit_warning_title)
                        builder.setMessage(R.string.adjust_limit_warning_message)
                        builder.setView(dialogView)
                        builder.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
                        builder.setPositiveButton(R.string.ok) { _, _ ->
                            var limitString = limitEditText.text.toString()
                            if (limitString.trim { it <= ' ' }.isEmpty()) {
                                limitString =
                                    "0" // Set a 0 value if no value is provided (will lead to an error displayed to the user)
                            }

                            try {
                                val newLimit = Integer.valueOf(limitString)

                                // Invalid value, alert the user
                                if (newLimit <= 0) {
                                    throw IllegalArgumentException("limit should be > 0")
                                }

                                appPreferences.setLowMoneyWarningAmount(newLimit)
                                setLimitWarningPreferenceTitle(limitWarningPreference)
                            } catch (e: Exception) {
                                AlertDialog.Builder(context)
                                    .setTitle(R.string.adjust_limit_warning_error_title)
                                    .setMessage(resources.getString(R.string.adjust_limit_warning_error_message))
                                    .setPositiveButton(R.string.ok) { dialog1, _ -> dialog1.dismiss() }
                                    .show()
                            }
                        }

                        val dialog = builder.show()

                        // Directly show keyboard when the dialog pops
                        limitEditText.onFocusChangeListener =
                            View.OnFocusChangeListener { _, hasFocus ->
                                // Check if the device doesn't have a physical keyboard
                                if (hasFocus && resources.configuration.keyboard == Configuration.KEYBOARD_NOKEYS) {
                                    dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                                }
                            }
                    }

                    false
                }

            setLimitWarningPreferenceTitle(limitWarningPreference)

            /*
             * Remove Ads button, Premium
            */
            refreshPremiumPreference()
        }

        /*
         * Notifications
         */
        val updateNotifPref =
            findPreference<CheckBoxPreference>(resources.getString(R.string.setting_category_notifications_update_key))
        updateNotifPref?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            appPreferences.setUserAllowUpdatePushes((it as CheckBoxPreference).isChecked)
            true
        }
        updateNotifPref?.isChecked = appPreferences.isUserAllowingUpdatePushes()


        /*
         * Broadcast receiver
         */
        val filter = IntentFilter(SelectCurrencyFragment.CURRENCY_SELECTED_INTENT)
        receiver =
            object : BroadcastReceiver() {
                override fun onReceive(appContext: Context, intent: Intent) {
                    if (SelectCurrencyFragment.CURRENCY_SELECTED_INTENT == intent.action && selectCurrencyDialog != null) {
                        findPreference<Preference>(resources.getString(R.string.setting_category_currency_change_button_key))?.let { currencyPreference ->
                            setCurrencyPreferenceTitle(currencyPreference)
                        }

                        selectCurrencyDialog!!.dismiss()
                        selectCurrencyDialog = null
                    }
                }
            }

        context?.let { context ->
            LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter)
        }

        /*
         * Check if we should show backup options
         */
        if (activity?.intent?.getBooleanExtra(SHOW_BACKUP_INTENT_KEY, false) == true) {
            activity?.intent?.putExtra(SHOW_BACKUP_INTENT_KEY, false)
            startActivity(Intent(context, BackupSettingsActivity::class.java))
        }
    }

    /**
     *
     */
    private fun updateBackupPreferences() {
        findPreference<Preference>(getString(R.string.setting_category_backup))?.setSummary(
            if (appPreferences.isBackupEnabled()) {
                R.string.backup_settings_backups_activated
            } else {
                R.string.backup_settings_backups_deactivated
            }
        )
    }

    /**
     *
     */
    override fun onResume() {
        super.onResume()
        updateBackupPreferences()
    }

    /**
     * Set the currency preference title according to selected currency
     *
     * @param currencyPreference
     */
    private fun setCurrencyPreferenceTitle(currencyPreference: Preference) {
        currencyPreference.title = resources.getString(
            R.string.setting_category_currency_change_button_title,
            appPreferences.getUserCurrency().symbol
        )
    }

    /**
     * Set the limit warning preference title according to the selected limit
     *
     * @param limitWarningPreferenceTitle
     */
    private fun setLimitWarningPreferenceTitle(limitWarningPreferenceTitle: Preference) {
        limitWarningPreferenceTitle.title = resources.getString(
            R.string.setting_category_limit_set_button_title,
            CurrencyHelper.getFormattedCurrencyString(
                appPreferences,
                appPreferences.getLowMoneyWarningAmount().toDouble()
            )
        )
    }

    override fun onDestroy() {
        context?.let { context ->
            LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver)
        }
        super.onDestroy()
    }


    /**
     * Show the right premium preference depending on the user state
     */
    private fun refreshPremiumPreference() {
        val isPremium = iab.isUserPremium()
        val pref: Preference =
            findPreference<Preference>(resources.getString(R.string.setting_category_premium_key))!!
        if (isPremium) {
            pref.title = getString(R.string.setting_category_premium_status_title)
            pref.summary = getString(R.string.setting_category_premium_status_message)

        } else {
            pref.title = getString(R.string.setting_category_not_premium_status_title)
            pref.summary = getString(R.string.setting_category_not_premium_status_message)
            pref.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    showBecomePremiumDialog()
                    false
                }
        }
    }

    private fun showBecomePremiumDialog() {
        // Launch in app here
        startActivity(Intent(context, PremiumActivity::class.java))
    }

    /**
     *
     */
    fun getWeekDaysName(weekDay: Int): String {
        return when (weekDay) {
            CaldroidFragment.SUNDAY -> "SUNDAY"
            CaldroidFragment.MONDAY -> "MONDAY"
            CaldroidFragment.TUESDAY -> "TUESDAY"
            CaldroidFragment.WEDNESDAY -> "WEDNESDAY"
            CaldroidFragment.THURSDAY -> "THURSDAY"
            CaldroidFragment.FRIDAY -> "FRIDAY"
            CaldroidFragment.SATURDAY -> "SATURDAY"
            else -> "MONDAY"
        }
    }

    /**
     *
     */
    private fun getWeekDayId(weekDay: String): Int {
        return when (weekDay) {
            "SUNDAY" -> CaldroidFragment.SUNDAY
            "MONDAY" -> CaldroidFragment.MONDAY
            "TUESDAY" -> CaldroidFragment.TUESDAY
            "WEDNESDAY" -> CaldroidFragment.WEDNESDAY
            "THURSDAY" -> CaldroidFragment.THURSDAY
            "FRIDAY" -> CaldroidFragment.FRIDAY
            "SATURDAY" -> CaldroidFragment.SATURDAY
            else -> CaldroidFragment.MONDAY
        }
    }

    /**
     *
     */
    fun displayPasswordProtectionDisclaimer() {
        val builder = AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setOnDismissListener { if (!iab.isUserPremium()) (activity as SettingsActivity).showInterstitial() }
            .setTitle("App password successfully set!")
            .setMessage("Note: If you forgot your password just clear or re-install app.\n\nYou can't reset your password.That's why it is recommended to enable backup so that your data would be saved in case of reset.\n\nThank you")
            .setPositiveButton("Got it!") { _, _ ->
            }
        builder.create().show()
    }
}