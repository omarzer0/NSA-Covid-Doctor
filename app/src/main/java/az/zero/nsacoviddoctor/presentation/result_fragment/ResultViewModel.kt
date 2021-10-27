package az.zero.nsacoviddoctor.presentation.result_fragment

import androidx.lifecycle.SavedStateHandle
import az.zero.nsacoviddoctor.common.getResultMessages
import az.zero.nsacoviddoctor.core.BaseViewModel
import az.zero.nsacoviddoctor.domain.model.result_data.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val state: SavedStateHandle
) : BaseViewModel() {

    val resultData = state.get<ResultData>("resultData") ?: getResultMessages("NORMAL")
}