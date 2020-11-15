package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.FragmentMainBinding
import `in`.dimigo.dimigoin.ui.util.sharedGraphViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import java.util.*

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainFragmentViewModel by sharedGraphViewModel(R.id.main_nav_graph)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.apply {
            lifecycleOwner = this@MainFragment
            vm = viewModel
        }
        initView(binding)
        return binding.root
    }

    private fun initView(binding: FragmentMainBinding) = with(binding) {
        mealViewPager.apply {
            offscreenPageLimit = 3
            val offsetPx = resources.getDimensionPixelOffset(R.dimen.meal_view_pager_page_offset)
            val pageGapPx = resources.getDimensionPixelOffset(R.dimen.meal_view_pager_page_gap)
            setPageTransformer { page, position ->
                val offset = position * -(2 * offsetPx + pageGapPx)
                page.translationX = offset
            }
            adapter = MealCardAdapter(this@MainFragment)

            val child = getChildAt(0)
            if (child is RecyclerView) child.overScrollMode = View.OVER_SCROLL_NEVER

            val currentCardPosition = getCurrentMealTime().ordinal
            setCurrentItem(currentCardPosition, false)
            mealPageIndicator.setSelected(currentCardPosition)

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    mealPageIndicator.selection = position
                }
            })
        }
    }

    private fun getCurrentMealTime() = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
        in 0..8 -> MealTime.BREAKFAST
        in 9..13 -> MealTime.LUNCH
        in 14..19 -> MealTime.DINNER
        else -> MealTime.BREAKFAST
    }
}
