package `in`.dimigo.dimigoin

import `in`.dimigo.dimigoin.data.usecase.fcm.FcmUseCase
import `in`.dimigo.dimigoin.data.util.UserDataStore
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class FirebaseCloudMessagingService : FirebaseMessagingService() {
    private val fcmUseCase: FcmUseCase by inject()
    private val userDataStore: UserDataStore by inject()
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onNewToken(newToken: String) {
        if (userDataStore.userData == null) return
        scope.launch {
            fcmUseCase.uploadFcmToken(newToken)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}
