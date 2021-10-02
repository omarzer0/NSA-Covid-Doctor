package az.zero.nsacoviddoctor.data.repository

import az.zero.nsacoviddoctor.data.data_source.network.CovidApi
import az.zero.nsacoviddoctor.domain.repository.CovidRepository
import javax.inject.Inject

class CovidRepositoryImpl @Inject constructor(
    private val api: CovidApi
) : CovidRepository {

    override fun getFakeTest(): List<String> {
        return List(100) {
            "${it + 1}"
        }
    }

}