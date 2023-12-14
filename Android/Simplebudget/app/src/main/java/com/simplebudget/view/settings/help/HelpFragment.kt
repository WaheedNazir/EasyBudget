/*
 *   Copyright 2023 Benoit LETONDOR
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
package com.simplebudget.view.settings.help

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.simplebudget.R
import com.simplebudget.helper.*
import com.simplebudget.helper.extensions.getTelegramIntent
import com.simplebudget.view.settings.faq.FAQActivity


/**
 * Fragment to display Help preferences
 *
 * @author Waheed Nazir
 */
class HelpFragment : PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.help_preferences, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
         * Telegram channel button
         */
        findPreference<Preference>(resources.getString(R.string.setting_telegram_channel_key))?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                activity?.let {
                    startActivity(Intent().getTelegramIntent(it))
                }
                false
            }

        /*
         * FAQ app
         */
        findPreference<Preference>(resources.getString(R.string.setting_category_faq_key))?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                startActivity(Intent(context, FAQActivity::class.java))
                false
            }

        /*
         * Feedback of app
         */
        findPreference<Preference>(resources.getString(R.string.setting_category_feedback_app_key))?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                context?.let { context -> Feedback.askForFeedback(context) }
                false
            }

    }
}