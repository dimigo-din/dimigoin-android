package `in`.dimigo.dimigoin.ui.main.fragment.card

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.databinding.FragmentCardBinding
import `in`.dimigo.dimigoin.ui.main.MainActivity
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class CardFragment : Fragment() {
    private lateinit var binding: FragmentCardBinding
    private var isCardShowing = false
    private val mainActivity: MainActivity?
        get() = activity as? MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCardBinding.inflate(inflater, container, false)

        binding.apply {
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
            if (isCardShowing) hideCard()
            else showCard()
        }

        cardMotionLayout.setTransitionListener(transitionListener)

        if (UserDataStore.userData.photo.isNotEmpty()) {
            Glide.with(requireContext())
                .load(DimigoinService.getProfileUrl(UserDataStore.userData.photo.last()))
                .into(profileImage)
        }
    }

    private fun showCard() {
        mainActivity?.enterFullScreen()
        binding.cardMotionLayout.updatePadding(top = getStatusBarHeight(), bottom = getNavigationBarHeight())
        binding.cardMotionLayout.transitionToEnd()
        isCardShowing = true
    }

    private fun hideCard() {
        mainActivity?.showBottomNavBar()
        binding.cardMotionLayout.transitionToStart()
        isCardShowing = false
    }

    private val transitionListener = object : MotionLayout.TransitionListener {
        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            if (currentId == R.id.card_start) onCardHidden()
        }

        override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}
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

    private fun getNavigationBarHeight(): Int {
        val resources = context?.resources ?: return 0
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    companion object {
        private const val VIEW_CAMERA_DISTANCE = 8000f
    }
}
