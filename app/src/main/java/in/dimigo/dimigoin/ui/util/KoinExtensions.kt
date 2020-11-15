package `in`.dimigo.dimigoin.ui.util

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import org.koin.android.viewmodel.ViewModelOwner
import org.koin.android.viewmodel.ext.android.sharedViewModel

inline fun <reified VM : ViewModel> Fragment.sharedGraphViewModel(@IdRes navGraphId: Int) =
    sharedViewModel<VM>(owner = {
        ViewModelOwner.from(findNavController().getViewModelStoreOwner(navGraphId))
    })
