package com.borchik.infrastructure.utils

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDeleteCallback(
    private val adapterActionDelegate: AdapterItemActionDelegate,
    private val vibrator: Vibrator
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    override fun isItemViewSwipeEnabled(): Boolean {
        return super.isItemViewSwipeEnabled()
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        vibrator.vibrate(VibrationEffect.createOneShot(20, VibrationEffect.EFFECT_TICK))
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.LEFT) {
            adapterActionDelegate.onSwipeLeft(position)
        }
    }
}