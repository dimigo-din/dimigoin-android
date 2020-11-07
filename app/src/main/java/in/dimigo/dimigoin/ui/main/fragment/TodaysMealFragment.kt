package `in`.dimigo.dimigoin.ui.main.fragment

import `in`.dimigo.dimigoin.databinding.FragmentSchoolMealBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class TodaysMealFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSchoolMealBinding.inflate(inflater, container, false)
        return binding.root
    }
}
