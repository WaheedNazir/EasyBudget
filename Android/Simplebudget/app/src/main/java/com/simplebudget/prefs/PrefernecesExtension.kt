/*
 *   Copyright 2024 Benoit LETONDOR
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
package com.simplebudget.prefs

import com.roomorama.caldroid.CaldroidFragment
import com.simplebudget.helper.*
import com.simplebudget.model.account.AccountType
import java.time.LocalDate
import java.util.*

private const val DEFAULT_LOW_MONEY_WARNING_AMOUNT = 100

/**
 * Balance place holder
 */
public const val BALANCE_PLACE_HOLDER = "---"

/**
 * Date of the base balance set-up (long)
 */
private const val INIT_DATE_PARAMETERS_KEY = "init_date"

/**
 * Local identifier of the device (generated on first launch) (string)
 */
private const val LOCAL_ID_PARAMETERS_KEY = "local_id"

/**
 * Number of time different day has been open (int)
 */
private const val NUMBER_OF_DAILY_OPEN_PARAMETERS_KEY = "number_of_daily_open"

/**
 * Timestamp that indicates the last time user was presented the rating popup (long)
 */
private const val RATING_POPUP_LAST_AUTO_SHOW_PARAMETERS_KEY = "rating_popup_last_auto_show"

/**
 * App version stored to detect updates (int)
 */
private const val APP_VERSION_PARAMETERS_KEY = "appversion"

/**
 * Number of time the app has been opened (int)
 */
private const val NUMBER_OF_OPEN_PARAMETERS_KEY = "number_of_open"

/**
 * Timestamp of the last open (long)
 */
private const val LAST_OPEN_DATE_PARAMETERS_KEY = "last_open_date"

/**
 * Warning limit for low money on account (int)
 */
private const val LOW_MONEY_WARNING_AMOUNT_PARAMETERS_KEY = "low_money_warning_amount"

/**
 * First day of week to show (int)
 */
private const val FIRST_DAY_OF_WEEK_PARAMETERS_KEY = "first_day_of_week"

/**
 * The user wants to receive notifications for updates (bool)
 */
private const val USER_ALLOW_UPDATE_PUSH_PARAMETERS_KEY = "user_allow_update_push"

/**
 * The user wants to receive a monthly reminder notification when report is available (bool)
 */
private const val USER_ALLOW_MONTHLY_PUSH_PARAMETERS_KEY = "user_allow_monthly_push"

/**
 * Indicate if the rating has been completed by the user (finished or not ask me again) (bool)
 */
private const val RATING_COMPLETED_PARAMETERS_KEY = "rating_completed"

/**
 * Has the user saw the monthly report hint (bool)
 */
private const val USER_SAW_MONTHLY_REPORT_HINT_PARAMETERS_KEY =
    "user_saw_monthly_report_hint_updated"


/**
 * Break down hint
 */
private const val USER_SAW_BREAK_DOWN_HINT_PARAMETERS_KEY = "user_saw_break_down_hint"

/**
 * Backup hint
 */
private const val USER_SAW_BACKUP_HINT_PARAMETERS_KEY = "backup_hint"

/**
 * Search hint
 */
private const val USER_SAW_SEARCH_HINT_PARAMETERS_KEY = "user_saw_search_hint"

/**
 * Help hint
 */
private const val USER_SAW_HELP_HINT_PARAMETERS_KEY = "user_saw_help_hint"

/**
 * Settings Hint
 */
private const val USER_SAW_SETTINGS_HINT_PARAMETERS_KEY = "user_saw_settings_hint"

/**
 * Privacy Hint
 */
private const val USER_SAW_PRIVACY_HINT_PARAMETERS_KEY = "user_saw_privacy_hint"

/**
 * Hide Balance
 */
private const val USER_SAW_HIDE_BALANCE_HINT_PARAMETERS_KEY = "user_saw_hide_balance_hint"

private const val USER_SAW_ADD_SINGLE_EXPENSE_HINT_PARAMETERS_KEY =
    "user_saw_add_single_expense_hint"


private const val USER_SAW_EXPENSE_SWITCH_HINT_PARAMETERS_KEY = "user_saw_switch_expense_hint"

private const val USER_SAW_ADD_RECURRING_EXPENSE_HINT_PARAMETERS_KEY =
    "user_saw_add_recurring_expense_hint"

private const val USER_SAW_MULTI_ACCOUNT_HINT_PARAMETERS_KEY = "user_saw_multi_account_hint"

