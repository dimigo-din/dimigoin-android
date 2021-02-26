package `in`.dimigo.dimigoin.ui.util

import `in`.dimigo.dimigoin.R
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
