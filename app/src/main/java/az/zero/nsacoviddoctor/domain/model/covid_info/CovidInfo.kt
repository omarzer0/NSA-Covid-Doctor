package az.zero.nsacoviddoctor.domain.model.covid_info

data class CovidInfo(
    val active: Int,
    val critical: Int,
    val deaths: Int,
    val recovered: Int,
    val cases: Int,

    val activePerOneMillion: Double,
    val casesPerOneMillion: Int,
    val continent: String,
    val country: String,
    val countryInfo: CountryInfo,
    val criticalPerOneMillion: Double,
    val deathsPerOneMillion: Int,
    val oneCasePerPeople: Int,
    val oneDeathPerPeople: Int,
    val oneTestPerPeople: Int,
    val population: Int,
    val recoveredPerOneMillion: Double,
    val tests: Int,
    val testsPerOneMillion: Int,
    val todayCases: Int,
    val todayDeaths: Int,
    val todayRecovered: Int,
    val updated: Long
)