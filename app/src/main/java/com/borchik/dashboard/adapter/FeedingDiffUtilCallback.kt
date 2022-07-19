package com.borchik.dashboard.adapter

import androidx.recyclerview.widget.DiffUtil
import com.borchik.infrastructure.db.FeedingEntity

class FeedingDiffUtilCallback(
    private val oldData: List<FeedingEntity>,
    private val newData: List<FeedingEntity>
) : DiffUtil.Callback() {

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldData[oldItemPosition] == newData[newItemPosition]

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldData[oldItemPosition].id == newData[newItemPosition].id

    override fun getNewListSize(): Int =
        newData.size

    override fun getOldListSize(): Int =
        oldData.size
}