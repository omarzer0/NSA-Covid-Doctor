package az.zero.nsacoviddoctor.common

import android.content.Context
import android.util.Log
import az.zero.nsacoviddoctor.R
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import es.dmoral.toasty.Toasty
import gun0912.tedimagepicker.builder.TedImagePicker


fun logMe(msg: String, tag: String = "TAG") {
    val showLog = true
    if (!showLog) return
    Log.e(tag, msg)
}

fun toastMy(
    context: Context,
    message: String,
    success: Boolean = false,
    hideInRelease: Boolean = false
) {
    if (hideInRelease) return
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

fun loadImage(): ShimmerDrawable {
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

fun pickImage(profile: Boolean, context: Context) {
    TedImagePicker.with(context)
        .title("Choose image")
        .backButton(R.drawable.ic_arrow_back_black_24dp)
        .showCameraTile(true)
        .buttonBackground(R.drawable.btn_done_button)
        .buttonTextColor(R.color.white)
        .buttonText("Choose image")
        .errorListener { throwable -> logMe(throwable.localizedMessage ?: "pickImage") }
        .start { uri ->
            // TODO add it to fragment
        }
}