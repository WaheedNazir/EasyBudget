package com.simplebudget.view.settings.faq

import java.util.*

/**
 */
class FaqRepo {
    val faqList: List<Question>
        get() {
            val faqList = ArrayList<Question>()

            faqList.addAll(
                listOf(
                    Question(
                        "What is a SimpleBudget app and how does it work?",
                        "A SimpleBudget is a tool that helps users manage their finances by tracking their spending and income, setting financial goals, and providing insights into their spending habits. You can input your expenses and income, and the app will automatically provide an overview of your financial status."
                    ),
                    Question(
                        "What are some of the features offered by SimpleBudget app?",
                        "SimpleBudget offers a variety of features, including: expense tracking, budget creation, bill reminders, investment tracking, and spending analysis."
                    ),
                    Question(
                        "Is SimpleBudget app necessary if I have a good handle on my finances?",
                        "This app can be helpful for anyone, regardless of their financial situation. It can help users stay on top of their finances and make informed financial decisions."
                    ),
                    Question(
                        "How does SimpleBudget help me save money?",
                        "SimpleBudget help you keep track of your spending and income, allowing you to see where your money is going and identify areas where they can cut back, such as saving for a big purchase or paying off debt."
                    ),
                    Question(
                        "Can I use a SimpleBudget on multiple devices?",
                        "Yes, But you can access data on one device at a time. However in future we are adding support for multiple devices to connect with one account so that partners can key in simultaneously."
                    ),
                    Question(
                        "How secure is my financial information in SimpleBudget?",
                        "SimpleBudget uses secure encryption to protect users' financial information. It's important to choose an app from a reputable developer and to regularly review the app's privacy policy and security measures."
                    ),
                    Question(
                        "How do I categorize my transactions in SimpleBudget?",
                        "You can categorize your expenses while adding them in SimpleBudget app. You manage your categories from the app. "
                    ),
                    Question(
                        "How often should I update my budget in the app?",
                        "It's recommended to update your budget in the app on a regular basis, such as daily or weekly, to ensure that the information is up-to-date and accurate."
                    ),
                    Question(
                        " What happens if I lose my phone or my device is stolen?",
                        "SimpleBudget offers the ability to backup and restore your data, allowing you to access your financial information from another device if necessary. It's important to enable backup from app settings so that app could regularly backup your data to protect against data loss."
                    ),
                    Question(
                        "Can I connect my bank account?",
                        "No, For the sake of simplicity and privacy, you cannot connect your bank account."
                    ),
                    Question(
                        "How can I track my spending?",
                        "You can track your spending by logging your transactions in the app. You can export reports and check expenses breakdown."
                    ),
                    Question(
                        "Can I see my spending history?",
                        "Yes, you can view your spending history from reports to see how much you have spent and on what."
                    ),
                    Question(
                        "How do I get support?",
                        "You can get support by contacting us through the app from feedback section under app's settings or from Telegram channel support group. Otherwise you can always send us an email."
                    ),
                    Question(
                        "Is SimpleBudget free?",
                        "Yes, All the functionalities of SimpleBudget are completely free to use. However free version have advertisements."
                    ),
                    Question(
                        "Is it possible to share my budget with someone else in SimpleBudget?",
                        "No, SimpleBudget don't have the ability to add multiple users and share budget information, but in future releases this feature would be available."
                    ),
                    Question(
                        "Can I see my spending history in SimpleBudget?",
                        "Yes, SimpleBudget provides a spending history, allowing users to see their past spending patterns and make informed financial decisions."
                    ),
                    Question(
                        "Can I set multiple budgets in SimpleBudget?",
                        "Yes, SimpleBudget allow users to set multiple budgets for different expenses, such as housing, food, and entertainment etc."
                    ),
                    Question(
                        "How does SimpleBudget handle irregular income?",
                        "SimpleBudget can handle irregular income by allowing users to manually input their income at any time."
                    ),
                    Question(
                        "How does SimpleBudget help me stick to my budget?",
                        "SimpleBudget can help users stick to their budget by providing an overview of their spending, alerting them when they are close to reaching their budget limit, and offering suggestions on how to adjust their spending."
                    ),
                    Question(
                        "If I delete or edit any category. Would it also update or delete expenses attached to this category?",
                        "No, By editing or deleting any category won\'t affect expenses. If you want to update categories in expenses you can directly edit / update in your expenses."
                    ),
                    Question(
                        "I can't see any amount, my balance is replaced with dashes -- what should I do?",
                        "You have enabled balance privacy, you can turn it OFF from the switch under the calendar on main screen! "
                    ),
                    Question(
                        "Can I create multiple account?",
                        "Yes, you can create multiple accounts!"
                    ),
                    Question(
                        "How many account I can create?",
                        "You can add 5 more accounts other than DEFAULT ACCOUNT so you'll be having total 6 accounts to manage your expenses!"
                    ),
                    Question(
                        "What would happen if I accidentally delete any account?",
                        "If you delete an account your transactions of that account would also be deleted! But if you have enabled data backup you can restore it quickly before the next auto-backup happened as it would overwrite your last backup with new changes and you can't restore it!"
                    ),
                    Question(
                        "How can I refund muy purchases?",
                        "You can ask your refund via Feedback section of the app under settings."
                    ),
                    // Add more questions here
                )
            )

            return faqList
        }
}
