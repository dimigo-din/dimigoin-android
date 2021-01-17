package `in`.dimigo.dimigoin.ui.main.fragment.card

import `in`.dimigo.dimigoin.databinding.FragmentCardBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class CardFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }
}
