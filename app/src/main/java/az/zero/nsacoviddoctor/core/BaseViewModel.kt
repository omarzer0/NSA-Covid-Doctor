package az.zero.nsacoviddoctor.core

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.common.Event
import az.zero.nsacoviddoctor.common.Status
import az.zero.nsacoviddoctor.common.logMe
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

    private val _status = MutableStateFlow<Event<Status>>(Event(Status.Empty))
    val status: StateFlow<Event<Status>>
        get() = _status

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch(Dispatchers.Main) {

            logMe("BaseViewModel exceptionHandler: ${throwable.localizedMessage}")

            val throwableMessage = throwable.localizedMessage
            val errorMessage =
                if (throwableMessage != null && throwableMessage.contains("No address associated with hostname"))
                    "Check your internet connection!"
                else application.getString(R.string.error)

            _status.value =
                Event(Status.Error(errorMessage))
        }
    }

    protected fun <T : BaseResponse> executeApi(
        action: suspend () -> Response<T>,
        response: (T) -> Unit,
        showDialog: Boolean = true
    ) {
        viewModelScope.launch(exceptionHandler) {
            if (showDialog) {
                _status.value = Event(Status.Loading)
            }
            val _response: Response<T> = action()
            println("log_test $_response")
            if (_response.isSuccessful) {
                logMe("success11111111111111111111111111")
                logMe("key = ${_response.body()!!.key}")

                response(_response.body()!!)
                _status.value =
                    Event(Status.Success("Success"))
            } else {
                logMe("${_response.code()} ${_response.message()}", "TAG_FOR_API")

                _status.value =
                    Event(
                        Status.Error(
                            application.getString(R.string.error)
                        )
                    )

            }
        }
    }


    protected fun <T> safeCallApi(
        action: suspend () -> Response<T>,
        response: (T) -> Unit,
        messageIfSuccess: String = "",
        showDialog: Boolean = true
    ) {
        viewModelScope.launch(exceptionHandler) {
            if (showDialog) {
                _status.value = Event(Status.Loading)
            }
            val callResponse: Response<T> = action()
            if (callResponse.isSuccessful) {
                val code = callResponse.code()
                logMe("BaseViewModel safeCallApi - if: $code")

                when (code) {
                    200 -> {
                        callResponse.body()?.let { response(it) }
                        _status.value = Event(Status.Success(messageIfSuccess))
                    }
                    401 -> {

                    }
                    404 -> {

                    }
                    else -> {
                    }
                }
            } else {
                logMe("BaseViewModel safeCallApi - else: ${callResponse.code()} ${callResponse.message()}")
                _status.value = Event(Status.Error(application.getString(R.string.error)))
            }
        }
    }


}