private const val USER_SAW_CALENDAR_ICON_HINT_PARAMETERS_KEY = "user_saw_calendar_icon_hint"

/**
 * Backup enabled
 */
private const val BACKUP_ENABLED_PARAMETERS_KEY = "backup_enabled"

/**
 * Last successful backup timestamp
 */
private const val LAST_BACKUP_TIMESTAMP = "last_backup_ts"

/**
 * Should reset init date at next app launch after backup restore
 */
private const val SHOULD_RESET_INIT_DATE = "should_reset_init_date"

/**
 * Home screen display balance
 */
private const val DISPLAY_BALANCE = "display_balance"

/**
 * App lock
 */
private const val APP_LOCK = "app_lock"

/**
 * Future expenses menu in report activity
 */
private const val SHOW_CASE_VIEW_EXPENSES_BREAK_DOWN = "expenses_break_menu_in_future"

/**
 * Future expenses menu in select category
 */
private const val SHOW_CASE_VIEW_MANAGE_CATEGORIES_IN_SELECT_CATEGORY =
    "manage_categories_in_select_category"

/**
 * Language
 */
private const val KEY_CHANGE_LANGUAGE = "change_language"

/**
 * Active Account
 */
private const val KEY_ACTIVE_ACCOUNT = "active_account"
private const val KEY_ACTIVE_ACCOUNT_NAME = "active_account_name"


/**
 * Categories sorting
 * Default should be Sort By Latest
 */
private const val CATEGORIES_SORTING_KEY = "categories_sorting"

fun AppPreferences.getInitDate(): LocalDate? {
    val timestamp = getLong(INIT_DATE_PARAMETERS_KEY, 0L)
    if (timestamp <= 0L) {
        return LocalDate.now()
    }

    return localDateFromTimestamp(timestamp)
}

fun AppPreferences.setInitDate(date: Date) {
    putLong(INIT_DATE_PARAMETERS_KEY, date.time)
}

fun AppPreferences.getLocalId(): String? {
    return getString(LOCAL_ID_PARAMETERS_KEY)
}

fun AppPreferences.setLocalId(localId: String) {
    putString(LOCAL_ID_PARAMETERS_KEY, localId)
}

fun AppPreferences.getNumberOfDailyOpen(): Int {
    return getInt(NUMBER_OF_DAILY_OPEN_PARAMETERS_KEY, 0)
}

fun AppPreferences.setNumberOfDailyOpen(numberOfDailyOpen: Int) {
    putInt(NUMBER_OF_DAILY_OPEN_PARAMETERS_KEY, numberOfDailyOpen)
}

fun AppPreferences.getRatingPopupLastAutoShowTimestamp(): Long {
    return getLong(RATING_POPUP_LAST_AUTO_SHOW_PARAMETERS_KEY, 0)
}

fun AppPreferences.setRatingPopupLastAutoShowTimestamp(timestamp: Long) {
    putLong(RATING_POPUP_LAST_AUTO_SHOW_PARAMETERS_KEY, timestamp)
}


fun AppPreferences.getCurrentAppVersion(): Int {
    return getInt(APP_VERSION_PARAMETERS_KEY, 0)
}

fun AppPreferences.setCurrentAppVersion(appVersion: Int) {
    putInt(APP_VERSION_PARAMETERS_KEY, appVersion)
}

fun AppPreferences.getNumberOfOpen(): Int {
    return getInt(NUMBER_OF_OPEN_PARAMETERS_KEY, 0)
}

fun AppPreferences.setNumberOfOpen(numberOfOpen: Int) {
    putInt(NUMBER_OF_OPEN_PARAMETERS_KEY, numberOfOpen)
}

fun AppPreferences.getLastOpenTimestamp(): Long {
    return getLong(LAST_OPEN_DATE_PARAMETERS_KEY, 0)
}

fun AppPreferences.setLastOpenTimestamp(timestamp: Long) {
    putLong(LAST_OPEN_DATE_PARAMETERS_KEY, timestamp)
}

fun AppPreferences.getLowMoneyWarningAmount(): Int {
    return getInt(LOW_MONEY_WARNING_AMOUNT_PARAMETERS_KEY, DEFAULT_LOW_MONEY_WARNING_AMOUNT)
}

fun AppPreferences.setLowMoneyWarningAmount(amount: Int) {
    putInt(LOW_MONEY_WARNING_AMOUNT_PARAMETERS_KEY, amount)
}

