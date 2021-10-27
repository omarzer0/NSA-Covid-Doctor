package az.zero.nsacoviddoctor.common

sealed class Status {
    data class Success(val message: String) : Status()
    data class Error(val message: String?) : Status()
    object Loading : Status()
    object Empty : Status()
}