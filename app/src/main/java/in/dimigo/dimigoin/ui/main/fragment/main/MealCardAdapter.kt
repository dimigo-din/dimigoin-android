package `in`.dimigo.dimigoin.ui.main.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MealCardAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = MealCardFragment()
        fragment.arguments = Bundle().apply {
            putSerializable(MealCardFragment.KEY_MEAL_TIME, MealTime.values()[position])
        }
        return fragment
    }
}
