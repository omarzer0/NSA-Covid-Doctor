package az.zero.nsacoviddoctor.presentation.posts_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.common.Resource
import az.zero.nsacoviddoctor.common.logMe
import az.zero.nsacoviddoctor.core.BaseFragment
import az.zero.nsacoviddoctor.databinding.FragmentPostsBinding
import az.zero.nsacoviddoctor.presentation.adapter.post_adapter.PostAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostsFragment : BaseFragment(R.layout.fragment_posts) {

    override val viewModel: PostsViewModel by viewModels()
    private lateinit var binding: FragmentPostsBinding
    private val postAdapter = PostAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostsBinding.bind(view)

        viewModel.getAllCovidPosts()

        binding.postsRv.apply {
            adapter = postAdapter
            setHasFixedSize(true)
        }

        postAdapter.setOnPostClickListener {
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
                        postAdapter.submitList(it)
                    }
                    binding.spinKitPb.visibility = View.GONE
                }
            }
        }

    }

}