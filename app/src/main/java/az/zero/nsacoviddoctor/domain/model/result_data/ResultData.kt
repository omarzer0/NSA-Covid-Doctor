package az.zero.nsacoviddoctor.domain.model.result_data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultData(
    val title: String,
//    val content: String,
    val image: Int
) : Parcelable