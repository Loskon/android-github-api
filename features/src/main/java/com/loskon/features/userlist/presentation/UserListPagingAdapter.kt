package com.loskon.features.userlist.presentation

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.loskon.base.extension.view.setDebounceClickListener
import com.loskon.base.viewbinding.viewBinding
import com.loskon.features.databinding.ItemUserCardBinding
import com.loskon.features.model.UserModel
import com.loskon.features.util.imageloader.loadImage
import timber.log.Timber

class UserListPagingAdapter(
    userDiffUtil: DiffUtil.ItemCallback<UserModel> = UserListDiffUtil
) : PagingDataAdapter<UserModel, UserListPagingAdapter.UserListViewHolder>(userDiffUtil) {

    private var onItemClickListener: ((UserModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(parent.viewBinding(ItemUserCardBinding::inflate))
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val user = getItem(position)

        with(holder.binding) {
            user?.apply {
                ivUserCard.loadImage(avatarUrl)
                tvUserCardLogin.text = login
                cardViewUser.setDebounceClickListener { onItemClickListener?.invoke(this) }
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: ((UserModel) -> Unit)?) {
        this.onItemClickListener = onItemClickListener
    }

    class UserListViewHolder(val binding: ItemUserCardBinding) : RecyclerView.ViewHolder(binding.root)
}

object UserListDiffUtil : DiffUtil.ItemCallback<UserModel>() {

    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        Timber.d("areItemsTheSame: %s", oldItem.login.toString() + "/" + newItem.login + " " + (oldItem.login == newItem.login))
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem == newItem
    }
}