package `in`.dimigo.dimigoin.ui.custom

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.DialogBaseBinding
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

class DimigoinDialog(private val context: Context) {
    inner class CustomView(
        private val view: View,
        private val cancelable: Boolean = true,
        @ColorRes private val colorId: Int = R.color.pink_400
    ) {
        private var useDoubleButton = false
        private var positiveButtonTextId: Int? = null
        private var onPositiveButtonClick: (() -> Unit)? = null
        private var negativeButtonTextId: Int? = null
        private var onNegativeButtonClick: (() -> Unit)? = null

        private val accentColor = ContextCompat.getColor(context, colorId)

        fun usePositiveButton(@StringRes textId: Int? = null, onClick: (() -> Unit)? = null) {
            if (textId != null) positiveButtonTextId = textId
            onPositiveButtonClick = onClick
        }

        fun useNegativeButton(@StringRes textId: Int? = null, onClick: (() -> Unit)? = null) {
            useDoubleButton = true
            if (textId != null) negativeButtonTextId = textId
            onNegativeButtonClick = onClick
        }

        fun show() {
            val dialog = AlertDialog.Builder(context)
                .setCancelable(cancelable)
                .create()
            val view = createView(dialog)
            dialog.apply {
                setView(view)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                show()
            }
        }

        @SuppressLint("InflateParams")
        private fun createView(dialog: Dialog): View {
            val binding = DialogBaseBinding.inflate(LayoutInflater.from(context))

            binding.dialogLayout.addView(view)
            if (useDoubleButton) initDoubleButton(binding, dialog)
            else initSingleButton(binding, dialog)

            return binding.root
        }

        private fun initSingleButton(binding: DialogBaseBinding, dialog: Dialog) = with(binding) {
            doubleButtonLayout.visibility = View.GONE
            singleButton.setBackgroundColor(accentColor)
            singleButton.setText(positiveButtonTextId ?: R.string.close)
            singleButton.setOnClickListener {
                onPositiveButtonClick?.invoke()
                dialog.dismiss()
            }
        }

        private fun initDoubleButton(binding: DialogBaseBinding, dialog: Dialog) = with(binding) {
            singleButton.visibility = View.GONE
            positiveButton.setBackgroundColor(accentColor)
            positiveButton.setText(positiveButtonTextId ?: R.string.ok)
            positiveButton.setOnClickListener {
                onPositiveButtonClick?.invoke()
                dialog.dismiss()
            }
            negativeButton.setText(negativeButtonTextId ?: R.string.cancellation)
            negativeButton.setOnClickListener {
                onNegativeButtonClick?.invoke()
                dialog.dismiss()
            }
        }
    }
}
