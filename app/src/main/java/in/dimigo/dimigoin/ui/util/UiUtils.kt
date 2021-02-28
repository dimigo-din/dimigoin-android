package `in`.dimigo.dimigoin.ui.util

import `in`.dimigo.dimigoin.R
import android.app.Activity
import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen

fun RecyclerView.showSkeleton(@LayoutRes skeletonLayoutId: Int, itemCount: Int): SkeletonScreen {
    return Skeleton.bind(this)
        .adapter(this.adapter)
        .load(skeletonLayoutId)
        .color(R.color.grey_200)
        .count(itemCount)
        .show()
}

fun View.showSkeleton(@LayoutRes skeletonLayoutId: Int): SkeletonScreen {
    return Skeleton.bind(this)
        .load(skeletonLayoutId)
        .color(R.color.grey_200)
        .show()
}

fun Activity.startActivityTransition() {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P)
        overridePendingTransition(R.anim.fade_in, R.anim.maintain_alpha)
}
