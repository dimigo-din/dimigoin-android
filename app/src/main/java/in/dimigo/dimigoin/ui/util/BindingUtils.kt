package `in`.dimigo.dimigoin.ui.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.google.android.material.textfield.TextInputLayout

@BindingMethods(
    BindingMethod(
        type = ImageView::class,
        attribute = "app:tint",
        method = "setImageTintList"
    )
)
object ImageViewBindingMethods

@BindingAdapter("android:src")
fun ImageView.setDrawableId(@DrawableRes resId: Int) {
    setImageResource(resId)
}

@BindingAdapter("app:errorText")
fun TextInputLayout.setErrorText(string: String?) {
    error = string
}
