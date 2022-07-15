/*
 *   Copyright 2022 Waheed Nazir
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
package com.simplebudget.view.breakdown

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.simplebudget.R
import com.simplebudget.databinding.FragmentBreakDownBinding
import com.simplebudget.helper.*
import com.simplebudget.helper.extensions.showCaseView
import com.simplebudget.iab.PREMIUM_PARAMETER_KEY
import com.simplebudget.prefs.AppPreferences
import com.simplebudget.prefs.hasUserCompleteExpensesBreakDownCategoryShowCaseView
import com.simplebudget.prefs.setUserCompleteExpensesBreakDownCategoryShowCaseView
import com.simplebudget.view.expenseedit.ExpenseEditActivity
import com.simplebudget.view.main.MainActivity
import com.simplebudget.view.recurringexpenseadd.RecurringExpenseEditActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


private const val ARG_DATE = "arg_date"


class BreakDownFragment : BaseFragment<FragmentBreakDownBinding>() {
    /**
     * The first date of the month at 00:00:00
     */
    private lateinit var date: Date
    private var type: String = ""
    private val appPreferences: AppPreferences by inject()
    private val viewModel: BreakDownViewModel by viewModel()
    private var adView: AdView? = null
    private val lisOfExpenses: ArrayList<BreakDownViewModel.CategoryWiseExpense> = ArrayList()

