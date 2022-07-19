package com.borchik.dashboard.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.borchik.dashboard.model.FeedingAction
import com.borchik.dashboard.usecase.CreateFeedingEntityUseCase
import com.borchik.dashboard.usecase.FormatFeedingIntervalUseCase
import com.borchik.dashboard.usecase.GetFeedingsUseCase
import com.borchik.dashboard.usecase.GetTimeBetweenFeedingsUseCase
import com.borchik.infrastructure.db.FeedingEntity
import com.borchik.infrastructure.db.FeedingRepository
import com.borchik.infrastructure.utils.AdapterItemActionDelegate
import com.borchik.infrastructure.utils.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Calendar

class DashboardViewModel(
    private val feedingRepository: FeedingRepository,
    private val createFeedingEntityUseCase: CreateFeedingEntityUseCase,
    private val getFeedingsUseCase: GetFeedingsUseCase,
    private val formatFeedingIntervalUseCase: FormatFeedingIntervalUseCase,
    private val getTimeBetweenFeedingsUseCase: GetTimeBetweenFeedingsUseCase
) : AdapterItemActionDelegate, ViewModel() {

    val direction = SingleLiveData<NavDirections>()

    val feedings = MutableLiveData<List<FeedingEntity>>()

    val vibrate = MutableLiveData<Unit>()

    val snackBarMessage = MutableLiveData<FeedingAction>()

    val timeSinceLastFeeding = MutableLiveData<String>()

    val isRefreshing = MutableLiveData<Boolean>()

    val avgTimeBetweenFeedings = MutableLiveData<String>()

    private var lastRemovedFeeding: FeedingEntity? = null

    init {
        refreshFeedings()
    }

    fun onFeedClicked() {
        vibrate.postValue(Unit)
        viewModelScope.launch(Dispatchers.IO) {
            val feeding = createFeedingEntityUseCase.execute(Calendar.getInstance().timeInMillis)

            runCatching { feedingRepository.add(feeding) }
                .onFailure {
                    Timber.e("Failed to add entry $it")
                }
                .onSuccess {
                    val feedings = feedingRepository.getAll()
                    Timber.i("Feeding added successfully: $feeding\n Feedings: $feedings")
                    refreshFeedings()
                    snackBarMessage.postValue(FeedingAction.ADDED)
                }
        }
    }

    fun onFeedLongClicked(): Boolean {
        vibrate.postValue(Unit)
        direction.postValue(DashboardFragmentDirections.toAddFeeding())
        return true
    }

    fun onFeedingsClicked() {
        // TODO
    }

    override fun onSwipeLeft(targetIndex: Int) {
        viewModelScope.launch {
            feedings.value?.get(targetIndex)
                ?.let {
                    lastRemovedFeeding = it
                    feedingRepository.delete(it)
                    refreshFeedings()
                    snackBarMessage.postValue(FeedingAction.REMOVED)
                }
        }
    }

    fun undoDeletion() {
        lastRemovedFeeding?.let {
            viewModelScope.launch {
                feedingRepository.add(it)
                refreshFeedings()
                snackBarMessage.postValue(FeedingAction.RESTORED)
            }
        }
    }

    fun onSwipeToRefresh() {
        isRefreshing.postValue(true)
        refreshFeedings()
        isRefreshing.postValue(false)
    }

    private fun refreshFeedings() {
        viewModelScope.launch {
            runCatching {
                val feedingRecords = getFeedingsUseCase.execute()
                val currentTimestamp = Calendar.getInstance().timeInMillis
                val lastFeedingTimestamp = feedingRecords.firstOrNull()?.timestamp
                    ?: currentTimestamp
                timeSinceLastFeeding.postValue(
                    formatFeedingIntervalUseCase.execute(
                        lastFeedingTimestamp,
                        currentTimestamp
                    )
                )
                feedings.postValue(feedingRecords)

                val timeBetweenFeedings = getTimeBetweenFeedingsUseCase.execute()
                avgTimeBetweenFeedings.postValue(timeBetweenFeedings)
                Timber.i("Time between feedings: $timeBetweenFeedings")
            }
        }
    }
}