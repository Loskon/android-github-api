package com.loskon.githubapi.app.presentation.screens.userprofile.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.loskon.githubapi.app.base.extension.view.setDebounceClickListener
import com.loskon.githubapi.app.base.extension.view.textWithGone
import com.loskon.githubapi.app.base.widget.recyclerview.RecyclerDiffUtil
import com.loskon.githubapi.databinding.ItemRepositoryCardBinding
import com.loskon.githubapi.domain.model.RepositoryModel
import com.loskon.githubapi.viewbinding.viewBinding

/**
 * Адаптер для работы со списком репозиториев
 */
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