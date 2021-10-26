package az.zero.nsacoviddoctor.presentation.home_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.common.Resource
import az.zero.nsacoviddoctor.common.logMe
import az.zero.nsacoviddoctor.core.BaseFragment
import az.zero.nsacoviddoctor.databinding.FragmentHomeBinding
import az.zero.nsacoviddoctor.presentation.adapter.post_adapter.HomePostAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val homeAdapter = HomePostAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        viewModel.getAllCovidPosts()

        binding.preventionRv.apply {
            adapter = homeAdapter
        }

        homeAdapter.setOnPostClickListener {
            openBrowser(it.link)
        }

        binding.seeGuidelinesBtn.setOnClickListener {
            openBrowser("http://space-app.spider-te8.com/guidelines")
        }

        viewModel.covidPostsLiveData.observe(viewLifecycleOwner) { covidData ->
            logMe("${covidData.data} \n\n ${covidData.msg}")
            homeAdapter.submitList(covidData.data)
        }

    }
}