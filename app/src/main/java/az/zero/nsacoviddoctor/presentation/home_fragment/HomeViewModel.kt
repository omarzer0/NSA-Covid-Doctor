package az.zero.nsacoviddoctor.presentation.home_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import az.zero.nsacoviddoctor.common.PREVENTION
import az.zero.nsacoviddoctor.common.Resource
import az.zero.nsacoviddoctor.common.logMe
import az.zero.nsacoviddoctor.core.BaseViewModel
import az.zero.nsacoviddoctor.data.repository.CovidRepositoryImpl
import az.zero.nsacoviddoctor.domain.model.covid_post.CovidData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CovidRepositoryImpl
) : BaseViewModel() {


    private val covidPostsMutableLiveData = MutableLiveData<Resource<CovidData>>()
    val covidPostsLiveData: LiveData<Resource<CovidData>>
        get() = covidPostsMutableLiveData

    fun getAllCovidPosts() = viewModelScope.launch {
        covidPostsMutableLiveData.value = Resource.Loading()
        try {
            val call = repository.getAllCovidPosts(PREVENTION)

            if (call.isSuccessful) {
                call.body()?.let { covidInfo ->
                    covidPostsMutableLiveData.value = Resource.Success(covidInfo)
                }
            }
        } catch (e: Exception) {
            logMe("${e.localizedMessage}")
            covidPostsMutableLiveData.value = Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

}