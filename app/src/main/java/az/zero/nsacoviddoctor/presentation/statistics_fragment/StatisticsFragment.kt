package az.zero.nsacoviddoctor.presentation.statistics_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.common.getLocation
import az.zero.nsacoviddoctor.common.logMe
import az.zero.nsacoviddoctor.core.BaseFragment
import az.zero.nsacoviddoctor.databinding.FragmentStatisticsBinding
import az.zero.nsacoviddoctor.domain.model.covid_info.CovidInfo
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
            setDataToViews(covidInfo)
        }

    }

    private fun setDataToViews(covidInfo: CovidInfo) {
        binding.apply {
            affectedNumbersTv.text = covidInfo.cases.toString()
            deathNumbersTv.text = covidInfo.deaths.toString()
            recoveredNumbersTv.text = covidInfo.recovered.toString()
            activeNumbersTv.text = covidInfo.active.toString()
            seriousNumbersTv.text = covidInfo.critical.toString()
        }
    }
}