package com.loskon.features.userprofile.presentation

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loskon.base.extension.view.setDebounceClickListener
import com.loskon.base.extension.view.textWithGone
import com.loskon.base.viewbinding.viewBinding
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
                tvRepositoryNameCard.text = name
                tvRepositoryDescriptionCard.textWithGone(description)
                tvRepositoryLanguageCard.textWithGone(language)
                cardViewRepository.setDebounceClickListener { clickListener?.invoke(this) }
            }
        }
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setRepositories(newList: List<RepositoryModel>) {
        list = newList
        notifyDataSetChanged()
    }

    fun setItemClickListener(newClickListener: ((RepositoryModel) -> Unit)?) {
        clickListener = newClickListener
    }

    class RepositoryViewHolder(val binding: ItemRepositoryCardBinding) : RecyclerView.ViewHolder(binding.root)
}