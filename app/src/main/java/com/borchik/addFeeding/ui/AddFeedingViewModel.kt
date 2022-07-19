package com.borchik.addFeeding.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borchik.dashboard.usecase.CreateFeedingEntityUseCase
import com.borchik.infrastructure.db.FeedingRepository
import com.borchik.infrastructure.utils.DateFormatUtils
import com.borchik.infrastructure.utils.SingleLiveData
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Calendar

class AddFeedingViewModel(
    private val createFeedingEntityUseCase: CreateFeedingEntityUseCase,
    private val feedingRepository: FeedingRepository,
    private val dateFormatUtils: DateFormatUtils
) : ViewModel() {

    private var pickedDate = Calendar.getInstance().time

    val date = MutableLiveData(getCurrentDate())

    val time = MutableLiveData(getCurrentTime())

    val openDatePicker = MutableLiveData<Unit>()

    val openTimePicker = MutableLiveData<Unit>()

    val navigateUp = SingleLiveData<Unit>()

    fun onDateClicked() {
        openDatePicker.postValue(Unit)
    }

    fun onDateSelected(timestamp: Long) {
        pickedDate = Calendar.Builder()
            .setInstant(timestamp)
            .build().time
        date.postValue(dateFormatUtils.formatDateShort(timestamp))
    }

    fun onTimeClicked() {
        openTimePicker.postValue(Unit)
    }

    fun onTimeSelected(hours: Int, minutes: Int) {
        pickedDate.hours = hours
        pickedDate.minutes = minutes
        time.postValue(dateFormatUtils.formatTimeShort(pickedDate.time))
    }

    fun onSaveClicked() {
        Timber.i("Picked date: $pickedDate")
        viewModelScope.launch {
            runCatching { feedingRepository.add(createFeedingEntityUseCase.execute(pickedDate.time)) }
                .onFailure {
                    Timber.i("Failed to add feeding: $it")
                }
                .onSuccess {
                    navigateUp.postValue(Unit)
                }
        }
    }

    private fun getCurrentDate(): String =
        dateFormatUtils.formatDateShort(pickedDate.time)

    private fun getCurrentTime(): String =
        dateFormatUtils.formatTimeShort(pickedDate.time)
}