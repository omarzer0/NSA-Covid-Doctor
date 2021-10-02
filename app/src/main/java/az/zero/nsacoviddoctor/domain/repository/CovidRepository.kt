package az.zero.nsacoviddoctor.domain.repository

interface CovidRepository {

    fun getFakeTest(): List<String>

}