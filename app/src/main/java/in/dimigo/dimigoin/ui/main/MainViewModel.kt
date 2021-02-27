package `in`.dimigo.dimigoin.ui.main

import `in`.dimigo.dimigoin.R
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val hideCard = MutableLiveData(null)
    var navigationPos = R.id.main
}
