package az.zero.nsacoviddoctor.presentation.home_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import az.zero.nsacoviddoctor.common.PREVENTION
import az.zero.nsacoviddoctor.common.Status
import az.zero.nsacoviddoctor.core.BaseViewModel
import az.zero.nsacoviddoctor.data.repository.CovidRepositoryImpl
import az.zero.nsacoviddoctor.domain.model.covid_post.CovidData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CovidRepositoryImpl
) : BaseViewModel() {


    private val covidPostsMutableLiveData = MutableLiveData<CovidData>()
    val covidPostsLiveData: LiveData<CovidData>
        get() = covidPostsMutableLiveData

    fun getAllCovidPosts() = safeCallApiWithLiveData(
        {
            repository.getAllCovidPosts(PREVENTION)
        }, {
            covidPostsMutableLiveData.value = it
        }
    )

}