package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.FragmentNoticeBinding
import `in`.dimigo.dimigoin.ui.util.sharedGraphViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class NoticeCardFragment : Fragment() {
    private lateinit var binding: FragmentNoticeBinding
    private val pos: Int by lazy {
        requireNotNull(arguments?.getSerializable(KEY_POSITION).toString().toInt())
    }
    private val viewModel: MainFragmentViewModel by sharedGraphViewModel(R.id.main_nav_graph)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNoticeBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            position = pos
        }
        return binding.root
    }

    companion object {
        const val KEY_POSITION = "position"
    }
}

