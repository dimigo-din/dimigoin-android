package `in`.dimigo.dimigoin.ui.util

import android.widget.ImageView
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods

@BindingMethods(
    BindingMethod(
        type = ImageView::class,
        attribute = "app:tint",
        method = "setImageTintList"
    )
)
object ImageViewBindingMethods
