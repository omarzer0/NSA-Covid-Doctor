package az.zero.nsacoviddoctor.domain.model.covid_post

import az.zero.nsacoviddoctor.core.BaseResponse

data class CovidData(
    val data : List<CovidPost>
): BaseResponse()