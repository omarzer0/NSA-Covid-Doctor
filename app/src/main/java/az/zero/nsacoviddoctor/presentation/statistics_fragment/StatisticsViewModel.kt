package az.zero.nsacoviddoctor.presentation.statistics_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import az.zero.nsacoviddoctor.common.Resource
import az.zero.nsacoviddoctor.common.logMe
import az.zero.nsacoviddoctor.core.BaseViewModel
import az.zero.nsacoviddoctor.data.repository.CovidRepositoryImpl
import az.zero.nsacoviddoctor.domain.model.covid_info.CovidInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: CovidRepositoryImpl
) : BaseViewModel() {

    private val covidInfoMutableLiveData = MutableLiveData<Resource<CovidInfo>>()
    val covidInfoLiveData: LiveData<Resource<CovidInfo>>
        get() = covidInfoMutableLiveData

    fun getCovidInfo(country: String) = viewModelScope.launch {
        covidInfoMutableLiveData.value = Resource.Loading()
        try {
            val call = repository.getCovidInfo(country)
            if (call.isSuccessful) {
                call.body()?.let { covidInfo ->
                    covidInfoMutableLiveData.value = Resource.Success(covidInfo)
                }
            }
        } catch (e: Exception) {
            logMe("${e.localizedMessage}")
            covidInfoMutableLiveData.value = Resource.Error(e.localizedMessage?:"Unknown error")
        }
    }

}