package com.loskon.githubapi.app.features.userprofile.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.loskon.githubapi.network.retrofit.model.RepositoryModel

/**
 * Вычисление обновления списка
 */

class RepositoryListDiffUtil(
    private val oldList: List<RepositoryModel>,
    private val newList: List<RepositoryModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].fullName == newList[newItemPosition].fullName &&
            oldList[oldItemPosition].size == newList[newItemPosition].size
    }
}