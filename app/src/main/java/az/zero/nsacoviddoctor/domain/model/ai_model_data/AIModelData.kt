package az.zero.nsacoviddoctor.domain.model.ai_model_data

import az.zero.nsacoviddoctor.core.BaseResponse

data class AIModelData(
    val data: String = "NORMAL"
) : BaseResponse()
