package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.LayoutMealCardBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

class MealCardFragment : Fragment() {
    private lateinit var binding: LayoutMealCardBinding
    private val mealTime: MealTime by lazy {
        arguments?.getSerializable(KEY_MEAL_TIME) as? MealTime ?: throw Exception()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_meal_card, container, false)
        initView()
        return binding.root
    }

    private fun initView() = with(binding) {
        mealCard.clipToOutline = false
        mealCardTitle.setText(mealTime.stringId)
    }

    companion object {
        const val KEY_MEAL_TIME = "mealTime"
    }
}

enum class MealTime(@StringRes val stringId: Int) {
    BREAKFAST(R.string.breakfast),
    LUNCH(R.string.lunch),
    DINNER(R.string.dinner);
}
