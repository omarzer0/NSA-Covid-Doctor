package az.zero.nsacoviddoctor.data.repository

import az.zero.nsacoviddoctor.common.Resource
import az.zero.nsacoviddoctor.data.data_source.network.CovidApi
import az.zero.nsacoviddoctor.domain.model.ai_model_data.AIModelData
import az.zero.nsacoviddoctor.domain.model.covid_info.CovidInfo
import az.zero.nsacoviddoctor.domain.model.covid_post.CovidData
import az.zero.nsacoviddoctor.domain.repository.CovidRepository
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class CovidRepositoryImpl @Inject constructor(
    private val api: CovidApi
) : CovidRepository {

    override suspend fun getCovidInfo(country: String): Response<CovidInfo> =
        api.getCovidInfo(country)

    override suspend fun getAllCovidPosts(category: String): Response<CovidData> =
        api.getAllCovidPosts(category)

    override suspend fun getAIModelData(image: MultipartBody.Part): Response<AIModelData> =
        api.getAIModelData(image)

}