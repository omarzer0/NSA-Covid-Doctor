package az.zero.nsacoviddoctor.presentation.result_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import az.zero.nsacoviddoctor.common.Resource
import az.zero.nsacoviddoctor.common.getResultMessages
import az.zero.nsacoviddoctor.common.logMe
import az.zero.nsacoviddoctor.core.BaseViewModel
import az.zero.nsacoviddoctor.data.repository.CovidRepositoryImpl
import az.zero.nsacoviddoctor.domain.model.covid_info.CovidInfo
import az.zero.nsacoviddoctor.domain.model.result_data.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val state: SavedStateHandle
) : BaseViewModel() {

    val resultData = state.get<ResultData>("resultData") ?: getResultMessages("NORMAL")
}