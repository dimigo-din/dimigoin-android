package `in`.dimigo.dimigoin.ui

import `in`.dimigo.dimigoin.data.util.UserDataStore
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import org.koin.android.ext.android.inject

open class BaseFragment<T : ViewBinding>(@LayoutRes private val layoutId: Int) : Fragment() {
    private val userDataStore: UserDataStore by inject()
    protected val userData by lazy { userDataStore.requireUserData() }

    protected lateinit var binding: T

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }
}
