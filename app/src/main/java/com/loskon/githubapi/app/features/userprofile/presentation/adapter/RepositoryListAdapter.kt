package com.loskon.githubapi.app.features.userprofile.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.loskon.githubapi.base.extension.view.setDebounceClickListener
import com.loskon.githubapi.base.extension.view.textNotEmpty
import com.loskon.githubapi.databinding.ItemRepositoryCardBinding
import com.loskon.githubapi.network.retrofit.model.RepositoryModel
import com.loskon.githubapi.viewbinding.viewBinding

/**
 * Адаптер для работы со списком репозиториев
 */
class RepositoryListAdapter : RecyclerView.Adapter<RepositoryListAdapter.RepositoryViewHolder>() {

    private var clickListener: ((RepositoryModel) -> Unit)? = null
    private var list: List<RepositoryModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(parent.viewBinding(ItemRepositoryCardBinding::inflate))
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repository = list[position]

        with(holder.binding) {
            tvRepositoryTitle.text = repository.name
            tvRepositoryDescription.textNotEmpty(repository.description)
            tvRepositoryLanguage.textNotEmpty(repository.language)
            cardViewRepository.setDebounceClickListener { clickListener?.invoke(repository) }
        }
    }

    override fun getItemCount(): Int = list.size

    fun setRepositories(newList: List<RepositoryModel>) {
        val diffUtil = RepositoryListDiffUtil(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil, false)
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun setItemClickListener(newClickListener: ((RepositoryModel) -> Unit)?) {
        clickListener = newClickListener
    }

    inner class RepositoryViewHolder(val binding: ItemRepositoryCardBinding) : RecyclerView.ViewHolder(binding.root)
}