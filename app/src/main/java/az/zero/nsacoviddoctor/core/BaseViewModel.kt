package az.zero.nsacoviddoctor.core

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.common.Event
import az.zero.nsacoviddoctor.common.Resource
import az.zero.nsacoviddoctor.domain.model.covid_info.CovidInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var application: Application

    private val _status = MutableStateFlow<Event<ResponseState>>(Event(ResponseState.Empty))
    val status: StateFlow<Event<ResponseState>>
        get() = _status

//    protected val _callState = MutableLiveData<Resource<*>>()
//    val callState: LiveData<Resource<*>>
//        get() = _callState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch(Dispatchers.Main) {
            _status.value =
                Event(ResponseState.Error(application.getString(R.string.error)))
        }
    }

    protected fun <T : BaseResponse> safeCallApi(
        action: suspend () -> Response<T>,
        response: (T) -> Unit,
        showDialog: Boolean = true
    ) {
        viewModelScope.launch(exceptionHandler) {
            if (showDialog) {
                _status.value = Event(ResponseState.Loading)
            }
            val _response: Response<T> = action()
            if (_response.isSuccessful) {
                when (_response.body()!!.key) {
                    "success" -> {
                        response(_response.body()!!)
                        _status.value = Event(
                            ResponseState.Success(_response.body()!!.msg!!, _response.body()!!)
                        )
                    }

                    else -> {
                        _status.value = Event(ResponseState.Error(_response.body()!!.msg!!))
                    }
                }
            } else {
                _status.value = Event(ResponseState.Error(application.getString(R.string.error)))
            }
        }
    }

//    protected fun <T : BaseResponse> myApiCall(
//        action: suspend () -> Response<T>,
//        response: (T) -> Unit,
//    ) {
//        viewModelScope.launch {
//            _callState.value = Resource.Loading()
//            try {
//                val call: Response<T> = action()
//                if (call.isSuccessful){
//                    call.body()?.let {
//                        when (call) {
//                            is Resource.Loading<*> -> {
//                            }
//                            is Resource.Error<*> -> _callState.value =Resource.Error(call.message ?: "Unknown error")
//                            is Resource.Success<*> -> {
//                                call.data
//                                _callState.value = Resource.Success(it)
//                            }
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//
//            }
//        }
//    }

    //fun getCovidInfo(country: String) = viewModelScope.launch {
    //        covidInfoMutableLiveData.value = Resource.Loading()
    //        try {
    //            val x = repository.getCovidInfo(country)
    //            if (x.isSuccessful) {
    //                x.body()?.let { covidInfo ->
    //                    covidInfoMutableLiveData.value = Resource.Success(covidInfo)
    //                }
    //            }
    //        } catch (e: Exception) {
    //            logMe("${e.localizedMessage}")
    //            covidInfoMutableLiveData.value = Resource.Error(e.localizedMessage ?: "Unknown error")
    //        }
    //    }

}