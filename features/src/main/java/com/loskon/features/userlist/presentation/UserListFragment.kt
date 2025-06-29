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
import com.loskon.base.widget.recyclerview.AddAnimationItemAnimator
import com.loskon.base.widget.snackbar.WarningSnackbar
import com.loskon.features.R
import com.loskon.features.databinding.FragmentUserListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private val viewModel: UserListViewModel by viewModel()
    private val binding by viewBinding(FragmentUserListBinding::bind)
    private val userListAdapter = UserListAdapter()

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
            itemAnimator = AddAnimationItemAnimator()
            adapter = userListAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupViewsListener() {
        binding.refreshLayoutUserList.setOnCanceledRefreshListener {
            viewModel.getUsers()
        }
        userListAdapter.setOnItemClickListener { user ->
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
                    binding.indUserList.isVisible = true
                }

                is UserListState.Success -> {
                    binding.indUserList.isVisible = false
                    binding.ivUserList.isVisible = false
                    binding.tvNoInternetUserList.isVisible = false
                    userListAdapter.setUsers(it.users)
                }

                is UserListState.Failure -> {
                    binding.indUserList.isVisible = false
                    binding.ivUserList.isVisible = true
                    binding.tvNoInternetUserList.isVisible = false
                    showWarningSnackbar(getString(R.string.error_loading))
                }

                is UserListState.ConnectionFailure -> {
                    binding.indUserList.isVisible = false
                    binding.tvNoInternetUserList.isVisible = true

                    if (it.users?.isNotEmpty() == true) {
                        userListAdapter.setUsers(it.users)
                    } else {
                        binding.ivUserList.isVisible = true
                    }

                    showWarningSnackbar(getString(R.string.no_internet_connection))
                }
            }
        }
    }

    private fun showWarningSnackbar(message: String) {
        WarningSnackbar()
            .make(binding.root, binding.bottomBarUsersList, message, success = false)
            .setAction(getString(R.string.retry)) { viewModel.getUsers() }
            .show()
    }
}