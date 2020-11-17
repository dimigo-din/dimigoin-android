package `in`.dimigo.dimigoin.ui.main.fragment.meal

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.model.MealModel
import `in`.dimigo.dimigoin.databinding.FragmentMealBinding
import `in`.dimigo.dimigoin.ui.main.fragment.util.getFormattedToday
import `in`.dimigo.dimigoin.ui.util.sharedGraphViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import org.koin.core.parameter.parametersOf

class MealFragment : Fragment(), MealViewUseCase {
    private val viewModel: MealViewModel by sharedGraphViewModel(
        R.id.main_nav_graph,
        parameters = { parametersOf(this as MealViewUseCase) }
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMealBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            date = getFormattedToday(getString(R.string.date_format))
            OverScrollDecoratorHelper.setUpOverScroll(mealScrollView)
        }
        return binding.root
    }

    override fun onMealFetchFailed() {
        val failedMealModel = MealModel.getFailedMealModel(requireContext())
        viewModel.setTodayMeal(failedMealModel)
    }
}
