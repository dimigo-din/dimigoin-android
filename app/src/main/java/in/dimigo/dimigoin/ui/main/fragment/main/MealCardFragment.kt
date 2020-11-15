package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.LayoutMealCardBinding
import `in`.dimigo.dimigoin.ui.main.fragment.meal.MealTime
import `in`.dimigo.dimigoin.ui.util.sharedGraphViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

class MealCardFragment : Fragment() {
    private lateinit var binding: LayoutMealCardBinding
    private val mealTime: MealTime by lazy {
        arguments?.getSerializable(KEY_MEAL_TIME) as? MealTime ?: throw Exception()
    }
    private val mainFragmentViewModel: MainFragmentViewModel by sharedGraphViewModel(R.id.main_nav_graph)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_meal_card, container, false)
        initView()
        return binding.root
    }

    private fun initView() = with(binding) {
        mealCard.clipToOutline = false
        lifecycleOwner = viewLifecycleOwner
        vm = mainFragmentViewModel
        mealTime = this@MealCardFragment.mealTime
    }

    companion object {
        const val KEY_MEAL_TIME = "mealTime"
    }
}

