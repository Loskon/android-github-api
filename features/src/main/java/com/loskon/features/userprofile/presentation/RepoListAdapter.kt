package com.loskon.features.userprofile.presentation

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loskon.base.extension.view.setDebounceClickListener
import com.loskon.base.extension.view.textWithGone
import com.loskon.base.viewbinding.viewBinding
import com.loskon.features.databinding.ItemRepoCardBinding
import com.loskon.features.model.RepoModel

class RepoListAdapter : RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>() {

    private var onItemClickListener: ((RepoModel) -> Unit)? = null
    private var list: List<RepoModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(parent.viewBinding(ItemRepoCardBinding::inflate))
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = list[position]

        with(holder.binding) {
            repo.apply {
                tvRepoNameCard.text = name
                tvRepoDescriptionCard.textWithGone(description)
                tvRepoLanguageCard.textWithGone(language)
                cardViewRepo.setDebounceClickListener { onItemClickListener?.invoke(this) }
            }
        }
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setRepos(newList: List<RepoModel>) {
        list = newList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: ((RepoModel) -> Unit)?) {
        this.onItemClickListener = onItemClickListener
    }

    class RepoViewHolder(val binding: ItemRepoCardBinding) : RecyclerView.ViewHolder(binding.root)
}