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

import android.content.BroadcastReceiver
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Resources.Theme
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.text.format.DateFormat
import android.util.TypedValue
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.os.ConfigurationCompat
import androidx.core.widget.NestedScrollView
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.viewbinding.ViewBinding
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Retrieve a color from the current [android.content.res.Resources.Theme].
 */
@ColorInt
fun Context.themeColor(
    @AttrRes themeAttrId: Int
): Int {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getColor(0, Color.MAGENTA)
    }
}

fun Long.getDateTimeString(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return SimpleDateFormat.getDateTimeInstance().format(calendar.time)
}


fun Long.getTimeString(): String {
    return (this / 60L / 1000L).toString()
}

var Calendar.hour: Int
    get() = this.get(Calendar.HOUR_OF_DAY)
    set(value) {
        this.set(Calendar.HOUR_OF_DAY, value)
    }

var Calendar.minute: Int
    get() = this.get(Calendar.MINUTE)
    set(value) {
        this.set(Calendar.MINUTE, value)
    }

var Calendar.DayOfYear: Int
    get() = this.get(Calendar.DAY_OF_YEAR)
    set(value) {
        this.set(Calendar.DAY_OF_YEAR, value)
    }

fun Calendar.addDay(amount: Int): Calendar {
    this.add(Calendar.DAY_OF_YEAR, amount)
    return this
}

fun Calendar.getDateTimeString(): String {
    return SimpleDateFormat.getDateTimeInstance().format(this.time)
}

fun Date.getTimeString(context: Context): String {
    val pattern = if (DateFormat.is24HourFormat(context)) "HH:mm" else "hh:mm a"
    val primaryLocale = ConfigurationCompat.getLocales(context.resources.configuration).get(0)
    val dateFormat = SimpleDateFormat(pattern, primaryLocale)
    return dateFormat.format(this)
}

fun Date.getDateTimeString(): String {
    val format = java.text.DateFormat.getDateTimeInstance(
        java.text.DateFormat.SHORT, java.text.DateFormat.SHORT, Locale.getDefault()
    )
    return format.format(this)
}

fun PopupMenu.forcePopUpMenuToShowIcons() {
    try {
        val method = menu.javaClass.getDeclaredMethod(
            "setOptionalIconsVisible", Boolean::class.javaPrimitiveType
        )
        method.isAccessible = true
        method.invoke(menu, true)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.getAttrColor(res: Int): Int {
    val typedValue = TypedValue()
    val theme: Theme = this.theme
    theme.resolveAttribute(res, typedValue, true)
    return ContextCompat.getColor(this, typedValue.resourceId)
}

fun Context.isDarkThemeOn(): Boolean {
    return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
}

fun Any?.isNull() = this == null

fun Any?.isNotNull() = this != null

fun <T, VH : RecyclerView.ViewHolder> ListAdapter<T, VH>.getItemOrNull(position: Int): T? {
    return this.currentList.elementAtOrNull(position)
}

fun View.setVerticalBias(bias: Float) {
    val params = layoutParams
    if (params is ConstraintLayout.LayoutParams) {
        params.verticalBias = bias
        layoutParams = params
    }
}

fun View.setBackgroundColorShaped(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this.background.colorFilter = BlendModeColorFilter(
            color, BlendMode.SRC_ATOP
        )
    } else {
        @Suppress("DEPRECATION") this.background.setColorFilter(
            color, PorterDuff.Mode.SRC_ATOP
        )
    }
}

fun View.onClick(click: (View) -> Unit) = setOnClickListener { click(it) }

fun RecyclerView.disableAnimations() {
    (itemAnimator as SimpleItemAnimator).apply {
        changeDuration = 0
        removeDuration = 0
        addDuration = 0
        moveDuration = 0
    }
}

fun TextView.setDateText(date: Date, pattern: String = "dd. MM.") {
    val primaryLocale = ConfigurationCompat.getLocales(context.resources.configuration).get(0)
    val dateFormat = SimpleDateFormat(pattern, primaryLocale)
    text = dateFormat.format(date)
}

fun TextView.setTimeText(date: Date) {
    text = date.getTimeString(context)
}

fun EditText.getNumber() = this.text.toString().toInt()
fun EditText.getString() = this.text.toString()

fun TextInputLayout.showError(message: String?) {
    isErrorEnabled = message != null
    error = message
}

fun TextView.setDrawableTint(color: Int) {
    TextViewCompat.setCompoundDrawableTintList(this, ColorStateList.valueOf(color))
}

fun NestedScrollView.scrollToBottom() {
    smoothScrollBy(0, this.getChildAt(0).height)
}

val ViewBinding.context: Context
    get() = root.context


val Fragment.applicationContext: Context
    get() = requireActivity().applicationContext

fun BroadcastReceiver.goAsync(
    coroutineScope: CoroutineScope = GlobalScope,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default,
    block: suspend () -> Unit
) {
    val result = goAsync()
    coroutineScope.launch(coroutineDispatcher) {
        try {
            block()
        } finally {
            // Always call finish(), even if the coroutineScope was cancelled
            result.finish()
        }
    }
}