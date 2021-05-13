package android.example.blowonboredom.utils

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showToast(message: String?) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showSnackBar(text: String) {
    val snackBar: Snackbar = Snackbar.make(this.requireView(), text, Snackbar.LENGTH_SHORT)
    snackBar.show()
}

fun Fragment.showSnackBar(
        text: String,
        btnText: String,
        action: () -> Unit,
        actionOnDismiss: () -> Unit
) {
    val snackBar: Snackbar = Snackbar.make(this.requireView(), text, Snackbar.LENGTH_LONG)
    snackBar.setAction(btnText) {
        action()
    }
    snackBar.addCallback(object : Snackbar.Callback() {
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            super.onDismissed(transientBottomBar, event)
            if (event == DISMISS_EVENT_TIMEOUT) {
                actionOnDismiss()
            }
        }

    })
    snackBar.animationMode = Snackbar.ANIMATION_MODE_SLIDE
    snackBar.show()
}