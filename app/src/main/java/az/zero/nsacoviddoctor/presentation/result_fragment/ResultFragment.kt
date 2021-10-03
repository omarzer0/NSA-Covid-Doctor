package az.zero.nsacoviddoctor.presentation.result_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.core.BaseFragment
import az.zero.nsacoviddoctor.databinding.FragmentResultBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ResultFragment : BaseFragment(R.layout.fragment_result) {

    override val viewModel: ResultViewModel by viewModels()
    private lateinit var binding: FragmentResultBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentResultBinding.bind(view)

        val resultData = viewModel.resultData

        binding.apply {
            tvTextResult.text = resultData.title
            ivTestResult.setImageResource(resultData.image)
        }

    }

}