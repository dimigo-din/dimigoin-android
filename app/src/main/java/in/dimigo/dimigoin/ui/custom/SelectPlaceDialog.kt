package `in`.dimigo.dimigoin.ui.custom

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.model.AttendanceLogModel
import `in`.dimigo.dimigoin.data.model.PlaceModel
import `in`.dimigo.dimigoin.data.model.UserModel
import `in`.dimigo.dimigoin.databinding.DialogEtcBinding
import `in`.dimigo.dimigoin.databinding.DialogSelectPlaceBinding
import `in`.dimigo.dimigoin.databinding.ItemPlacesRadioGroupBinding
import `in`.dimigo.dimigoin.ui.main.fragment.main.AttendanceLocation
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.core.view.children
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SelectPlaceDialog(
    private val context: Context,
    private val coroutineScope: CoroutineScope,
    private val placeProvider: PlaceProvider
) {
    private val layoutInflater by lazy { LayoutInflater.from(context) }
    private var selectedPlace: PlaceModel? = null
    private var currentReason: String? = null

    fun show(
        student: UserModel,
        currentTimeText: String,
        changeCurrentAttendancePlace: (place: PlaceModel, remark: String, student: UserModel) -> Unit
    ) = show(
        null, null, currentTimeText,
        { place, remark -> changeCurrentAttendancePlace(place, remark, student) },
        context.getString(R.string.change_location_of_student, student.name)
    )

    fun show(
        currentAttendanceLocation: AttendanceLocation?,
        currentAttendanceLog: AttendanceLogModel?,
        currentTimeText: String,
        changeCurrentAttendancePlace: (place: PlaceModel, remark: String) -> Unit,
        title: String = context.getString(R.string.where_are_you)
    ) {
        if (currentAttendanceLocation == AttendanceLocation.Etc) {
            selectedPlace = currentAttendanceLog?.place
            currentReason = currentAttendanceLog?.remark
        }

        val dialogBinding = DialogEtcBinding.inflate(layoutInflater).apply {
            timeText.text = currentTimeText
            titleText.text = title
            selectedPlace?.let { selectPlaceEditText.setText(it.name) }
            currentReason?.let { reasonEditText.setText(it) }
            selectPlaceEditText.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) return@setOnFocusChangeListener
                v.clearFocus()
                showSelectPlaceDialog(selectedPlace) { place ->
                    selectedPlace = place
                    selectPlaceEditText.setText(place.name)
                }
            }
        }
        DimigoinDialog(context).CustomView(dialogBinding.root).apply {
            usePositiveButton(dismissOnClick = false) {
                val reason = dialogBinding.reasonEditText.text
                if (selectedPlace == null) {
                    Toast.makeText(context, R.string.select_location, Toast.LENGTH_SHORT).show()
                    return@usePositiveButton
                } else if (reason.isNullOrBlank()) {
                    Toast.makeText(context, R.string.enter_reason, Toast.LENGTH_SHORT).show()
                    return@usePositiveButton
                }
                changeCurrentAttendancePlace(requireNotNull(selectedPlace), reason.toString())
                it.dismiss()
            }
            useNegativeButton()
        }.show()
    }

    private fun showSelectPlaceDialog(previousSelectedPlace: PlaceModel?, onSelected: (PlaceModel) -> Unit) {
        val dialog = Dialog(context, R.style.Theme_App_FullScreenDialog)

        val places = placeProvider.places
        if (places == null) {
            coroutineScope.launch { placeProvider.fetchPlaces() }
            Toast.makeText(context, R.string.places_are_not_loaded, Toast.LENGTH_SHORT).show()
            return
        }

        val dialogBinding = DialogSelectPlaceBinding.inflate(layoutInflater).apply {
            val radioGroup = createPlacesRadioGroup(places)
            radioGroup.children.forEach {
                if (it.tag as? PlaceModel == previousSelectedPlace) radioGroup.check(it.id)
            }
            radioGroupScrollView.addView(radioGroup)

            submitButton.setOnClickListener {
                val checkedButtonId = radioGroup.checkedRadioButtonId
                if (checkedButtonId < 0) {
                    Toast.makeText(context, R.string.select_location, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val checkedRadioButton = radioGroup.findViewById<RadioButton>(checkedButtonId)
                val selectedPlace = checkedRadioButton.tag as PlaceModel
                onSelected(selectedPlace)
                dialog.dismiss()
            }
        }

        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }

    private fun createPlacesRadioGroup(places: List<PlaceModel>): RadioGroup {
        val radioGroup = ItemPlacesRadioGroupBinding.inflate(layoutInflater)

        places.forEach { place ->
            radioGroup.placesRadioGroup.run {
                addView(createPlaceRadioButton(place))
                addView(createDivider())
            }
        }

        return radioGroup.root
    }

    private fun createPlaceRadioButton(place: PlaceModel) =
        RadioButton(ContextThemeWrapper(context, R.style.RadioButton)).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            updateRadioButtonColors(false)
            setOnCheckedChangeListener { _, isChecked ->
                updateRadioButtonColors(isChecked)
            }
            text = place.name
            tag = place
        }

    private fun createDivider() = View(context).apply {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3)
        setBackgroundColor(ContextCompat.getColor(context, R.color.grey_100))
    }

    private fun RadioButton.updateRadioButtonColors(isChecked: Boolean) {
        val textColor: Int = if (isChecked) R.color.black
        else R.color.grey_500
        setTextColor(ContextCompat.getColor(context, textColor))
    }
}

interface PlaceProvider {
    var places: List<PlaceModel>?

    suspend fun fetchPlaces()
}
