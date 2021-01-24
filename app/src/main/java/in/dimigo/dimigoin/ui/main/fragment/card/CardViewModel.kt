package `in`.dimigo.dimigoin.ui.main.fragment.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CardViewModel : ViewModel() {
    private val _remainingSeconds = MutableLiveData(CARD_SHOWING_SECONDS)
    val remainingSeconds: LiveData<Int> = _remainingSeconds

    private var timerJob: Job? = null

    fun startTimer() {
        timerJob = viewModelScope.launch {
            repeat(CARD_SHOWING_SECONDS) {
                delay(1000)
                val currentRemainingSeconds = remainingSeconds.value ?: CARD_SHOWING_SECONDS
                _remainingSeconds.value = currentRemainingSeconds - 1
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        _remainingSeconds.value = CARD_SHOWING_SECONDS
    }

    companion object {
        private const val CARD_SHOWING_SECONDS = 15
    }
}
