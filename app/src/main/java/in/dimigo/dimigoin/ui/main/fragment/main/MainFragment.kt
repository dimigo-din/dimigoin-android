package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.model.PlaceModel
import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.databinding.*
import `in`.dimigo.dimigoin.ui.custom.DimigoinDialog
import `in`.dimigo.dimigoin.ui.main.fragment.meal.MealTime
import `in`.dimigo.dimigoin.ui.util.observeEvent
import `in`.dimigo.dimigoin.ui.util.sharedGraphViewModel
import android.animation.LayoutTransition
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.rd.PageIndicatorView
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

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
        mealViewPager.apply {
            adapter = MealCardAdapter(this@MainFragment)
            disableOverScrollMode()
            setDefaultSelectedItem(mealPageIndicator)
            applyPageIndicator(mealPageIndicator)
            applyCarouselEffect()
        }

        if (UserDataStore.userData.photo.isNotEmpty()) {
            Glide.with(requireContext())
                .load(DimigoinService.getProfileUrl(UserDataStore.userData.photo.last()))
                .into(profileImage)
        }

        viewModel.event.observeEvent(viewLifecycleOwner) {
            when (it) {
                Event.LOCATION_ETC_CLICKED -> {
                    showEtcDialog()
                }
            }
        }

        mainContentLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        OverScrollDecoratorHelper.setUpOverScroll(mainScrollView)
    }

    private fun showEtcDialog() {
        var selectedPlace: PlaceModel? = null

        val dialogBinding = DialogEtcBinding.inflate(layoutInflater).apply {
            timeText.text = binding.timeText.text
            selectPlaceEditText.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) return@setOnFocusChangeListener
                v.clearFocus()
                showSelectPlaceDialog(selectedPlace) { place ->
                    selectedPlace = place
                    selectPlaceEditText.setText(place.name)
                }
            }
        }
        DimigoinDialog(requireContext()).CustomView(dialogBinding.root).apply {
            usePositiveButton(dismissOnClick = false) {
                val reason = dialogBinding.reasonEditText.text
                if (selectedPlace == null) {
                    Toast.makeText(context, R.string.select_location, Toast.LENGTH_SHORT).show()
                    return@usePositiveButton
                } else if (reason.isNullOrBlank()) {
                    Toast.makeText(context, R.string.enter_reason, Toast.LENGTH_SHORT).show()
                    return@usePositiveButton
                }
                viewModel.changeCurrentAttendancePlace(requireNotNull(selectedPlace), reason.toString())
                it.dismiss()
            }
            useNegativeButton()
        }.show()
    }

    private fun showSelectPlaceDialog(previousSelectedPlace: PlaceModel?, onSelected: (PlaceModel) -> Unit) {
        val dialog = Dialog(requireContext(), R.style.Theme_App_FullScreenDialog)

        val places = viewModel.places
        if (places == null) {
            viewModel.fetchPlaces()
            Toast.makeText(context, R.string.enter_id, Toast.LENGTH_SHORT).show()
            return
        }

        val dialogBinding = DialogSelectPlaceBinding.inflate(layoutInflater).apply {
            val radioGroup = createPlacesRadioGroup(places)
            radioGroup.children.forEach {
                if (it.tag as? PlaceModel == previousSelectedPlace) radioGroup.check(it.id)
            }
            radioGroupScrollView.addView(radioGroup)

            submitButton.setOnClickListener {
                val checkedButtonId = radioGroup.checkedRadioButtonId
                if (checkedButtonId < 0) {
                    Toast.makeText(context, R.string.select_location, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val checkedRadioButton = radioGroup.findViewById<RadioButton>(checkedButtonId)
                val selectedPlace = checkedRadioButton.tag as PlaceModel
                onSelected(selectedPlace)
                dialog.dismiss()
            }
        }

        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }

    private fun createPlacesRadioGroup(places: List<PlaceModel>): RadioGroup {
        val radioGroup = ItemPlacesRadioGroupBinding.inflate(layoutInflater)

        places.forEach { place ->
            radioGroup.placesRadioGroup.run {
                addView(createPlaceRadioButton(place))
                addView(createDivider())
            }
        }

        return radioGroup.root
    }

    private fun createPlaceRadioButton(place: PlaceModel) =
        RadioButton(ContextThemeWrapper(context, R.style.RadioButton)).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            updateRadioButtonColors(false)
            setOnCheckedChangeListener { _, isChecked ->
                updateRadioButtonColors(isChecked)
            }
            text = place.name
            tag = place
        }

    private fun createDivider() = View(context).apply {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, 3)
        setBackgroundColor(ContextCompat.getColor(context, R.color.grey_100))
    }

    private fun RadioButton.updateRadioButtonColors(isChecked: Boolean) {
        val textColor: Int = if (isChecked) R.color.black
        else R.color.grey_500
        setTextColor(ContextCompat.getColor(context, textColor))
    }

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

    enum class Event {
        LOCATION_ETC_CLICKED
    }
}
