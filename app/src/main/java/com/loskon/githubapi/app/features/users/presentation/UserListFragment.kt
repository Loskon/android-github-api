package com.loskon.githubapi.app.features.users.presentation

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.snackbar.Snackbar
import com.loskon.githubapi.R
import com.loskon.githubapi.app.features.users.presentation.adapter.UserListAdapter
import com.loskon.githubapi.app.features.users.presentation.state.ErrorType
import com.loskon.githubapi.app.features.users.presentation.state.UserListUiAction
import com.loskon.githubapi.base.extension.flow.observe
import com.loskon.githubapi.base.extension.view.setGoneVisibleKtx
import com.loskon.githubapi.base.extension.view.setVisibleKtx
import com.loskon.githubapi.databinding.FragmentUserListBinding
import com.loskon.githubapi.viewbinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private val viewModel: UserListViewModel by viewModel()
    private val binding by viewBinding(FragmentUserListBinding::bind)

    private val usersAdapter = UserListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureParentLayout()
        configureRefreshLayout()
        configureUserListAdapter()
        configureRecyclerView()
        installObservers()
    }

    private fun configureParentLayout() {
        // To disable flickering during textView animation
        binding.lll.layoutTransition.disableTransitionType(LayoutTransition.APPEARING)
    }

    private fun configureRefreshLayout() {
        with(binding.refreshLayout) {
            setOnRefreshListener {
                viewModel.performRepeatRequest()
                isRefreshing = false
            }
            setColorSchemeResources(R.color.slateblue)
        }
    }

    private fun configureUserListAdapter() {
        usersAdapter.setItemClickListener { user ->
            val action = UserListFragmentDirections.goUserProfileFragment(user.login)
            findNavController().navigate(action)
        }
    }

    private fun configureRecyclerView() {
        with(binding.rvUsers) {
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = usersAdapter
        }
    }

    private fun installObservers() {
        viewModel.getUsersState.observe(viewLifecycleOwner) { users ->
            users?.let { usersAdapter.setUsers(it) }
        }

        viewModel.geuUsersAction.observe(viewLifecycleOwner) { action ->
            when (action) {
                is UserListUiAction.ShowLoadingIndicator -> showLoadingIndicator(action.loading)
                is UserListUiAction.ShowError -> showError(action.type, action.message)
            }
        }
    }

    private fun showLoadingIndicator(loading: Boolean) {
        binding.indicator.setVisibleKtx(loading)
        if (loading.not()) binding.tvNoInternet.setGoneVisibleKtx(false)
    }

    private fun showError(errorType: ErrorType, message: String?) {
        binding.indicator.setVisibleKtx(false)
        when (errorType) {
            ErrorType.EMPTY_CACHE -> binding.tvNoInternet.setGoneVisibleKtx(true)
            ErrorType.NO_SUCCESSFUL -> showSnackbar(getString(R.string.problems_get_data, message))
            ErrorType.OTHER -> showSnackbar(message)
        }
    }

    private fun showSnackbar(errorMessage: String?) {
        val message = errorMessage ?: getString(R.string.unknown_error)
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}