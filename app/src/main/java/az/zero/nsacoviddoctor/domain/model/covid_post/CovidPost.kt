package az.zero.nsacoviddoctor.domain.model.covid_post

data class CovidPost(
    val description: String = "",
    val image: String = "",
    val slug: String,
//    val social_links:String,
    val title: String = "",
    val user_id: Int = -1
)