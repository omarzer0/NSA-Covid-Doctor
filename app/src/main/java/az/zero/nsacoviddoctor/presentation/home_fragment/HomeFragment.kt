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

        viewModel.covidPostsLiveData.observe(viewLifecycleOwner) { covidData ->
            when (covidData) {
                is Resource.Error -> {
                    logMe(covidData.message ?: "viewModel.covidPostsLiveData.observe")
                    toastMy("Check internet connection")
                    binding.spinKitPb.visibility = View.GONE
                }
                is Resource.Loading -> {
                    binding.spinKitPb.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    covidData.data?.data.let {
                        homeAdapter.submitList(it)
                    }
                    binding.spinKitPb.visibility = View.GONE
                }
            }
        }

    }
}