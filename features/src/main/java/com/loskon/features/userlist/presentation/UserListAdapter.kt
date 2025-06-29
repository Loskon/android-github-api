package com.loskon.features.userlist.presentation

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loskon.base.extension.view.setDebounceClickListener
import com.loskon.base.viewbinding.viewBinding
import com.loskon.features.databinding.ItemUserCardBinding
import com.loskon.features.model.UserModel
import com.loskon.features.util.imageloader.loadImage

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    private var list: List<UserModel> = emptyList()

    private var onItemClickListener: ((UserModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(parent.viewBinding(ItemUserCardBinding::inflate))
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val user = list[position]

        with(holder.binding) {
            user.apply {
                ivUserCard.loadImage(avatarUrl)
                tvUserCardLogin.text = login
                cardViewUser.setDebounceClickListener { onItemClickListener?.invoke(this) }
            }
        }
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setUsers(list: List<UserModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: ((UserModel) -> Unit)?) {
        this.onItemClickListener = onItemClickListener
    }

    class UserListViewHolder(val binding: ItemUserCardBinding) : RecyclerView.ViewHolder(binding.root)
}