/*
 *   Copyright 2024 Waheed Nazir
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
package com.simplebudget.view.settings.releaseHistory

import java.util.*

/**
 */
class ReleaseHistoryRepo {
    val historyList: List<ReleaseHistory>
        get() {
            val releaseList = ArrayList<ReleaseHistory>()

            // 3.3.3
            releaseList.add(ReleaseHistory("Version", "3.3.3", "Added support for Android 14."))

            // 3.3.2
            releaseList.add(ReleaseHistory("Version", "3.3.2", "Categories sorting options added, Sort By Latest / Sort Alphabetically."))
            releaseList.add(ReleaseHistory("Version", "3.3.2", "Added overall designs enhancements."))

            // 3.3.1
            releaseList.add(ReleaseHistory("Version", "3.3.1", "Pie chart added back with improvements."))
            releaseList.add(ReleaseHistory("Version", "3.3.1", "Reviews section added."))
            releaseList.add(ReleaseHistory("Version", "3.3.1", "Consent added to display ads."))

            // 3.3.0
            releaseList.add(ReleaseHistory("Version", "3.3.0", "Expenses breakdown design updated."))
            releaseList.add(ReleaseHistory("Version", "3.3.0", "Bug fixes on multiple accounts / reports."))
            releaseList.add(ReleaseHistory("Version", "3.3.0", "Added Youtube / WhatsApp channel inside help section."))
            releaseList.add(ReleaseHistory("Version", "3.3.0", "Enhanced app performance and boot time."))

            // 3.2.0
            releaseList.add(ReleaseHistory("Version", "3.2.0", "Allowed renaming of default account."))
            releaseList.add(ReleaseHistory("Version", "3.2.0", "Minor bug fixes on multiple accounts / reports."))


            // 3.1.0
            releaseList.add(ReleaseHistory("Version", "3.1.0", "Added multiple accounts."))
            releaseList.add(ReleaseHistory("Version", "3.1.0", "Updated premium payments."))
            releaseList.add(ReleaseHistory("Version", "3.1.0", "You can search your expenses by amount."))
            releaseList.add(ReleaseHistory("Version", "3.1.0", "Added side navigation for more options."))
            releaseList.add(ReleaseHistory("Version", "3.1.0", "Added overall designs enhancements."))
            releaseList.add(ReleaseHistory("Version", "3.1.0", "Bugs fixed for account name duplication."))
            releaseList.add(ReleaseHistory("Version", "3.1.0", "Default Simple Budget Savings account renamed to DEFAULT ACCOUNT"))

            // 3.0.0
            releaseList.add(ReleaseHistory("Version", "3.0.0", "Added multiple accounts."))
            releaseList.add(ReleaseHistory("Version", "3.0.0", "Updated premium payments."))
            releaseList.add(ReleaseHistory("Version", "3.0.0", "Added side navigation for more options."))
            releaseList.add(ReleaseHistory("Version", "3.0.0", "Added overall designs enhancements."))

            // 2.1.4
            releaseList.add(ReleaseHistory("Version", "2.1.4", "Search expenses issues fixed."))
            releaseList.add(ReleaseHistory("Version", "2.1.4", "Fixed few bugs and crashes."))
            releaseList.add(ReleaseHistory("Version", "2.1.4", "Added overall designs enhancements."))

            // 2.1.3
            releaseList.add(ReleaseHistory("Version", "2.1.3", "App reset functionality added."))
            releaseList.add(ReleaseHistory("Version", "2.1.3", "You can edit and delete expenses categories."))
            releaseList.add(ReleaseHistory("Version", "2.1.3", "Pop up, app open ads totally removed."))
            releaseList.add(ReleaseHistory("Version", "2.1.3", "You can check breakdown and reports up-to 2 years."))
            releaseList.add(ReleaseHistory("Version", "2.1.3", "Fixed few bugs and crashes."))
            releaseList.add(ReleaseHistory("Version", "2.1.3", "Added overall designs enhancements."))

            // 2.1.2
            releaseList.add(ReleaseHistory("Version", "2.1.2", "You can check future and past expenses breakdowns."))
            releaseList.add(ReleaseHistory("Version", "2.1.2", "You can check and print future and past reports."))
            releaseList.add(ReleaseHistory("Version", "2.1.2", "You can print reports for search results too."))
            releaseList.add(ReleaseHistory("Version", "2.1.2", "FAQ section added inside app settings."))
            releaseList.add(ReleaseHistory("Version", "2.1.2", "Fixed few bugs and crashes."))
            releaseList.add(ReleaseHistory("Version", "2.1.2", "Added overall designs enhancements."))

            // 2.1.1
            releaseList.add(ReleaseHistory("Version", "2.1.1", "You can check future and past expenses breakdowns."))
            releaseList.add(ReleaseHistory("Version", "2.1.1", "You can check and print future and past reports."))
            releaseList.add(ReleaseHistory("Version", "2.1.1", "You can print reports for search results too."))
            releaseList.add(ReleaseHistory("Version", "2.1.1", "FAQ section added inside app settings."))
            releaseList.add(ReleaseHistory("Version", "2.1.1", "Added overall designs enhancements."))

            // 2.0.9
            releaseList.add(ReleaseHistory("Version", "2.0.9", "Search expenses functionality added."))
            releaseList.add(ReleaseHistory("Version", "2.0.9", "Expenses breakdown chart will display top expenses."))
            releaseList.add(ReleaseHistory("Version", "2.0.9", "UI enhancement and overall improvements."))

            // 2.0.8
            releaseList.add(ReleaseHistory("Version", "2.0.8", "New design added for category selection while adding expenses."))
            releaseList.add(ReleaseHistory("Version", "2.0.8", "Voice search added for categories while adding expenses."))
            releaseList.add(ReleaseHistory("Version", "2.0.8", "Edit expenses, category was not populating issue resolved."))
            releaseList.add(ReleaseHistory("Version", "2.0.8", "UI enhancement and bug fixing."))

            // 2.0.7
            releaseList.add(ReleaseHistory("Version", "2.0.7", "New design added for category selection."))
            releaseList.add(ReleaseHistory("Version", "2.0.7", "You can add or remove your categories from app settings."))
            releaseList.add(ReleaseHistory("Version", "2.0.7", "Filters(All, Expenses and Incomes) added in breakdown."))
            releaseList.add(ReleaseHistory("Version", "2.0.7", "Added intro and hint functionality for all expense/income switch."))
            releaseList.add(ReleaseHistory("Version", "2.0.7", "UI enhancement and bug fixing."))

            // 2.0.6
            releaseList.add(ReleaseHistory("Version", "2.0.6", "Added functionality to check upcoming(Future) expenses and payments. You can access inside app settings. "))
            releaseList.add(ReleaseHistory("Version", "2.0.6", "Added monthly expenses break down (Pie Chart). You can access inside app settings. "))
            releaseList.add(ReleaseHistory("Version", "2.0.6", "Edit expenses category was not populating, issue resolved."))
            releaseList.add(ReleaseHistory("Version", "2.0.6", "Added intro and hint functionality for all important features."))
            releaseList.add(ReleaseHistory("Version", "2.0.6", "Telegram group added for quick support and news."))
            releaseList.add(ReleaseHistory("Version", "2.0.6", "UI enhancement and bug fixing."))

            // 2.0.5
            releaseList.add(ReleaseHistory("Version", "2.0.5", "Create/Edit own categories for expenses."))
            releaseList.add(ReleaseHistory("Version", "2.0.5", "Telegram channel added for quick support and news."))
            releaseList.add(ReleaseHistory("Version", "2.0.5", "More apps section added."))
            releaseList.add(ReleaseHistory("Version", "2.0.5", "Privacy policy section added."))
            releaseList.add(ReleaseHistory("Version", "2.0.5", "UI enhancement and bug fixing."))

            // 2.0.2
            releaseList.add(ReleaseHistory("Version", "2.0.2", "Monthly reports now showing categories and total spending."))
            releaseList.add(ReleaseHistory("Version", "2.0.2", "Reports PDF design updated added categories."))
            releaseList.add(ReleaseHistory("Version", "2.0.2", "Feedback to email issue fixed."))
            releaseList.add(ReleaseHistory("Version", "2.0.2", "UI enhancement and bug fixing."))

            // 2.0.1
            releaseList.add(ReleaseHistory("Version", "2.0.1", "Monthly reports now showing categories and total spending."))
            releaseList.add(ReleaseHistory("Version", "2.0.1", "Reports PDF design updated added categories."))
            releaseList.add(ReleaseHistory("Version", "2.0.1", "Feedback to email issue fixed."))
            releaseList.add(ReleaseHistory("Version", "2.0.1", "UI enhancement and bug fixing."))

            // 2.0.0
            releaseList.add(ReleaseHistory("Version", "2.0.0", "Categories added for Incomes / Expenses."))
            releaseList.add(ReleaseHistory("Version", "2.0.0", "Categories added for monthly reports."))
            releaseList.add(ReleaseHistory("Version", "2.0.0", "Reports PDF design updated."))
            releaseList.add(ReleaseHistory("Version", "2.0.0", "Rating / In app review added for quick reviews."))
            releaseList.add(ReleaseHistory("Version", "2.0.0", "Feedback option added into settings."))
            releaseList.add(ReleaseHistory("Version", "2.0.0", "UI enhancement and bug fixing."))

            // 1.2.0
            releaseList.add(ReleaseHistory("Version", "1.2.0", "Premium acknowledgement screen added."))
            releaseList.add(ReleaseHistory("Version", "1.2.0", "Export monthly reports fixed few issues."))
            releaseList.add(ReleaseHistory("Version", "1.2.0", "UI enhancement and bug fixing."))

            // 1.1.0
            releaseList.add(ReleaseHistory("Version", "1.1.0", "Share monthly reports as spreadsheet."))
            releaseList.add(ReleaseHistory("Version", "1.1.0", "Print/Download monthly reports as pdf."))
            releaseList.add(ReleaseHistory("Version", "1.1.0", "Redeem promo code to buy premium."))
            releaseList.add(ReleaseHistory("Version", "1.1.0", "UI enhancement and bug fixing."))

            // 1.0.9
            releaseList.add(ReleaseHistory("Version", "1.0.9", "Expense displayed on dashboard."))
            releaseList.add(ReleaseHistory("Version", "1.0.9", "App security user interface redesigned."))

            // 1.0.8
            releaseList.add(ReleaseHistory("Version", "1.0.8", "App password protection option added."))
            releaseList.add(ReleaseHistory("Version", "1.0.8", "Change start day of weeks functionality added."))
            releaseList.add(ReleaseHistory("Version", "1.0.8", "Recurring option for every 2 months added."))
            releaseList.add(ReleaseHistory("Version", "1.0.8", "Opening reports upon clicking weekly report notification."))
            releaseList.add(ReleaseHistory("Version", "1.0.8", "Settings click app version to see release notes."))
            releaseList.add(ReleaseHistory("Version", "1.0.8", "Performance and design enhancements."))

            // 1.0.7
            releaseList.add(ReleaseHistory("Version", "1.0.7", "Hide / Show balance feature added for privacy."))
            releaseList.add(ReleaseHistory("Version", "1.0.7", "Option added to start week from Sunday or Monday."))
            releaseList.add(ReleaseHistory("Version", "1.0.7", "Currency search functionality added."))
            releaseList.add(ReleaseHistory("Version", "1.0.7", "Performance and design enhancements"))

            return releaseList
        }
}
