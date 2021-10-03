package az.zero.nsacoviddoctor.presentation.posts_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import az.zero.nsacoviddoctor.core.BaseViewModel
import az.zero.nsacoviddoctor.data.repository.CovidRepositoryImpl
import az.zero.nsacoviddoctor.domain.model.covid_post.CovidData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val repository: CovidRepositoryImpl
) : BaseViewModel() {



    private val covidPostsMutableLiveData = MutableLiveData<CovidData>()
    val covidPostsLiveData: LiveData<CovidData>
        get() = covidPostsMutableLiveData

    fun getAllCovidPosts() = viewModelScope.launch {
        covidPostsMutableLiveData.value = repository.getAllCovidPosts()
    }

}