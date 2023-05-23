package com.loskon.features.userlist.presentation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.loskon.base.extension.view.setDebounceClickListener
import com.loskon.base.viewbinding.viewBinding
import com.loskon.base.widget.recyclerview.RecyclerDiffUtil
import com.loskon.features.databinding.ItemAdCardBinding
import com.loskon.features.databinding.ItemUserCardBinding
import com.loskon.features.model.UserModel
import com.loskon.network.imageloader.ImageLoader

private const val ADS_AFTER: Int = 10
private const val ITEM_VIEW = 0
private const val AD_VIEW = 1

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    private var list: List<UserModel> = emptyList()

    private var onItemClickListener: ((UserModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return if (viewType == AD_VIEW) {
            AdViewHolder(parent.viewBinding(ItemAdCardBinding::inflate))
        } else {
            UserViewHolder(parent.viewBinding(ItemUserCardBinding::inflate))
        }
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        if(getItemViewType(position) == AD_VIEW){
            val adHolder = holder as AdViewHolder

        } else {
            val user = list[position]
            val userHolder = holder as UserViewHolder

            with(userHolder.binding) {
                user.apply {
                    ImageLoader.load(ivUserCard, avatarUrl)
                    tvUserCardLogin.text = login
                    cardViewUser.setDebounceClickListener { onItemClickListener?.invoke(this) }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        val listSize = list.size
        var listWithAdSize = listSize + listSize / ADS_AFTER
        if (listSize > 0) listWithAdSize++
        return listWithAdSize
    }

    override fun getItemViewType(position: Int): Int {
        return if (position != 0 && position % ADS_AFTER == 0) {
            AD_VIEW
        } else {
            ITEM_VIEW
        }
    }

    fun setUsers(list: List<UserModel>) {
        val diffUtil = RecyclerDiffUtil(this.list, list, userListDiffUtil)
        val diffResult = DiffUtil.calculateDiff(diffUtil, false)
        this.list = list
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(onItemClickListener: ((UserModel) -> Unit)?) {
        this.onItemClickListener = onItemClickListener
    }

    private class UserViewHolder(val binding: ItemUserCardBinding) : UserListViewHolder(binding.root)
    private class AdViewHolder(val binding: ItemAdCardBinding) : UserListViewHolder(binding.root)
    open class UserListViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

class ExerciseViewHolder : RecyclerView.ViewHolder {
    var exerciseBinding: ItemUserCardBinding? = null
    var relaxBinding: ItemAdCardBinding? = null

    constructor(binding: ItemUserCardBinding) : super(binding.root) {
        exerciseBinding = binding
    }

    constructor(binding: ItemAdCardBinding) : super(binding.root) {
        relaxBinding = binding
    }
}