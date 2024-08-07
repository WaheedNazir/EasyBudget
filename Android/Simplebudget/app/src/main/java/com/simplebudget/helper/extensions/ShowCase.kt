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
package com.simplebudget.helper.extensions

import android.content.Context
import android.view.View
import com.simplebudget.helper.showcaseviewlib.GuideView
import com.simplebudget.helper.showcaseviewlib.config.DismissType
import com.simplebudget.helper.showcaseviewlib.config.Gravity
import com.simplebudget.helper.showcaseviewlib.config.PointerType

/**
 * Show case view future expenses
 */
fun Context.showCaseView(
    targetView: View,
    title: String,
    message: String,
    handleGuideListener: () -> Unit
) {
    GuideView.Builder(this)
        .setTitle(title)
        .setContentText(message)
        .setGravity(Gravity.center) //optional
        .setDismissType(DismissType.anywhere) //optional - default DismissType.targetView
        .setTargetView(targetView)
        .setContentTextSize(14) //optional
        .setTitleTextSize(18) //optional
        .setPointerType(PointerType.arrow)
        .setGuideListener {
            handleGuideListener.invoke()
        }
        .build()
        .show()
}