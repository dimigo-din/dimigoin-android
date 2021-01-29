package `in`.dimigo.dimigoin.ui.main.fragment.meal

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.FragmentWeeklyMealBinding
import `in`.dimigo.dimigoin.ui.main.fragment.util.getFormattedToday
import `in`.dimigo.dimigoin.ui.util.sharedGraphViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import java.util.*

class WeeklyMealFragment : Fragment() {
    private val viewModel: MealViewModel by sharedGraphViewModel(R.id.main_nav_graph)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentWeeklyMealBinding.inflate(inflater, container, false)
        val shortWeekDays = listOf("월", "화", "수", "목", "금", "토", "일")

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            date = getFormattedToday(getString(R.string.date_format))
            OverScrollDecoratorHelper.setUpOverScroll(mealScrollView)

            mealViewPager.adapter = MealAdapter(this@WeeklyMealFragment)

            TabLayoutMediator(tabLayout, mealViewPager) { tab, position ->
                tab.text = shortWeekDays[position]
                mealViewPager.setCurrentItem(tab.position, true)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }.attach()
        }

        return binding.root
    }
}