/**
 * Get the first day of the week to display to the user
 *
 * @return the id of the first day of week to display
 */
fun AppPreferences.getCaldroidFirstDayOfWeek(): Int {
    val currentValue = getInt(FIRST_DAY_OF_WEEK_PARAMETERS_KEY, -1)
    return if (currentValue < 1 || currentValue > 7) {
        CaldroidFragment.MONDAY
    } else currentValue
}

/**
 * Set the first day of week to display to the user
 *
 * @param firstDayOfWeek the id of the first day of week to display
 */
fun AppPreferences.setCaldroidFirstDayOfWeek(firstDayOfWeek: Int) {
    putInt(FIRST_DAY_OF_WEEK_PARAMETERS_KEY, firstDayOfWeek)
}

/**
 * The user wants or not to receive notification about updates
 *
 * @return true if we can display update notifications, false otherwise
 */
fun AppPreferences.isUserAllowingUpdatePushes(): Boolean {
    return getBoolean(USER_ALLOW_UPDATE_PUSH_PARAMETERS_KEY, true)
}

/**
 * Set the user choice about update notifications
 *
 * @param value if the user wants or not to receive notifications about updates
 */
fun AppPreferences.setUserAllowUpdatePushes(value: Boolean) {
    putBoolean(USER_ALLOW_UPDATE_PUSH_PARAMETERS_KEY, value)
}

/**
 * The user wants or not to receive a daily monthly notification when report is available
 *
 * @return true if we can display monthly notifications, false otherwise
 */
fun AppPreferences.isUserAllowingMonthlyReminderPushes(): Boolean {
    return getBoolean(USER_ALLOW_MONTHLY_PUSH_PARAMETERS_KEY, true)
}

/**
 * Has the user complete the rating popup
 *
 * @return true if the user has already answered, false otherwise
 */
fun AppPreferences.hasUserCompleteRating(): Boolean {
    return getBoolean(RATING_COMPLETED_PARAMETERS_KEY, false)
}

/**
 * Set that the user has complete the rating popup process
 */
fun AppPreferences.setUserHasCompleteRating() {
    putBoolean(RATING_COMPLETED_PARAMETERS_KEY, true)
}

/**
 * Has the user saw the monthly report hint so far
 *
 * @return true if the user saw it, false otherwise
 */
fun AppPreferences.hasUserSawMonthlyReportHint(): Boolean {
    return getBoolean(USER_SAW_MONTHLY_REPORT_HINT_PARAMETERS_KEY, false)
}

/**
 * Set that the user saw the monthly report hint
 */
fun AppPreferences.setUserSawMonthlyReportHint() {
    putBoolean(USER_SAW_MONTHLY_REPORT_HINT_PARAMETERS_KEY, true)
}


/**
 * Break Down Hint
 */
fun AppPreferences.hasUserSawBreakDownHint(): Boolean {
    return getBoolean(USER_SAW_BREAK_DOWN_HINT_PARAMETERS_KEY, false)
}

fun AppPreferences.setUserSawBreakDownHint() {
    putBoolean(USER_SAW_BREAK_DOWN_HINT_PARAMETERS_KEY, true)
}

/**
 * Backup Hint
 */
fun AppPreferences.hasUserSawBackupHint(): Boolean {
    return getBoolean(USER_SAW_BACKUP_HINT_PARAMETERS_KEY, false)
}

fun AppPreferences.setUserSawBackupHint() {
    putBoolean(USER_SAW_BACKUP_HINT_PARAMETERS_KEY, true)
}

/**
 * Search Hint
 */
fun AppPreferences.hasUserSawSearchHint(): Boolean {
    return getBoolean(USER_SAW_SEARCH_HINT_PARAMETERS_KEY, false)
}

fun AppPreferences.setUserSawSearchHint() {
    putBoolean(USER_SAW_SEARCH_HINT_PARAMETERS_KEY, true)
}

/**
 * Help Hint
 */
fun AppPreferences.hasUserSawHelpHint(): Boolean {
    return getBoolean(USER_SAW_HELP_HINT_PARAMETERS_KEY, false)
}

fun AppPreferences.setUserSawHelpHint() {
    putBoolean(USER_SAW_HELP_HINT_PARAMETERS_KEY, true)
}

/**
 * Settings hint
 */
fun AppPreferences.hasUserSawSettingsHint(): Boolean {
    return getBoolean(USER_SAW_SETTINGS_HINT_PARAMETERS_KEY, false)
}

