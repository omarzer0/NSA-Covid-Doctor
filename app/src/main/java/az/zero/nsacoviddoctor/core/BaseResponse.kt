package az.zero.nsacoviddoctor.core

open class BaseResponse(
    // it is called key not status
    val key: String? = "",
    val msg: String? = "",
    val code: Int? = 200
)