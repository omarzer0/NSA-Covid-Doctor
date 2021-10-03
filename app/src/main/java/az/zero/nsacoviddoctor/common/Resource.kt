package az.zero.nsacoviddoctor.common

// Generic sealed class wrapper for network calls
// differentiate between success and failure responses
// and for handling loading data states
// takes the body (data) of nullable generic type and nullable String response message
sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    // Success class returns a non nullable data which was fetched from retrofit
    // defines that the response was correct and data was sent
    class Success<T>(data: T) : Resource<T>(data)

    // Error class returns a non nullable message which defines what when wrong
    // optionally can take data as a pram if needed
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    // Loading class notifies when the call was done and response was received (success or error)
    class Loading<T> : Resource<T>()
}