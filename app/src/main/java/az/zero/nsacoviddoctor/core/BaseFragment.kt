package az.zero.nsacoviddoctor.core

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.common.IS_DEBUG
import az.zero.nsacoviddoctor.common.Status
import az.zero.nsacoviddoctor.common.logMe
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
abstract class BaseFragment(layout: Int) : Fragment(layout) {

    abstract val viewModel: BaseViewModel

    private var dialog: ProgressDialog? = null


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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog = ProgressDialog.show(
            requireContext(), "Loading",
            "Loading. Please wait...", true
        )

        hideDialog()
        viewState()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun viewState() {
        lifecycleScope.launchWhenStarted {
            viewModel.status.collect { event ->
                event.getContentIfNotHandled()?.let {
                    when (it) {
                        is Status.Success -> {
                            logMe("BaseFragment viewState Success")

                            hideDialog()
                            if (it.message != "") {
                                toastMy(it.message, true)
                            }
                        }
                        is Status.Error -> {
                            hideDialog()
                            logMe("BaseFragment viewState Error")
                            if (it.message == null) {
                                toastMy("connection error", false)
                            } else {
                                if (it.message != "") {
                                    toastMy(it.message, false)
                                }
                            }
                        }
                        is Status.Loading -> {
                            logMe("BaseFragment viewState Loading")
                            showDialog()
                        }
                        else -> {
                            logMe("BaseFragment viewState Empty")
                        }
                    }
                }
            }
        }
    }

    private fun showDialog() {
        dialog?.show()

    }

    private fun hideDialog() {
        dialog?.hide()
    }

    fun openBrowser(url: String?) {
        if (url.isNullOrEmpty()) {
            toastMy("url can't be empty!", false)
        } else {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            try {
                requireContext().startActivity(browserIntent)
            } catch (e: Exception) {
                toastMy(resources.getString(R.string.error), false)
            }
        }
    }
}