package `in`.dimigo.dimigoin.ui.main.fragment.card

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.databinding.FragmentCardBinding
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class CardFragment : Fragment() {
    private lateinit var binding: FragmentCardBinding
    private var isCardShowing = false

    private val cardFrontInAnimator by lazy {
        AnimatorInflater.loadAnimator(context, R.animator.card_front_in)
    }
    private val cardBackInAnimator by lazy {
        AnimatorInflater.loadAnimator(context, R.animator.card_back_in) as AnimatorSet
    }
    private val cardFrontOutAnimator by lazy {
        AnimatorInflater.loadAnimator(context, R.animator.card_front_out) as AnimatorSet
    }
    private val cardBackOutAnimator by lazy {
        AnimatorInflater.loadAnimator(context, R.animator.card_back_out) as AnimatorSet
    }

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
        cardBackContainer.cardBackLayout.cameraDistance = cameraDistance

        cardFrontLayout.setOnClickListener {
            if (isCardShowing) hideCard()
            else showCard()
        }

        if (UserDataStore.userData.photo.isNotEmpty()) {
            Glide.with(requireContext())
                .load(DimigoinService.getProfileUrl(UserDataStore.userData.photo.last()))
                .into(cardBackContainer.profileImage)
        }
    }

    private fun showCard() = with(binding) {
        cardFrontOutAnimator.setTarget(cardFrontLayout)
        cardBackInAnimator.setTarget(cardBackContainer.cardBackLayout)
        cardFrontOutAnimator.start()
        cardBackInAnimator.start()
        isCardShowing = true
    }

    private fun hideCard() = with(binding) {
        cardBackOutAnimator.setTarget(cardBackContainer.cardBackLayout)
        cardFrontInAnimator.setTarget(cardFrontLayout)
        cardBackOutAnimator.start()
        cardFrontInAnimator.start()
        isCardShowing = false
    }

    companion object {
        private const val VIEW_CAMERA_DISTANCE = 8000f
    }
}
