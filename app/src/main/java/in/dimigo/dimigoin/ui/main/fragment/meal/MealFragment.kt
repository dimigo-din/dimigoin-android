package `in`.dimigo.dimigoin.ui.main.fragment.meal

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.FragmentMealBinding
import `in`.dimigo.dimigoin.ui.util.sharedGraphViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

class MealFragment : Fragment() {
    private val viewModel: MealViewModel by sharedGraphViewModel(R.id.main_nav_graph)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMealBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel

            val time = Calendar.getInstance().time
            val formattedDate = SimpleDateFormat(getString(R.string.meal_date_format), Locale.KOREA).format(time)
            date = formattedDate
        }
        return binding.root
    }
}
