package az.zero.nsacoviddoctor.presentation.statistics_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import az.zero.nsacoviddoctor.core.BaseViewModel
import az.zero.nsacoviddoctor.data.repository.CovidRepositoryImpl
import az.zero.nsacoviddoctor.domain.model.covid_info.CovidInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: CovidRepositoryImpl
) : BaseViewModel() {

    private val covidInfoMutableLiveData = MutableLiveData<CovidInfo>()
    val covidInfoLiveData: LiveData<CovidInfo>
        get() = covidInfoMutableLiveData


    fun getCovidInfo(country: String) = safeCallApi(
        {
            repository.getCovidInfo(country)
        }, {
            covidInfoMutableLiveData.value = it
        }
    )

}