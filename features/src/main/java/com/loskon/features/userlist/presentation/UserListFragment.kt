package com.loskon.features.userlist.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.loskon.base.extension.coroutines.observe
import com.loskon.base.viewbinding.viewBinding
import com.loskon.base.widget.recyclerview.AddAnimationItemAnimator
import com.loskon.base.widget.snackbar.AppSnackbar
import com.loskon.features.R
import com.loskon.features.databinding.FragmentUserListBinding
import com.loskon.features.utils.AppPreference
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private val viewModel: UserListViewModel by viewModel()
    private val binding by viewBinding(FragmentUserListBinding::bind)

    private val userListAdapter = UserListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) getUsers()
    }

    private fun getUsers() {
        val pageSize = AppPreference.getPageSize(requireContext())
        val since = AppPreference.getSince(requireContext())

        viewModel.getUsers(pageSize, since)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureUserListAdapter()
        configureRecyclerView()
        setupViewsListener()
        installObservers()
    }

    private fun configureUserListAdapter() {
        userListAdapter.setOnItemClickListener { user ->
            val action = UserListFragmentDirections.openUserProfileFragment(user.login)
            findNavController().navigate(action)
        }
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
        binding.refreshLayoutUserList.setOnRefreshListener {
            getUsers()
            binding.refreshLayoutUserList.isRefreshing = false
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
                    binding.indicatorUserList.isVisible = false
                    userListAdapter.setUsers(it.users)
                }
                is UserListState.Failure -> {
                    binding.indicatorUserList.isVisible = false
                    showMessageSnackbar(getString(R.string.error_loading))
                }
            }
        }
    }

    private fun showMessageSnackbar(message: String) {
        AppSnackbar().make(binding.root, binding.bottomBarUsersList, message, false).show()
    }
}