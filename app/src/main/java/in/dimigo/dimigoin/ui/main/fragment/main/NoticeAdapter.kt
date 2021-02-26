package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.ui.item.NoticeItem
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class NoticeAdapter(fragment: Fragment, private val noticeList: List<NoticeItem>) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = noticeList.size

    override fun createFragment(position: Int): Fragment {
        val fragment = NoticeCardFragment()
        fragment.arguments = Bundle().apply {
            putSerializable(NoticeCardFragment.KEY_POSITION, position)
        }
        return fragment
    }
}
