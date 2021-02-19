package `in`.dimigo.dimigoin.ui.util

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("android:src")
fun ImageView.setDrawableId(@DrawableRes resId: Int) {
    setImageResource(resId)
}

@BindingAdapter("app:errorText")
fun TextInputLayout.setErrorText(string: String?) {
    error = string
}

private const val TINT_ANIMATION_DURATION = 200L

@BindingAdapter("android:backgroundTint")
fun ImageView.animateBackgroundTint(@ColorInt colorTo: Int) {
    val colorFrom = backgroundTintList?.defaultColor ?: colorTo
    if (colorFrom == colorTo) {
        backgroundTintList = ColorStateList.valueOf(colorTo)
        return
    }
    ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo).apply {
        duration = TINT_ANIMATION_DURATION
        addUpdateListener {
            backgroundTintList = ColorStateList.valueOf(it.animatedValue.toString().toInt())
        }
        start()
    }
}

@BindingAdapter("app:tint")
fun ImageView.animateImageTint(@ColorInt colorTo: Int) {
    val colorFrom = imageTintList?.defaultColor ?: colorTo
    if (colorFrom == colorTo) {
        imageTintList = ColorStateList.valueOf(colorTo)
        return
    }
    ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo).apply {
        duration = TINT_ANIMATION_DURATION
        addUpdateListener {
            imageTintList = ColorStateList.valueOf(it.animatedValue.toString().toInt())
        }
        start()
    }
}
