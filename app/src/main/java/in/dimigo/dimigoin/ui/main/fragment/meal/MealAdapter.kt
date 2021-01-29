package `in`.dimigo.dimigoin.ui.main.fragment.meal

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MealAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 7

    override fun createFragment(position: Int): Fragment {
        val fragment = MealFragment()
        fragment.arguments = Bundle().apply {
            putSerializable(MealFragment.KEY_WEEKDAY, position)
        }
        return fragment
    }
}
