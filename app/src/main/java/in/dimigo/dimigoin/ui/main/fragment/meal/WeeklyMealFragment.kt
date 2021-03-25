package `in`.dimigo.dimigoin.ui.main.fragment.meal

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.FragmentWeeklyMealBinding
import `in`.dimigo.dimigoin.ui.BaseFragment
import `in`.dimigo.dimigoin.ui.util.sharedGraphViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class WeeklyMealFragment : BaseFragment<FragmentWeeklyMealBinding>(R.layout.fragment_weekly_meal) {
    private val viewModel: MealViewModel by sharedGraphViewModel(R.id.main_nav_graph)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val shortWeekDays = listOf("월", "화", "수", "목", "금", "토", "일")

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            date = getFormattedToday(getString(R.string.date_format))

            mealViewPager.adapter = MealAdapter(this@WeeklyMealFragment)
            TabLayoutMediator(tabLayout, mealViewPager) { tab, position ->
                tab.text = shortWeekDays[position]
            }.attach()
            mealViewPager.setCurrentItem(LocalDate.now().dayOfWeek.ordinal, false)
        }

        return binding.root
    }

    private fun getFormattedToday(dateFormat: String): String {
        val time = Calendar.getInstance().time
        return SimpleDateFormat(dateFormat, Locale.KOREA).format(time)
    }
}
