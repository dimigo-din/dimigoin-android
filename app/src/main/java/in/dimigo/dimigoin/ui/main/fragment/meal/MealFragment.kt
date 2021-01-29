package `in`.dimigo.dimigoin.ui.main.fragment.meal

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.FragmentMealBinding
import `in`.dimigo.dimigoin.ui.util.sharedGraphViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MealFragment : Fragment() {
    private val mealFragmentViewModel: MealViewModel by sharedGraphViewModel(R.id.main_nav_graph)
    private val position: Int by lazy {
        arguments?.getSerializable(KEY_WEEKDAY) as? Int ?: throw Exception()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentMealBinding.inflate(inflater, container, false).apply {
            vm = mealFragmentViewModel
            position = this@MealFragment.position
        }

        return binding.root
    }

    companion object {
        const val KEY_WEEKDAY = "weekday"
    }
}
