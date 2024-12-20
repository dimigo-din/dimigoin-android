package `in`.dimigo.dimigoin.ui

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.model.UserModel
import `in`.dimigo.dimigoin.data.util.UserDataStore
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject

open class BaseActivity : AppCompatActivity() {
    private val userDataStore: UserDataStore by inject()
    protected val userData: UserModel by lazy { userDataStore.requireUserData() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.getBoolean(R.bool.portrait_only))
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.navigationBarColor = getColor(R.color.white)
            window.navigationBarDividerColor = getColor(R.color.grey_100)
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = getDefaultWindowDecorViewFlags()
        }
    }

    @Suppress("DEPRECATION")
    protected fun getDefaultWindowDecorViewFlags(): Int {
        var flags = View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        return flags
    }
}
