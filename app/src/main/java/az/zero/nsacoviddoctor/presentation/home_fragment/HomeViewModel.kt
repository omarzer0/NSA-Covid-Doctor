package az.zero.nsacoviddoctor.presentation.home_fragment

import az.zero.nsacoviddoctor.core.BaseViewModel
import az.zero.nsacoviddoctor.data.repository.CovidRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CovidRepositoryImpl
) : BaseViewModel() {


    val x = repository.getFakeTest()

}