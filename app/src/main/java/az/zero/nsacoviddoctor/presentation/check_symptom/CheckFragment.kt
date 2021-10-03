package az.zero.nsacoviddoctor.presentation.check_symptom

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.common.Resource
import az.zero.nsacoviddoctor.common.getImageAsMultipartBodyPart
import az.zero.nsacoviddoctor.common.logMe
import az.zero.nsacoviddoctor.common.setImageUsingGlide
import az.zero.nsacoviddoctor.core.BaseFragment
import az.zero.nsacoviddoctor.databinding.FragmentCheckBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckFragment : BaseFragment(R.layout.fragment_check) {

    override val viewModel: CheckViewModel by viewModels()
    private lateinit var binding: FragmentCheckBinding
    private var uri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCheckBinding.bind(view)

        binding.checkMeBtn.setOnClickListener {
            pickImage {
                uri = it
                uri?.let { imageUri ->
                    val multiPart = getImageAsMultipartBodyPart(requireContext(), imageUri, "image")
                    viewModel.getAIModelData(multiPart)
                    setImageUsingGlide(binding.imageView2, imageUri.toString())
                }
            }
        }

        viewModel.aiModelDataLiveData.observe(viewLifecycleOwner) { aiModelData ->
            when (aiModelData) {
                is Resource.Error -> {
                    logMe(aiModelData.message ?: "viewModel.covidPostsLiveData.observe")
                    toastMy("something went wrong please check internet connection and try again")
                    binding.spinKitPb.visibility = View.GONE
                }
                is Resource.Loading -> {
                    binding.spinKitPb.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.spinKitPb.visibility = View.GONE
                    val x = aiModelData.data?.data
                    logMe("$x")
                }
            }
        }

    }

}