fun AppPreferences.setUserSawSettingsHint() {
    putBoolean(USER_SAW_SETTINGS_HINT_PARAMETERS_KEY, true)
}

/**
 * Privacy hint
 */
fun AppPreferences.hasUserSawPrivacyHint(): Boolean {
    return getBoolean(USER_SAW_PRIVACY_HINT_PARAMETERS_KEY, false)
}

fun AppPreferences.setUserSawPrivacyHint() {
    putBoolean(USER_SAW_PRIVACY_HINT_PARAMETERS_KEY, true)
}

/**
 * Multiple Accounts hint
 */
fun AppPreferences.hasUserSawMultiAccountsHint(): Boolean {
    return getBoolean(USER_SAW_MULTI_ACCOUNT_HINT_PARAMETERS_KEY, false)
}

fun AppPreferences.setUserSawMultiAccountsHint(enabled: Boolean = true) {
    putBoolean(USER_SAW_MULTI_ACCOUNT_HINT_PARAMETERS_KEY, enabled)
}

/**
 * Calendar icon hint
 */
fun AppPreferences.hasUserSawCalendarIconHint(): Boolean {
    return getBoolean(USER_SAW_CALENDAR_ICON_HINT_PARAMETERS_KEY, false)
}

fun AppPreferences.setUserSawCalendarIconHint(enabled: Boolean = true) {
    putBoolean(USER_SAW_CALENDAR_ICON_HINT_PARAMETERS_KEY, enabled)
}

/**
 * Hide Balance, Switch Button on home screen
 */
fun AppPreferences.hasUserSawHideBalanceHint(): Boolean {
    return getBoolean(USER_SAW_HIDE_BALANCE_HINT_PARAMETERS_KEY, false)
}

fun AppPreferences.setUserSawHideBalanceHint() {
    putBoolean(USER_SAW_HIDE_BALANCE_HINT_PARAMETERS_KEY, true)
}

/**
 * Hint Add single expense
 */
fun AppPreferences.hasUserSawAddSingleExpenseHint(): Boolean {
    return getBoolean(USER_SAW_ADD_SINGLE_EXPENSE_HINT_PARAMETERS_KEY, false)
}

fun AppPreferences.setUserSawAddSingleExpenseHint() {
    putBoolean(USER_SAW_ADD_SINGLE_EXPENSE_HINT_PARAMETERS_KEY, true)
}


/**
 * Hint switch expense / income
 */
fun AppPreferences.hasUserSawSwitchExpenseHint(): Boolean {
    return getBoolean(USER_SAW_EXPENSE_SWITCH_HINT_PARAMETERS_KEY, false)
}

fun AppPreferences.setUserSawSwitchExpenseHint() {
    putBoolean(USER_SAW_EXPENSE_SWITCH_HINT_PARAMETERS_KEY, true)
}

fun AppPreferences.resetUserSawSwitchExpenseHint() {
    putBoolean(USER_SAW_EXPENSE_SWITCH_HINT_PARAMETERS_KEY, false)
}

/**
 * Hint Add Recurring expense
 */
fun AppPreferences.hasUserSawAddRecurringExpenseHint(): Boolean {
    return getBoolean(USER_SAW_ADD_RECURRING_EXPENSE_HINT_PARAMETERS_KEY, false)
}

fun AppPreferences.setUserSawAddRecurringExpenseHint() {
    putBoolean(USER_SAW_ADD_RECURRING_EXPENSE_HINT_PARAMETERS_KEY, true)
}

/**
 *
 */
fun AppPreferences.isBackupEnabled(): Boolean {
    return getBoolean(BACKUP_ENABLED_PARAMETERS_KEY, false)
}

fun AppPreferences.setBackupEnabled(enabled: Boolean) {
    putBoolean(BACKUP_ENABLED_PARAMETERS_KEY, enabled)
}

fun AppPreferences.getLastBackupDate(): Date? {
    val lastTimestamp = getLong(LAST_BACKUP_TIMESTAMP, -1)
    if (lastTimestamp > 0) {
        return Date(lastTimestamp)
    }

    return null
}

fun AppPreferences.saveLastBackupDate(date: Date?) {
    if (date != null) {
        putLong(LAST_BACKUP_TIMESTAMP, date.time)
    } else {
        putLong(LAST_BACKUP_TIMESTAMP, -1)
    }
}

