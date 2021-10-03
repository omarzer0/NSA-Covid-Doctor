package az.zero.nsacoviddoctor.common

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.domain.model.result_data.ResultData
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import es.dmoral.toasty.Toasty
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


fun logMe(msg: String, tag: String = "TAG") {
    val showLog = IS_DEBUG
    if (!showLog) return
    Log.e(tag, msg)
}

fun toastMy(
    context: Context,
    message: String,
    success: Boolean = false,
    hideInRelease: Boolean = false
) {
    if (hideInRelease && !IS_DEBUG) return
    if (success) {
        Toasty.success(
            context, message, Toasty.LENGTH_SHORT, true
        ).show()
    } else {
        Toasty.error(
            context, message, Toasty.LENGTH_SHORT, true
        ).show()
    }
}

fun getImageAsMultipartBodyPart(
    context: Context?,
    uri: Uri,
    name: String
): MultipartBody.Part {
    val path: String = RealPathUtil.getRealPath(context, uri)
    val file = File(path)
    val reqFileSelect = file.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(name, file.name, reqFileSelect)
}

fun getShimmerDrawable(): ShimmerDrawable {
    val shimmer =
        Shimmer.AlphaHighlightBuilder()
            .setDuration(1800)
            .setBaseAlpha(0.7f)
            .setHighlightAlpha(0.6f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

    return ShimmerDrawable().apply {
        setShimmer(shimmer)
    }
}

fun setImageUsingGlide(view: ImageView, imageUrl: String?) {
    val context = view.context
    val shimmerDrawable = getShimmerDrawable()
    Glide.with(context)
        .load(imageUrl)
        .placeholder(shimmerDrawable)
        .error(R.drawable.ic_no_image)
        .into(view)
}

fun getLocation(context: Context) = context.resources.configuration.locale.country ?: ""

fun getResultMessages(msg: String): ResultData {
    return when (msg) {
        "NORMAL" -> {
            ResultData("You don't have Coronavirus", R.drawable.corona_free)
        }
        "PNEUMONIA" -> {
            ResultData(
                "You don't have Coronavirus, but you properly have pneumonia",
                R.drawable.corona_free
            )
        }
        "COVID" -> {
            ResultData(
                "Looks like you have Corona symptoms, please see a doctor! ",
                R.drawable.have_corona
            )
        }
        else -> throw Exception("message have a strange value")
    }
}