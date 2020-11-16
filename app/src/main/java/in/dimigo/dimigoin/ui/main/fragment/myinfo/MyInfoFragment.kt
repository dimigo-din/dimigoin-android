package `in`.dimigo.dimigoin.ui.main.fragment.myinfo

import `in`.dimigo.dimigoin.databinding.FragmentMyInfoBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MyInfoFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMyInfoBinding.inflate(inflater, container, false)
        return binding.root
    }
}