fun AppPreferences.setShouldResetInitDate(shouldResetInitDate: Boolean) {
    putBoolean(SHOULD_RESET_INIT_DATE, shouldResetInitDate, forceCommit = true)
}

fun AppPreferences.getShouldResetInitDate(): Boolean {
    return getBoolean(SHOULD_RESET_INIT_DATE, false)
}

fun AppPreferences.setDisplayBalance(shouldResetInitDate: Boolean) {
    putBoolean(DISPLAY_BALANCE, shouldResetInitDate, forceCommit = true)
}

fun AppPreferences.getDisplayBalance(): Boolean {
    return getBoolean(DISPLAY_BALANCE, true)
}

// App Lock
fun AppPreferences.setAppPasswordProtectionOn(isAppPasswordProtectionOn: Boolean) {
    putBoolean(APP_PASSWORD_PROTECTION, isAppPasswordProtectionOn, forceCommit = true)
}

fun AppPreferences.isAppPasswordProtectionOn(): Boolean {
    return getBoolean(APP_PASSWORD_PROTECTION, false)
}

// Password Hash
fun AppPreferences.appPasswordHash(): String = getString(APP_PASSWORD_HASH) ?: ""

fun AppPreferences.setAppPasswordHash(appPasswordHash: String) {
    putString(APP_PASSWORD_HASH, appPasswordHash)
}

// Hidden protection Type
fun AppPreferences.appProtectionType(): Int {
    return getInt(APP_PROTECTION_TYPE, PROTECTION_PIN)
}

fun AppPreferences.hiddenProtectionType(): Int {
    return getInt(PROTECTION_TYPE, PROTECTION_PIN)
}

fun AppPreferences.setHiddenProtectionType(hiddenProtectionType: Int) {
    putInt(PROTECTION_TYPE, hiddenProtectionType)
}

/**
 * Has the user complete Future Expenses Break Down
 *
 * @return true if the user has already viewed, false otherwise
 */
fun AppPreferences.hasUserCompleteExpensesBreakDownShowCaseView(): Boolean {
    return getBoolean(SHOW_CASE_VIEW_EXPENSES_BREAK_DOWN, false)
}

/**
 * Set that the user has complete Expenses Break Down
 */
fun AppPreferences.setUserCompleteExpensesBreakDownShowCaseView() {
    putBoolean(SHOW_CASE_VIEW_EXPENSES_BREAK_DOWN, true)
}

/**
 * Has the user complete manage categories from select category while adding / editing expenses
 *
 * @return true if the user has already viewed, false otherwise
 */
fun AppPreferences.hasUserCompleteManageCategoriesFromSelectCategoryShowCaseView(): Boolean {
    return getBoolean(SHOW_CASE_VIEW_MANAGE_CATEGORIES_IN_SELECT_CATEGORY, false)
}

/**
 * Set that the user has complete manage categories from select category while adding / editing expenses
 */
fun AppPreferences.setUserCompleteManageCategoriesFromSelectCategoryShowCaseView() {
    putBoolean(SHOW_CASE_VIEW_MANAGE_CATEGORIES_IN_SELECT_CATEGORY, true)
}

// Active account
// Default value is 1 as ID of DEFAULT is 1
fun AppPreferences.activeAccount(): Long = getLong(KEY_ACTIVE_ACCOUNT, 1)


fun AppPreferences.activeAccountLabel(): String =
    getString(KEY_ACTIVE_ACCOUNT_NAME) ?: AccountType.DEFAULT_ACCOUNT.name


/**
 * Account title must be saved from AccountType enum e.g
 * AccountType.DEFAULT_ACCOUNT.name
 */
fun AppPreferences.setActiveAccount(accountId: Long?, accountName: String?) {
    putLong(KEY_ACTIVE_ACCOUNT, accountId ?: 1) // 1 is default account id from the DB
    putString(KEY_ACTIVE_ACCOUNT_NAME, accountName ?: AccountType.DEFAULT_ACCOUNT.name)
}

/**
 * Save sorting options and load next time as same.
 */
fun AppPreferences.setSortingType(sortingType: String) {
    putString(CATEGORIES_SORTING_KEY, sortingType)
}

/**
 * Get sorting options and default is 'By Latest'.
 */
fun AppPreferences.getSortingType(): SortOption {
    return SortOption.valueOf(getString(CATEGORIES_SORTING_KEY) ?: SortOption.ByLatest.name)
}