package `in`.dimigo.dimigoin.ui.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> LiveData<EventWrapper<T>>.observeEvent(
    lifecycleOwner: LifecycleOwner,
    onChanged: (T) -> Unit
) {
    observe(lifecycleOwner, { eventWrapper ->
        eventWrapper.getContentIfNotHandled()?.let { value ->
            onChanged(value)
        }
    })
}
