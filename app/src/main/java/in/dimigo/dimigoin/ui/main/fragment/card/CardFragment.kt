package `in`.dimigo.dimigoin.ui.main.fragment.card

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.databinding.FragmentCardBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class CardFragment : Fragment() {
    private lateinit var binding: FragmentCardBinding
    private var isCardShowing = false

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
            isCardShowing = if (isCardShowing) {
                cardMotionLayout.transitionToStart()
                false
            } else {
                cardMotionLayout.transitionToEnd()
                true
            }
        }

        if (UserDataStore.userData.photo.isNotEmpty()) {
            Glide.with(requireContext())
                .load(DimigoinService.getProfileUrl(UserDataStore.userData.photo.last()))
                .into(profileImage)
        }
    }

    companion object {
        private const val VIEW_CAMERA_DISTANCE = 8000f
    }
}
