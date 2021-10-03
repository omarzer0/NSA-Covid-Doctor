package az.zero.nsacoviddoctor.domain.repository

import az.zero.nsacoviddoctor.domain.model.covid_info.CovidInfo

interface CovidRepository {

    suspend fun getCovidInfo(country:String): CovidInfo

}