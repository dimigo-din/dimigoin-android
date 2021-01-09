package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.api.DimigoinApi
import `in`.dimigo.dimigoin.databinding.FragmentMainBinding
import `in`.dimigo.dimigoin.ui.main.fragment.meal.MealTime
import `in`.dimigo.dimigoin.ui.util.sharedGraphViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        initView(binding)
        return binding.root
    }

    private fun initView(binding: FragmentMainBinding) = with(binding) {
        mealViewPager.apply {
            adapter = MealCardAdapter(this@MainFragment)
            disableOverScrollMode()
            setDefaultSelectedItem(mealPageIndicator)
            applyPageIndicator(mealPageIndicator)
            applyCarouselEffect()
        }

        Glide.with(requireContext())
            .load(DimigoinApi.getProfileUrl(viewModel.userData.photo.last()))
            .into(profileImage)

        OverScrollDecoratorHelper.setUpOverScroll(mainScrollView)
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
}
