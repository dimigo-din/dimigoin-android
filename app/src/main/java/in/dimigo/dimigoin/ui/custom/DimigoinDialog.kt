package `in`.dimigo.dimigoin.ui.custom

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.DialogAlertBinding
import `in`.dimigo.dimigoin.databinding.DialogBaseBinding
import `in`.dimigo.dimigoin.ui.util.setDrawableId
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

class DimigoinDialog(
    private val context: Context,
    private val cancelable: Boolean = true,
) {
    private val layoutInflater = LayoutInflater.from(context)

    fun alert(alertType: AlertType, @StringRes messageStringId: Int) {
        val binding = DialogAlertBinding.inflate(layoutInflater).apply {
            icon.setDrawableId(alertType.iconDrawableId)
            icon.setColorFilter(getColor(alertType.accentColorId))
            messageText.setText(messageStringId)
            messageText.setTextColor(getColor(alertType.messageColorId))
        }

        CustomView(binding.root, alertType.accentColorId).apply {
            usePositiveButton(R.string.ok)
            show()
        }
    }

    inner class CustomView(
        private val view: View,
        @ColorRes private val accentColorId: Int = R.color.pink_400
    ) {
        private var useDoubleButton = false
        private var positiveButtonTextId: Int? = null
        private var onPositiveButtonClick: ((Dialog) -> Unit)? = null
        private var positiveButtonDismissOnClick = true
        private var negativeButtonTextId: Int? = null
        private var onNegativeButtonClick: (() -> Unit)? = null

        fun usePositiveButton(
            @StringRes textId: Int? = null,
            dismissOnClick: Boolean = true,
            onClick: ((Dialog) -> Unit)? = null
        ) {
            if (textId != null) positiveButtonTextId = textId
            positiveButtonDismissOnClick = dismissOnClick
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

        private fun createView(dialog: Dialog): View {
            val binding = DialogBaseBinding.inflate(layoutInflater)

            binding.dialogLayout.addView(view)
            if (useDoubleButton) initDoubleButton(binding, dialog)
            else initSingleButton(binding, dialog)

            return binding.root
        }

        private fun initSingleButton(binding: DialogBaseBinding, dialog: Dialog) = with(binding) {
            doubleButtonLayout.visibility = View.GONE
            singleButton.setBackgroundColor(getColor(accentColorId))
            singleButton.setText(positiveButtonTextId ?: R.string.close)
            singleButton.setOnClickListener {
                onPositiveButtonClick?.invoke(dialog)
                if (positiveButtonDismissOnClick) dialog.dismiss()
            }
        }

        private fun initDoubleButton(binding: DialogBaseBinding, dialog: Dialog) = with(binding) {
            singleButton.visibility = View.GONE
            positiveButton.setBackgroundColor(getColor(accentColorId))
            positiveButton.setText(positiveButtonTextId ?: R.string.ok)
            positiveButton.setOnClickListener {
                onPositiveButtonClick?.invoke(dialog)
                if (positiveButtonDismissOnClick) dialog.dismiss()
            }
            negativeButton.setText(negativeButtonTextId ?: R.string.cancellation)
            negativeButton.setOnClickListener {
                onNegativeButtonClick?.invoke()
                dialog.dismiss()
            }
        }
    }

    private fun getColor(@ColorRes colorId: Int) = ContextCompat.getColor(context, colorId)

    enum class AlertType(
        @DrawableRes val iconDrawableId: Int,
        @ColorRes val accentColorId: Int,
        @ColorRes val messageColorId: Int
    ) {
        POSITIVE(R.drawable.ic_check, R.color.pink_400, R.color.pink_400),
        NEGATIVE(R.drawable.ic_check, R.color.grey_450, R.color.grey_450),
        ERROR(R.drawable.ic_information, R.color.amber_300, R.color.grey_450)
    }
}
