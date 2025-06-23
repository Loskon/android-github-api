package com.loskon.features.userlist.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.loskon.base.extension.coroutines.observe
import com.loskon.base.extension.view.setOnCanceledRefreshListener
import com.loskon.base.viewbinding.viewBinding
import com.loskon.base.widget.recyclerview.AddItemAnimator
import com.loskon.base.widget.snackbar.WarningSnackbar
import com.loskon.features.R
import com.loskon.features.databinding.FragmentUserListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private val viewModel: UserListViewModel by viewModel()
    private val binding by viewBinding(FragmentUserListBinding::bind)

    private val userListPagingAdapter = UserListPagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) viewModel.getUsers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()
        setupViewsListener()
        installObservers()
    }

    private fun configureRecyclerView() {
        with(binding.rvUserList) {
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = AddItemAnimator()
            setHasFixedSize(true)

            adapter = userListPagingAdapter
        }
    }

    private fun setupViewsListener() {
        binding.refreshLayoutUserList.setOnCanceledRefreshListener {
            userListPagingAdapter.refresh()
        }
        userListPagingAdapter.setOnItemClickListener { user ->
            val action = UserListFragmentDirections.openUserProfileFragment(user.login)
            findNavController().navigate(action)
        }
        binding.bottomBarUsersList.setNavigationOnClickListener {
            val action = UserListFragmentDirections.openSettingsFragment()
            findNavController().navigate(action)
        }
    }

    private fun installObservers() {
        viewModel.getUserListState.observe(viewLifecycleOwner) {
            when (it) {
                is UserListState.Loading -> {
                    binding.indicatorUserList.isVisible = true
                }

                is UserListState.Success -> {
                    binding.tvNoInternetUserList.isVisible = it.hasConnect.not()
                    binding.indicatorUserList.isVisible = false
                    userListPagingAdapter.submitData(it.users)
                    Timber.d("submitData")
                }

                is UserListState.Failure -> {
                    binding.indicatorUserList.isVisible = false
                    binding.tvNoInternetUserList.isVisible = false
                    showWarningSnackbar(getString(R.string.error_loading))
                }
            }
        }
    }

    private fun showWarningSnackbar(message: String) {
        WarningSnackbar().make(binding.root, binding.bottomBarUsersList, message, success = false).show()
    }
}