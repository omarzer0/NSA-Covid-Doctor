package az.zero.nsacoviddoctor.data.data_source.network

import az.zero.nsacoviddoctor.common.AI_MODEL_BASE_URL
import az.zero.nsacoviddoctor.common.POSTS_BASE_URL
import az.zero.nsacoviddoctor.domain.model.ai_model_data.AIModelData
import az.zero.nsacoviddoctor.domain.model.covid_info.CovidInfo
import az.zero.nsacoviddoctor.domain.model.covid_post.CovidData
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface CovidApi {

    @GET("countries/{country}?yesterday=true&strict=true")
    suspend fun getCovidInfo(
        @Path("country") country: String
    ): Response<CovidInfo>


    @GET("${POSTS_BASE_URL}posts")
    suspend fun getAllCovidPosts(
        @Query("category") category: String
    ): Response<CovidData>

    @Multipart
    @POST("${AI_MODEL_BASE_URL}upload")
    suspend fun getAIModelData(
        @Part image: MultipartBody.Part
    ): Response<AIModelData>


}