package com.loskon.features.userprofile.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.loskon.base.extension.view.setDebounceClickListener
import com.loskon.base.extension.view.textWithGone
import com.loskon.base.viewbinding.viewBinding
import com.loskon.base.widget.recyclerview.RecyclerDiffUtil
import com.loskon.features.databinding.ItemRepositoryCardBinding
import com.loskon.features.model.RepositoryModel

class RepoListAdapter : RecyclerView.Adapter<RepoListAdapter.RepositoryViewHolder>() {

    private var clickListener: ((RepositoryModel) -> Unit)? = null
    private var list: List<RepositoryModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(parent.viewBinding(ItemRepositoryCardBinding::inflate))
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repository = list[position]

        with(holder.binding) {
            repository.apply {
                tvRepositoryNameCard.text = fullName
                tvRepositoryDescriptionCard.textWithGone(description)
                tvRepositoryLanguageCard.textWithGone(language)
                cardViewRepository.setDebounceClickListener { clickListener?.invoke(this) }
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun setRepositories(newList: List<RepositoryModel>) {
        val diffUtil = RecyclerDiffUtil(list, newList, repoListDiffUtil)
        val diffResult = DiffUtil.calculateDiff(diffUtil, false)
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun setItemClickListener(newClickListener: ((RepositoryModel) -> Unit)?) {
        clickListener = newClickListener
    }

    class RepositoryViewHolder(val binding: ItemRepositoryCardBinding) : RecyclerView.ViewHolder(binding.root)
}