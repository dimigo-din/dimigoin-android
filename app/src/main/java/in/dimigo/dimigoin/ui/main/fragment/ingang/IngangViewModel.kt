package `in`.dimigo.dimigoin.ui.main.fragment.ingang

import `in`.dimigo.dimigoin.data.usecase.ingang.IngangUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class IngangViewModel(private val ingangUseCase: IngangUseCase) : ViewModel() {

    val ingangStatus = liveData {
        val ingangStatus = ingangUseCase.getIngangStatus()
        emit(ingangStatus)
    }
}
