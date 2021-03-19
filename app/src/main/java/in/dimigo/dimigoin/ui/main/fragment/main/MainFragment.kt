package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.databinding.*
import `in`.dimigo.dimigoin.ui.custom.DimigoinDialog
import `in`.dimigo.dimigoin.ui.custom.PlaceProvider
import `in`.dimigo.dimigoin.ui.custom.SelectPlaceDialog
import `in`.dimigo.dimigoin.ui.main.fragment.meal.MealTime
import `in`.dimigo.dimigoin.ui.splash.SplashActivity
import `in`.dimigo.dimigoin.ui.util.observeEvent
import `in`.dimigo.dimigoin.ui.util.sharedGraphViewModel
import android.animation.LayoutTransition
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.rd.PageIndicatorView

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainFragmentViewModel by sharedGraphViewModel(R.id.main_nav_graph)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@MainFragment
            vm = viewModel
        }
        initView()
        return binding.root
    }

    private fun initView() = with(binding) {
        val noticeAdapter = NoticeRecyclerViewAdapter()
        noticeRecyclerView.adapter = noticeAdapter

        mealViewPager.apply {
            adapter = MealCardAdapter(this@MainFragment)
            disableOverScrollMode()
            setDefaultSelectedItem(mealPageIndicator)
            applyPageIndicator(mealPageIndicator)
            applyCarouselEffect()
        }

        if (UserDataStore.userData.photos.isNotEmpty()) {
            Glide.with(requireContext())
                .load(UserDataStore.userData.photos.last())
                .into(profileImage)
        }

        viewModel.event.observeEvent(viewLifecycleOwner) { event ->
            when (event) {
                is Event.LocationEtcClicked -> {
                    val dialog = SelectPlaceDialog(requireContext(), lifecycleScope, viewModel as PlaceProvider)
                    dialog.show(
                        viewModel.attendanceLocation.value,
                        viewModel.currentAttendanceLog,
                        binding.timeText.text.toString(),
                        viewModel::changeCurrentAttendancePlace
                    )
                }
                is Event.AttendanceLocationChanged -> {
                    val message = getString(R.string.location_changed, event.placeName)
                    DimigoinDialog(requireContext()).alert(DimigoinDialog.AlertType.POSITIVE, message)
                }
                is Event.Error ->
                    DimigoinDialog(requireContext()).alert(DimigoinDialog.AlertType.ERROR, event.errorMessageId)
            }
        }

        viewModel.noticeList.observe(viewLifecycleOwner) {
            noticeAdapter.setItems(it)
        }

        binding.attendanceDetailButton.setOnClickListener {
            findNavController().navigate(R.id.attendance)
        }

        profileImage.setOnClickListener {
            val dialogView = DialogLogoutBinding.inflate(layoutInflater).root
            DimigoinDialog(requireContext()).CustomView(dialogView).apply {
                usePositiveButton {
                    val intent = Intent(context, SplashActivity::class.java)
                    intent.putExtra(SplashActivity.KEY_LOGOUT, true)
                    startActivity(intent)
                    activity?.finish()
                }
                useNegativeButton()
            }.show()
        }

        mainContentLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    }

    sealed class Event {
        object LocationEtcClicked : Event()
        data class AttendanceLocationChanged(val placeName: String) : Event()
        data class Error(@StringRes val errorMessageId: Int) : Event()
    }

    // region Viewpager carousel
    private fun ViewPager2.applyCarouselEffect() {
        offscreenPageLimit = 3
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.meal_view_pager_page_offset)
        val pageGapPx = resources.getDimensionPixelOffset(R.dimen.meal_view_pager_page_gap)
        setPageTransformer { page, position ->
            val offset = position * -(2 * offsetPx + pageGapPx)
            page.translationX = offset
        }
    }

    private fun ViewPager2.disableOverScrollMode() {
        val child = getChildAt(0)
        if (child is RecyclerView) child.overScrollMode = View.OVER_SCROLL_NEVER
    }

    private fun ViewPager2.setDefaultSelectedItem(mealPageIndicator: PageIndicatorView) {
        val currentCardPosition = MealTime.getCurrentMealTime().ordinal
        setCurrentItem(currentCardPosition, false)
        mealPageIndicator.setSelected(currentCardPosition)
    }

    private fun ViewPager2.applyPageIndicator(mealPageIndicator: PageIndicatorView) {
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                mealPageIndicator.selection = position
            }
        })
    }
    // endregion
}
