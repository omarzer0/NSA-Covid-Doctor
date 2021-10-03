package az.zero.nsacoviddoctor

import android.graphics.Color
import android.os.Bundle
import android.view.View
import az.zero.nsacoviddoctor.core.BaseFragment
import az.zero.nsacoviddoctor.core.BaseViewModel
import az.zero.nsacoviddoctor.databinding.FragmentStatisticsBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : BaseFragment(R.layout.fragment_statistics) {

    private lateinit var binding: FragmentStatisticsBinding
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStatisticsBinding.bind(view)

        var listBar: List<BarEntry> = listOf(
            BarEntry(2014f, 430f),
            BarEntry(2015f, 460f),
            BarEntry(2016f, 477f),
            BarEntry(2017f, 345f),
            BarEntry(2018f, 784f),
            BarEntry(2019f, 422f),
            BarEntry(2020f, 125f),
            BarEntry(2021f, 877f),
            BarEntry(2022f, 896f),
            BarEntry(2023f, 566f),
        )

        val barDataSet = BarDataSet(listBar, "covid by years")
        barDataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        barDataSet.valueTextColor = Color.BLUE
        barDataSet.valueTextSize = 16f

        val barData = BarData(barDataSet)
        binding.barChart.setFitBars(true)
        binding.barChart.data = barData
        binding.barChart.description.text = "covid chart bar"
        binding.barChart.animateY(2000)
    }
}

//in build.gradle module

// MP android chart graph
//implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'

// in statistics xml file

//<com.github.mikephil.charting.charts.BarChart
//android:layout_width="match_parent"
//android:layout_height="match_parent"
//android:id="@+id/bar_chart"
///>