package az.zero.nsacoviddoctor.data.data_source.network

import az.zero.nsacoviddoctor.domain.model.covid_info.CovidInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidApi {

    @GET("countries/{country}?yesterday=true&strict=true")
    suspend fun getCovidInfo(
        @Path("country") country: String
    ): CovidInfo


}