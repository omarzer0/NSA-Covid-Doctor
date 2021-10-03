package az.zero.nsacoviddoctor.presentation.posts_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import az.zero.nsacoviddoctor.R
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
//            openBrowser(it.url)
        }

        viewModel.covidPostsLiveData.observe(viewLifecycleOwner) {
            val postList = it.data
            postAdapter.submitList(postList)
        }

    }

}