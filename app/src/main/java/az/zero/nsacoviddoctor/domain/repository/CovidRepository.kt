package az.zero.nsacoviddoctor.domain.repository

import az.zero.nsacoviddoctor.domain.model.covid_info.CovidInfo
import az.zero.nsacoviddoctor.domain.model.covid_post.CovidData

interface CovidRepository {

    suspend fun getCovidInfo(country:String): CovidInfo

    suspend fun getAllCovidPosts(): CovidData

}