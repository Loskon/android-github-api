package com.loskon.githubapi.app.features.userlist.presentation

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.snackbar.Snackbar
import com.loskon.githubapi.R
import com.loskon.githubapi.app.features.userlist.presentation.adapter.UserListAdapter
import com.loskon.githubapi.app.features.userlist.presentation.state.ErrorTypeUserList
import com.loskon.githubapi.app.features.userlist.presentation.state.UserListAction
import com.loskon.githubapi.base.extension.content.getThemeMaterialColorKtx
import com.loskon.githubapi.base.extension.flow.observe
import com.loskon.githubapi.base.extension.view.setGoneVisibleKtx
import com.loskon.githubapi.base.extension.view.setVisibleKtx
import com.loskon.githubapi.base.widget.recyclerview.AddAnimationItemAnimator
import com.loskon.githubapi.databinding.FragmentUserListBinding
import com.loskon.githubapi.utils.AppPreference
import com.loskon.githubapi.utils.ColorUtil
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
        setupViewsListener()
        installObservers()
    }

    private fun setupViewsListener() {
        binding.bottomBarUsersList.setNavigationOnClickListener {
            val theme = AppPreference.hasDarkMode(requireContext()).not()
            AppPreference.setDarkMode(requireContext(), theme)
            ColorUtil.toggleDarkTheme(theme)
        }
    }

    private fun configureParentLayout() {
        // To disable flickering animation
        binding.linLayoutUserList.layoutTransition.disableTransitionType(LayoutTransition.APPEARING)
    }

    private fun configureRefreshLayout() {
        with(binding.refreshLayoutUserList) {
            setOnRefreshListener {
                viewModel.performUsersRequest()
                isRefreshing = false
            }
            val color = requireContext().getThemeMaterialColorKtx(android.R.attr.colorPrimary)
            setColorSchemeColors(color)
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
            itemAnimator = AddAnimationItemAnimator()
            adapter = usersAdapter
            setHasFixedSize(true)
        }
    }

    private fun installObservers() {
        viewModel.getUsersState.observe(viewLifecycleOwner) { users ->
            users?.let { usersAdapter.setUsers(it) }
        }

        viewModel.geuUsersAction.observe(viewLifecycleOwner) { action ->
            when (action) {
                is UserListAction.ShowLoadingIndicator -> showLoadingIndicator(action.loading)
                is UserListAction.ShowError -> showError(action.type, action.message)
            }
        }
    }

    private fun showLoadingIndicator(loading: Boolean) {
        binding.indicatorUserList.setVisibleKtx(loading)
        if (loading.not()) binding.tvNoInternetUserList.setGoneVisibleKtx(false)
    }

    private fun showError(errorType: ErrorTypeUserList, message: String?) {
        binding.indicatorUserList.setVisibleKtx(false)
        when (errorType) {
            ErrorTypeUserList.EMPTY_CACHE -> binding.tvNoInternetUserList.setGoneVisibleKtx(true)
            ErrorTypeUserList.NO_SUCCESSFUL -> showSnackbar(getString(R.string.problems_get_data, message))
            ErrorTypeUserList.TIMEOUT -> showSnackbar(getString(R.string.timeout))
            ErrorTypeUserList.UNKNOWN_HOST -> showSnackbar(getString(R.string.unknown_host))
            ErrorTypeUserList.OTHER -> showSnackbar(message)
        }
    }

    private fun showSnackbar(errorMessage: String?) {
        val message = errorMessage ?: getString(R.string.unknown_error)
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).setAnchorView(binding.bottomBarUsersList).show()
    }
}