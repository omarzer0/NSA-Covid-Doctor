package az.zero.nsacoviddoctor.core

sealed class ResponseState {
    data class Success<T>(val message: String, val data: T) : ResponseState()
    data class Error(val message: String?) : ResponseState()
    object Loading : ResponseState()
    object Empty : ResponseState()
}