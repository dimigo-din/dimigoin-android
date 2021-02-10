package `in`.dimigo.dimigoin.ui.main.fragment.ingang

import `in`.dimigo.dimigoin.databinding.FragmentIngangBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import org.koin.android.ext.android.inject

class IngangFragment : Fragment() {
    private lateinit var binding: FragmentIngangBinding
    private val viewModel: IngangViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentIngangBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
        initView()

        return binding.root
    }

    private fun initView() {
        OverScrollDecoratorHelper.setUpOverScroll(binding.ingangScrollView)
    }
}
