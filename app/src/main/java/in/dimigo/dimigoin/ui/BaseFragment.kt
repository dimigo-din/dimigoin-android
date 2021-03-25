package `in`.dimigo.dimigoin.ui

import `in`.dimigo.dimigoin.data.util.UserDataStore
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject

open class BaseFragment : Fragment() {
    private val userDataStore: UserDataStore by inject()
    protected val userData by lazy { userDataStore.requireUserData() }
}
