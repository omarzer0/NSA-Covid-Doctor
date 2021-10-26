package az.zero.nsacoviddoctor.domain.repository

import az.zero.nsacoviddoctor.common.Resource
import az.zero.nsacoviddoctor.domain.model.ai_model_data.AIModelData
import az.zero.nsacoviddoctor.domain.model.covid_info.CovidInfo
import az.zero.nsacoviddoctor.domain.model.covid_post.CovidData
import okhttp3.MultipartBody
import retrofit2.Response

interface CovidRepository {

    suspend fun getCovidInfo(country: String): Response<CovidInfo>

    suspend fun getAllCovidPosts(category: String): Response<CovidData>

    suspend fun getAIModelData(image: MultipartBody.Part): Response<AIModelData>

}