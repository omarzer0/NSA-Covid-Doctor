package az.zero.nsacoviddoctor.domain.model.covid_post

data class CovidPost(
    val description: String = "",
    val image: String = "",
    val slug: String,
    val title: String = "",
    val user_id: Int = -1,
    val post_id: Int = -1,
    val link: String = "",
    val date: String = "",
    val time: String = "",
)