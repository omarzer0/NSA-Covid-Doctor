package az.zero.nsacoviddoctor.core

import android.content.Context
import android.net.Uri
import androidx.fragment.app.Fragment
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.common.IS_DEBUG
import az.zero.nsacoviddoctor.common.logMe
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import gun0912.tedimagepicker.builder.TedImagePicker

@AndroidEntryPoint
abstract class BaseFragment(layout: Int) : Fragment(layout) {

    abstract val viewModel: BaseViewModel

    fun pickImage(action: (Uri) -> Unit) {
        TedImagePicker.with(requireContext())
            .title("Choose image")
            .backButton(R.drawable.ic_arrow_back_black_24dp)
            .showCameraTile(true)
            .buttonBackground(R.drawable.btn_done_button)
            .buttonTextColor(R.color.white)
            .buttonText("Choose image")
            .errorListener { throwable -> logMe(throwable.localizedMessage ?: "pickImage") }
            .start { uri ->
                action(uri)
            }
    }

    fun toastMy(
        message: String,
        success: Boolean = false,
        hideInRelease: Boolean = false
    ) {
        if (hideInRelease && !IS_DEBUG) return
        if (success) {
            Toasty.success(
                requireContext(), message, Toasty.LENGTH_SHORT, true
            ).show()
        } else {
            Toasty.error(
                requireContext(), message, Toasty.LENGTH_SHORT, true
            ).show()
        }
    }
}