package az.zero.nsacoviddoctor.presentation.check_symptom

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.common.*
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
                    val multiPart = getImageAsMultipartBodyPart(
                        requireContext(),
                        imageUri,
                        IMAGE_MULTI_PART_KEY
                    )
                    viewModel.getAIModelData(multiPart)
                }
            }
        }

        viewModel.aiModelDataLiveData.observe(viewLifecycleOwner) { aiModelData ->
            when (aiModelData) {
                is Resource.Error -> {
                    logMe(aiModelData.message ?: "viewModel.covidPostsLiveData.observe")
                    toastMy("something went wrong please try again")
                    binding.spinKitPb.visibility = View.GONE
                }
                is Resource.Loading -> {
                    binding.spinKitPb.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.spinKitPb.visibility = View.GONE
                    val result = aiModelData.data?.data
                    logMe("$result")
                    val resultData = getResultMessages(result ?: "NORMAL")
                    val action =
                        CheckFragmentDirections.actionCheckFragmentToResultFragment(resultData)

                    findNavController().navigate(action)
                }
            }
        }

    }

}