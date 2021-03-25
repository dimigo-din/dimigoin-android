package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.LayoutMealCardBinding
import `in`.dimigo.dimigoin.ui.BaseFragment
import `in`.dimigo.dimigoin.ui.main.fragment.meal.MealTime
import `in`.dimigo.dimigoin.ui.util.sharedGraphViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MealCardFragment : BaseFragment<LayoutMealCardBinding>(R.layout.layout_meal_card) {
    private val mealTime: MealTime by lazy {
        arguments?.getSerializable(KEY_MEAL_TIME) as? MealTime ?: throw Exception()
    }
    private val mainFragmentViewModel: MainFragmentViewModel by sharedGraphViewModel(R.id.main_nav_graph)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        initView()
        return binding.root
    }

    private fun initView() = with(binding) {
        lifecycleOwner = viewLifecycleOwner
        vm = mainFragmentViewModel
        mealTime = this@MealCardFragment.mealTime
    }

    companion object {
        const val KEY_MEAL_TIME = "mealTime"
    }
}

