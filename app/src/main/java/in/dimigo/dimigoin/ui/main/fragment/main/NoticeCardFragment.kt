package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.FragmentNoticeBinding
import `in`.dimigo.dimigoin.ui.util.sharedGraphViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

class NoticeCardFragment : Fragment() {
    private lateinit var binding: FragmentNoticeBinding
    private val pos: Int by lazy {
        arguments?.getSerializable(KEY_POSITION) as? Int ?: throw Exception()
    }
    private val viewModel: MainFragmentViewModel by sharedGraphViewModel(R.id.main_nav_graph)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notice, container, false)
        initView()
        return binding.root
    }

    private fun initView() = with(binding) {
        lifecycleOwner = viewLifecycleOwner
        vm = viewModel
        this.position = pos
    }

    companion object {
        const val KEY_POSITION = "position"
    }
}

