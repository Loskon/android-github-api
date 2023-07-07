package com.loskon.features.repolist.presentation

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.loskon.base.extension.view.setDebounceClickListener
import com.loskon.base.viewbinding.viewBinding
import com.loskon.features.databinding.ItemUserCardBinding
import com.loskon.features.model.UserModel
import com.loskon.network.imageloader.ImageLoader

class RepoListPagingAdapter : PagingDataAdapter<UserModel, RepoListPagingAdapter.RepoListViewHolder>(DIFF_CALLBACK) {

    private var onItemClickListener: ((UserModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListViewHolder {
        return RepoListViewHolder(parent.viewBinding(ItemUserCardBinding::inflate))
    }

    override fun onBindViewHolder(holder: RepoListViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            item?.apply {
                ImageLoader.load(ivUserCard, avatarUrl)
                tvUserCardLogin.text = login
                cardViewUser.setDebounceClickListener { onItemClickListener?.invoke(this) }
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: ((UserModel) -> Unit)?) {
        this.onItemClickListener = onItemClickListener
    }

    class RepoListViewHolder(val binding: ItemUserCardBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
                false

            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
                false
        }
    }
}