package com.loskon.features.repolist.presentation

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.loskon.base.viewbinding.viewBinding
import com.loskon.features.databinding.ItemLoadStateBinding

class RepoListLoadAdapter: LoadStateAdapter<RepoListLoadAdapter.RepoListLoadViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): RepoListLoadViewHolder {
        return RepoListLoadViewHolder(parent.viewBinding(ItemLoadStateBinding::inflate))
    }

    override fun onBindViewHolder(holder: RepoListLoadViewHolder, loadState: LoadState) {
        holder.binding.progressLoadStateIndicator.isVisible = loadState is LoadState.Loading
    }

    class RepoListLoadViewHolder(val binding: ItemLoadStateBinding) : RecyclerView.ViewHolder(binding.root)
}