package az.zero.nsacoviddoctor.data.repository

import az.zero.nsacoviddoctor.data.data_source.network.CovidApi
import az.zero.nsacoviddoctor.domain.model.covid_info.CovidInfo
import az.zero.nsacoviddoctor.domain.model.covid_post.CovidData
import az.zero.nsacoviddoctor.domain.repository.CovidRepository
import retrofit2.Response
import javax.inject.Inject

class CovidRepositoryImpl @Inject constructor(
    private val api: CovidApi
) : CovidRepository {

    override suspend fun getCovidInfo(country: String): CovidInfo = api.getCovidInfo(country)

    override suspend fun getAllCovidPosts(): CovidData = api.getAllCovidPosts()

}