// ---------------------------------->

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentBreakDownBinding =
        FragmentBreakDownBinding.inflate(inflater, container, false)


    /**
     * Enable menu options handling
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    /**
     *
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar.
        inflater.inflate(R.menu.menu_breakdown, menu)
        val menuItem = menu.findItem(R.id.action_breakdown)
        val rootView = menuItem?.actionView as LinearLayout?
        val customFutureExpenseMenu = rootView?.findViewById<TextView>(R.id.tvMenuBreakdown)
        customFutureExpenseMenu?.let {
            if (appPreferences.hasUserCompleteExpensesBreakDownCategoryShowCaseView().not()) {
                activity?.showCaseView(
                    targetView = it,
                    title = getString(R.string.change_breakdown),
                    message = getString(R.string.change_breakdown_details),
                    handleGuideListener = {
                        appPreferences.setUserCompleteExpensesBreakDownCategoryShowCaseView()
                    }
                )
            }
            it.setOnClickListener {
                BreakdownType.showTypeDialog(
                    requireActivity(),
                    type,
                    onLanguageSelected = { selectedType ->
                        customFutureExpenseMenu.text = selectedType // Update label
                        type = selectedType // Update the type to save it to locally
                        val typeEnum = BreakdownType.getSelectedType(requireContext(), type)
                        binding?.balancesContainer?.visibility =
                            if (typeEnum == TYPE.ALL.name && lisOfExpenses.isNotEmpty()) View.VISIBLE else View.GONE
                        viewModel.loadDataForMonth(
                            date,
                            typeEnum
                        )
                    })
            }
        }

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
    }

    /**
     *
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        date = requireArguments().getSerializable(ARG_DATE) as Date
        type = getString(R.string.all_label)

        viewModel.monthlyReportDataLiveDataForAllTypesOfExpenses.observe(viewLifecycleOwner) { result ->
            binding?.monthlyReportFragmentProgressBar?.visibility = View.GONE
            binding?.monthlyReportFragmentContent?.visibility = View.VISIBLE

            when (result) {
                BreakDownViewModel.MonthlyBreakDownData.Empty -> {
                    lisOfExpenses.clear()
                    binding?.monthlyReportFragmentRecyclerView?.visibility = View.GONE
                    binding?.monthlyReportFragmentEmptyState?.visibility = View.VISIBLE

                    binding?.monthlyReportFragmentRevenuesTotalTv?.text =
                        CurrencyHelper.getFormattedCurrencyString(appPreferences, 0.0)
                    binding?.monthlyReportFragmentExpensesTotalTv?.text =
                        CurrencyHelper.getFormattedCurrencyString(appPreferences, 0.0)
                    binding?.monthlyReportFragmentBalanceTv?.text =
                        CurrencyHelper.getFormattedCurrencyString(appPreferences, 0.0)
                    binding?.monthlyReportFragmentBalanceTv?.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.budget_green
                        )
                    )
                    binding?.balancesContainer?.visibility = View.GONE
                }
                is BreakDownViewModel.MonthlyBreakDownData.Data -> {
                    lisOfExpenses.clear()
                    binding?.monthlyReportFragmentRecyclerView?.visibility = View.VISIBLE
                    binding?.monthlyReportFragmentEmptyState?.visibility = View.GONE
                    lisOfExpenses.addAll(result.allExpensesOfThisMonth)
                    initChart()

                    configureRecyclerView(
                        binding?.monthlyReportFragmentRecyclerView!!,
                        BreakDownRecyclerViewAdapter(
                            result.allExpensesOfThisMonth,
                            result.expenses,
                            result.revenues,
                            appPreferences
                        )
                    )

                    binding?.monthlyReportFragmentRevenuesTotalTv?.text =
                        CurrencyHelper.getFormattedCurrencyString(
                            appPreferences,
                            result.revenuesAmount
                        )
                    binding?.monthlyReportFragmentExpensesTotalTv?.text =
                        CurrencyHelper.getFormattedCurrencyString(
                            appPreferences,
                            result.expensesAmount
                        )

                    val balance = result.revenuesAmount - result.expensesAmount
                    binding?.monthlyReportFragmentBalanceTv?.text =
                        CurrencyHelper.getFormattedCurrencyString(appPreferences, balance)
                    binding?.monthlyReportFragmentBalanceTv?.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            if (balance >= 0) R.color.budget_green else R.color.budget_red
                        )
                    )
                    binding?.balancesContainer?.visibility =
                        if (lisOfExpenses.isEmpty()) View.GONE else View.VISIBLE
                }
            }
        }
        viewModel.loadDataForMonth(date, BreakdownType.getSelectedType(requireContext(), type))
        /**
         * Banner ads
         */
        if (appPreferences.getBoolean(PREMIUM_PARAMETER_KEY, false)) {
            binding?.adViewContainer?.visibility = View.GONE
        } else {
            loadAndDisplayBannerAds()
        }

        /**
         * Handle clicks for Add expenses
         */
        handleAddExpenses()
    }


    /**
     *
     */
    private fun handleAddExpenses() {
        binding?.fabNewExpense?.setOnClickListener {
            val startIntent = Intent(requireActivity(), ExpenseEditActivity::class.java)
            startIntent.putExtra("date", Date().time)
            startIntent.putExtra(MainActivity.ANIMATE_TRANSITION_KEY, false)
            ActivityCompat.startActivityForResult(
                requireActivity(),
                startIntent,
                MainActivity.ADD_EXPENSE_ACTIVITY_CODE,
                null
            )
        }

        binding?.fabNewRecurringExpense?.setOnClickListener {
            val startIntent = Intent(requireActivity(), RecurringExpenseEditActivity::class.java)
            startIntent.putExtra("dateStart", Date().time)
            startIntent.putExtra(MainActivity.ANIMATE_TRANSITION_KEY, false)
            ActivityCompat.startActivityForResult(
                requireActivity(),
                startIntent,
                MainActivity.ADD_EXPENSE_ACTIVITY_CODE,
                null
            )
        }
    }

    /**
     * Configure recycler view LayoutManager & adapter
     */
    private fun configureRecyclerView(
        recyclerView: RecyclerView,
        adapter: BreakDownRecyclerViewAdapter
    ) {
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    /**
     *
     */
    private fun initChart() {
        binding?.chart?.setUsePercentValues(true)
        binding?.chart?.description?.isEnabled = false
        if (lisOfExpenses.isNotEmpty())
            binding?.chart?.centerText = generateCenterSpannableText()
        binding?.chart?.setHoleColor(Color.WHITE)
        setData()
        binding?.chart?.setEntryLabelColor(Color.BLACK)
        binding?.chart?.setEntryLabelTextSize(12f)
        binding?.chart?.legend?.isEnabled = false
        binding?.emptyExpensesRecyclerViewPlaceholder?.visibility =
            if (lisOfExpenses.isEmpty()) View.VISIBLE else View.GONE
    }

    /**
     * Set data
     */
    private fun setData() {
        val values = ArrayList<PieEntry>()
        lisOfExpenses.forEach {
            values.add(PieEntry(it.amountSpend.toFloat(), it.category, it.amountSpend))
        }
        val dataSet = PieDataSet(values, "")
        dataSet.sliceSpace = 4f
        dataSet.selectionShift = 6f

        // add a lot of colors
        val colors = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)
        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.BLACK)
        binding?.chart?.data = data
        binding?.chart?.invalidate()
    }

    /**
     *
     */
    private fun generateCenterSpannableText(): SpannableString {
        val s = SpannableString(date.getMonthTitle(requireContext()))
        s.setSpan(RelativeSizeSpan(1.7f), 0, s.length, 0)
        s.setSpan(ForegroundColorSpan(Color.BLACK), 0, s.length, 0)
        s.setSpan(StyleSpan(Typeface.ITALIC), 0, s.length, 0)
        return s
    }

    /**
     *
     */
    companion object {
        fun newInstance(date: Date): BreakDownFragment = BreakDownFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_DATE, date)
            }
        }
    }

    /**
     *
     */
    private fun loadAndDisplayBannerAds() {
        try {
            binding?.adViewContainer?.visibility = View.VISIBLE
            val adSize: AdSize = AdSizeUtils.getAdSize(
                requireContext(),
                requireActivity().windowManager.defaultDisplay
            )!!
            adView = AdView(requireContext())
            adView?.adUnitId = getString(R.string.banner_ad_unit_id)
            binding?.adViewContainer?.addView(adView)
            val actualAdRequest = AdRequest.Builder()
                .build()
            adView?.setAdSize(adSize)
            adView?.loadAd(actualAdRequest)
            adView?.adListener = object : AdListener() {
                override fun onAdLoaded() {}
                override fun onAdOpened() {}
                override fun onAdClosed() {
                    loadAndDisplayBannerAds()
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Called when leaving the activity
     */
    override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    /**
     * Called when opening the activity
     */
    override fun onResume() {
        adView?.resume()
        super.onResume()
    }
}