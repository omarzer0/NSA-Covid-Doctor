package az.zero.nsacoviddoctor.core

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.common.Event
import az.zero.nsacoviddoctor.common.toastMy
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

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch(Dispatchers.Main) {
            toastMy(
                application, "${throwable.localizedMessage} exceptionHandler",
                success = false,
                hideInRelease = true
            )
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

}