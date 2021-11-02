package az.zero.nsacoviddoctor.presentation.home_fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.common.Status
import az.zero.nsacoviddoctor.core.BaseFragment
import az.zero.nsacoviddoctor.databinding.FragmentHomeBinding
import az.zero.nsacoviddoctor.presentation.adapter.post_adapter.HomePostAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


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
            homeAdapter.submitList(covidData.data)
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Status.Empty -> {
                }
                is Status.Error -> {
                    binding.spinKitPb.isVisible = false
                    it.message?.let { errorMessage -> toastMy(errorMessage) }
                }
                is Status.Loading -> binding.spinKitPb.isVisible = true
                is Status.Success -> binding.spinKitPb.isVisible = false
            }
        }

//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            viewModel.status.collectLatest { event ->
//                event.getContentIfNotHandled()?.let {
//                    when (it) {
//                        is Status.Empty -> {
//                        }
//                        is Status.Error -> {
//                            binding.spinKitPb.isVisible = false
//                            it.message?.let { errorMessage -> toastMy(errorMessage) }
//                        }
//                        is Status.Loading -> binding.spinKitPb.isVisible = true
//                        is Status.Success -> binding.spinKitPb.isVisible = false
//                    }
//                }
//            }
//        }
    }
}