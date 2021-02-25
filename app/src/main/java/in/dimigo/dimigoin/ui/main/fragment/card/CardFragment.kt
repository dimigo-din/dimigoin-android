package `in`.dimigo.dimigoin.ui.main.fragment.card

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.databinding.DialogCardCautionBinding
import `in`.dimigo.dimigoin.databinding.FragmentCardBinding
import `in`.dimigo.dimigoin.ui.custom.DimigoinDialog
import `in`.dimigo.dimigoin.ui.main.MainActivity
import `in`.dimigo.dimigoin.ui.main.MainViewModel
import `in`.dimigo.dimigoin.ui.util.sharedGraphViewModel
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter

class CardFragment : Fragment() {
    private lateinit var binding: FragmentCardBinding
    private val viewModel: CardViewModel by sharedGraphViewModel(R.id.main_nav_graph)
    private val activityViewModel: MainViewModel by activityViewModels()
    private val mainActivity: MainActivity?
        get() = activity as? MainActivity

    private var isCardShowing = false
    private var isCardAnimating = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCardBinding.inflate(inflater, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            val userModel = UserDataStore.userData
            user = userModel
            department = resources.getStringArray(R.array.departments)[userModel.klass - 1]
        }

        initView()

        return binding.root
    }

    private fun initView() = with(binding) {
        val cameraDistanceScale = context?.resources?.displayMetrics?.density ?: 1f
        val cameraDistance = VIEW_CAMERA_DISTANCE * cameraDistanceScale
        cardFrontLayout.cameraDistance = cameraDistance
        cardBackLayout.cameraDistance = cameraDistance

        cardFrontLayout.setOnClickListener {
            if (!isCardShowing && !isCardAnimating) runWhenAuthenticated {
                showCard()
            }
        }

        cardBackCloseButton.setOnClickListener {
            if (isCardShowing && !isCardAnimating) hideCard()
        }

        cardMotionLayout.setTransitionListener(transitionListener)

        if (UserDataStore.userData.photos.isNotEmpty()) {
            Glide.with(requireContext())
                .load(UserDataStore.userData.photos.last())
                .into(profileImage)
        }

        viewModel.remainingSeconds.observe(viewLifecycleOwner) {
            if (it == 0) hideCard()
        }

        activityViewModel.hideCard.observe(viewLifecycleOwner) {
            hideCard()
        }

        barcodeImage.post {
            val barcodeBitmap = renderBarcode(barcodeImage.width, barcodeImage.height)
            barcodeImage.setImageBitmap(barcodeBitmap)
        }

        cautionButton.setOnClickListener {
            val dialogView = DialogCardCautionBinding.inflate(layoutInflater).root
            DimigoinDialog(requireContext()).CustomView(dialogView, R.color.grey_450).show()
        }
    }

    private fun runWhenAuthenticated(onSucceeded: () -> Unit) {
        val executor = ContextCompat.getMainExecutor(requireContext())
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSucceeded()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode != BiometricPrompt.ERROR_USER_CANCELED)
                    DimigoinDialog(requireContext())
                        .alert(DimigoinDialog.AlertType.ERROR, R.string.cannot_use_authentication)
            }
        }
        val biometricPrompt = BiometricPrompt(this, executor, callback)

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.authenticate))
            .setSubtitle(getString(R.string.authenticate_to_use_card))
            .setAllowedAuthenticators(BIOMETRIC_WEAK or DEVICE_CREDENTIAL)
            .build()
        biometricPrompt.authenticate(promptInfo)
    }

    private fun showCard() {
        mainActivity?.enterFullScreen()
        binding.cardMotionLayout.updatePadding(top = getStatusBarHeight())
        binding.cardMotionLayout.transitionToEnd()
        isCardShowing = true
    }

    private fun hideCard() {
        mainActivity?.showBottomNavBar()
        binding.cardMotionLayout.transitionToStart()
        isCardShowing = false
    }

    private val transitionListener = object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {
            isCardAnimating = true
        }

        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            isCardAnimating = false
            if (currentId == R.id.card_start) {
                onCardHidden()
                viewModel.stopTimer()
            } else if (currentId == R.id.card_end) viewModel.startTimer()
        }

        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
    }

    private fun onCardHidden() {
        mainActivity?.exitFullScreen()
        binding.cardMotionLayout.updatePadding(top = 0, bottom = 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.cardMotionLayout.removeTransitionListener(transitionListener)
    }

    private fun getStatusBarHeight(): Int {
        val rectangle = Rect()
        val window = activity?.window ?: return 0
        window.decorView.getWindowVisibleDisplayFrame(rectangle)
        return rectangle.top
    }

    private fun renderBarcode(width: Int, height: Int): Bitmap? {
        val barcodeString = UserDataStore.userData.libraryId ?: return null
        val bitMatrix = MultiFormatWriter().encode(barcodeString, BarcodeFormat.CODE_39, width, height)
        val bitmap = Bitmap.createBitmap(bitMatrix.width, bitMatrix.height, Bitmap.Config.ARGB_8888)
        for (x in 0 until width) {
            for (y in 0 until height) {
                val color = if (bitMatrix[x, y]) Color.BLACK else Color.TRANSPARENT
                bitmap.setPixel(x, y, color)
            }
        }
        return bitmap
    }

    companion object {
        private const val VIEW_CAMERA_DISTANCE = 8000f
    }
}
