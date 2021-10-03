package az.zero.nsacoviddoctor.presentation.statistics_fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.common.Resource
import az.zero.nsacoviddoctor.common.getLocation
import az.zero.nsacoviddoctor.common.logMe
import az.zero.nsacoviddoctor.common.setImageUsingGlide
import az.zero.nsacoviddoctor.core.BaseFragment
import az.zero.nsacoviddoctor.databinding.FragmentStatisticsBinding
import az.zero.nsacoviddoctor.domain.model.covid_info.CovidInfo
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StatisticsFragment : BaseFragment(R.layout.fragment_statistics) {

    override val viewModel: StatisticsViewModel by viewModels()
    private lateinit var binding: FragmentStatisticsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStatisticsBinding.bind(view)

        val x = getLocation(requireContext()).lowercase()

        viewModel.getCovidInfo(x)
        viewModel.covidInfoLiveData.observe(viewLifecycleOwner) { covidInfo ->
            when (covidInfo) {
                is Resource.Error -> {
                    logMe(covidInfo.message ?: "viewModel.covidPostsLiveData.observe")
                    toastMy("Check internet connection")
                    binding.spinKitPb.visibility = View.GONE
                }
                is Resource.Loading -> {
                    binding.spinKitPb.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    setDataToViews(covidInfo.data)
                    binding.spinKitPb.visibility = View.GONE
                }
            }
        }

        val listPie: List<PieEntry> = listOf(
            PieEntry(500f, "2010"),
            PieEntry(500f, "2010"),
            PieEntry(500f, "2010"),
            PieEntry(500f, "2010"),
            PieEntry(500f, "2010"),
        )
        val pieChart = binding.barChart
        val pieDataSet = PieDataSet(listPie, "test")
        pieDataSet.apply {
            colors = ColorTemplate.MATERIAL_COLORS.asList()
            valueTextColor = Color.WHITE
            valueTextSize = 16f
        }

        val pieData = PieData(pieDataSet)

        pieChart.apply {
            data = pieData
            description.isEnabled = false
            centerText = "Covid"
            animateX(1000)
            animateY(1000)
        }


    }

    private fun setDataToViews(covidInfo: CovidInfo?) {
        if (covidInfo == null) return
        binding.apply {
            affectedNumbersTv.text = covidInfo.cases.toString()
            deathNumbersTv.text = covidInfo.deaths.toString()
            recoveredNumbersTv.text = covidInfo.recovered.toString()
            activeNumbersTv.text = covidInfo.active.toString()
            seriousNumbersTv.text = covidInfo.critical.toString()
            setImageUsingGlide(countryImageIv, covidInfo.countryInfo.flag)
        }
    }
}