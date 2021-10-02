package az.zero.nsacoviddoctor.presentation.home_fragment

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.common.getLocation
import az.zero.nsacoviddoctor.core.BaseFragment
import az.zero.nsacoviddoctor.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        var uri: Uri?

        binding.chooseImageBtn.setOnClickListener {
            pickImage {
                uri = it
                toastMy("$uri", success = true)
            }
        }
        val x = getLocation(requireContext())
        toastMy("$x", true)

    }
}