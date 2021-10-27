package az.zero.nsacoviddoctor.presentation.check_symptom

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.common.IMAGE_MULTI_PART_KEY
import az.zero.nsacoviddoctor.common.getImageAsMultipartBodyPart
import az.zero.nsacoviddoctor.common.getResultMessages
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
            val result = aiModelData.data
            val resultData = getResultMessages(msg = result)
            val action =
                CheckFragmentDirections.actionCheckFragmentToResultFragment(resultData)

            findNavController().navigate(action)
        }

    }

}