package `in`.dimigo.dimigoin.ui.custom

import `in`.dimigo.dimigoin.R
import android.app.Dialog
import android.content.Context
import android.view.Window

class DimigoinProgressDialog(context: Context) {
    private val dialog = Dialog(context, R.style.ProgressDialog).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_progress)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    fun show() = dialog.show()

    fun stop() = dialog.dismiss()
}
