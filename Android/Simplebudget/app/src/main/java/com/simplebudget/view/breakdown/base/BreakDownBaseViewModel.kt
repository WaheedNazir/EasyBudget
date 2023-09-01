/*
 *   Copyright 2023 Waheed Nazir
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
package com.simplebudget.view.breakdown.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplebudget.helper.getListOfMonthsAvailableForUser
import com.simplebudget.prefs.AppPreferences
import com.simplebudget.view.report.base.MonthlyReportSelectedPosition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class BreakDownBaseViewModel(private val appPreferences: AppPreferences) : ViewModel() {
    /**
     * The current selected position
     */
    val selectedPositionLiveData = MutableLiveData<BreakDownSelectedPosition>()
    val datesLiveData = MutableLiveData<List<LocalDate>>()

    fun loadData() {
        viewModelScope.launch {
            val pair = withContext(Dispatchers.IO) {
                return@withContext appPreferences.getListOfMonthsAvailableForUser()
            }
            datesLiveData.value = pair.first

            if (pair.first.isNotEmpty()) {
                selectedPositionLiveData.value =
                    BreakDownSelectedPosition(
                        pair.second,
                        pair.first[pair.second],
                        (pair.second == pair.first.size - 1)
                    )
            }
        }
    }

    fun onPreviousMonthButtonClicked() {
        val dates = datesLiveData.value ?: return
        val (selectedPosition) = selectedPositionLiveData.value ?: return

        if (selectedPosition > 0) {
            selectedPositionLiveData.value = BreakDownSelectedPosition(
                selectedPosition - 1,
                dates[selectedPosition - 1],
                (selectedPosition - 1 == dates.size - 1)
            )
        }
    }

    fun onNextMonthButtonClicked() {
        val dates = datesLiveData.value ?: return
        val (selectedPosition) = selectedPositionLiveData.value ?: return

        if (selectedPosition < dates.size - 1) {
            selectedPositionLiveData.value = BreakDownSelectedPosition(
                selectedPosition + 1,
                dates[selectedPosition + 1],
                (selectedPosition + 1 == dates.size - 1)
            )
        }
    }

    fun onPageSelected(position: Int) {
        val dates = datesLiveData.value ?: return
        selectedPositionLiveData.value =
            BreakDownSelectedPosition(position, dates[position], (position == dates.size - 1))
    }
}

data class BreakDownSelectedPosition(val position: Int, val date: LocalDate, val latest: Boolean)