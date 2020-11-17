package `in`.dimigo.dimigoin.ui.main.fragment.ingang

import `in`.dimigo.dimigoin.databinding.FragmentIngangBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class IngangFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentIngangBinding.inflate(inflater, container, false)
        OverScrollDecoratorHelper.setUpOverScroll(binding.ingangScrollView)
        return binding.root
    }
}
