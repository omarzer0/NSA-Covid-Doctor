package az.zero.nsacoviddoctor.data.data_source.network

import az.zero.nsacoviddoctor.common.POSTS_BASE_URL
import az.zero.nsacoviddoctor.domain.model.covid_info.CovidInfo
import az.zero.nsacoviddoctor.domain.model.covid_post.CovidData
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidApi {

    @GET("countries/{country}?yesterday=true&strict=true")
    suspend fun getCovidInfo(
        @Path("country") country: String
    ): CovidInfo


    @GET("${POSTS_BASE_URL}posts")
    suspend fun getAllCovidPosts(): CovidData

}