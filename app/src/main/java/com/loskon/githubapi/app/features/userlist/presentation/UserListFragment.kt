package com.loskon.githubapi.app.features.userlist.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.loskon.githubapi.R
import com.loskon.githubapi.app.features.userlist.presentation.adapter.UserListAdapter
import com.loskon.githubapi.app.features.userlist.presentation.state.UserListState
import com.loskon.githubapi.base.extension.flow.observe
import com.loskon.githubapi.base.extension.fragment.colorPrimary
import com.loskon.githubapi.base.extension.fragment.getColor
import com.loskon.githubapi.base.extension.view.setGoneVisibleKtx
import com.loskon.githubapi.base.presentation.dialogfragment.BaseSnackbarFragment
import com.loskon.githubapi.base.presentation.viewmodel.IOErrorType
import com.loskon.githubapi.base.widget.recyclerview.AddAnimationItemAnimator
import com.loskon.githubapi.databinding.FragmentUserListBinding
import com.loskon.githubapi.sharedpreference.AppPreference
import com.loskon.githubapi.viewbinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserListFragment : BaseSnackbarFragment(R.layout.fragment_user_list) {

    private val viewModel: UserListViewModel by viewModel(parameters = { parametersOf(pageSize) })
    private val binding by viewBinding(FragmentUserListBinding::bind)

    private val usersAdapter = UserListAdapter()

    private val pageSize: Int get() = AppPreference.getPageSize(requireContext())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRefreshLayout()
        configureUserListAdapter()
        configureRecyclerView()
        setupViewsListener()
        installObservers()
    }

    private fun setupViewsListener() {
        binding.bottomBarUsersList.setNavigationOnClickListener {
            val action = UserListFragmentDirections.openSettingsFragment()
            findNavController().navigate(action)
        }
    }

    private fun configureRefreshLayout() {
        with(binding.refreshLayoutUserList) {
            setProgressBackgroundColorSchemeColor(getColor(R.color.swipe_background_color))
            setColorSchemeColors(colorPrimary)
            setOnRefreshListener {
                viewModel.performUsersRequest(pageSize)
                isRefreshing = false
            }
        }
    }

    private fun configureUserListAdapter() {
        usersAdapter.setItemClickListener { user ->
            val action = UserListFragmentDirections.openUserProfileFragment(user.login)
            findNavController().navigate(action)
        }
    }

    private fun configureRecyclerView() {
        with(binding.rvUserList) {
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = AddAnimationItemAnimator()
            adapter = usersAdapter
            setHasFixedSize(true)
        }
    }

    private fun installObservers() {
        viewModel.getUserListState.observe(viewLifecycleOwner) { userListState ->
            displayViews(userListState)
            userListState.users?.let { usersAdapter.setUsers(it) }
        }

        viewModel.getIoErrorState.observe(viewLifecycleOwner) { ioErrorState ->
            if (ioErrorState?.type != null) showError(ioErrorState.type, ioErrorState.message)
        }
    }

    private fun displayViews(userProfileState: UserListState) {
        with(binding) {
            indicatorUserList.setGoneVisibleKtx(userProfileState.loading)
            tvNoInternetUserList.setGoneVisibleKtx(userProfileState.fromCache)
        }
    }

    private fun showError(errorType: IOErrorType, message: String?) {
        when (errorType) {
            IOErrorType.EMPTY_CACHE -> showTextSnackbar(getString(R.string.no_internet_connection))
            IOErrorType.NO_SUCCESSFUL -> showTextSnackbar(getString(R.string.problems_get_data, message))
            IOErrorType.TIMEOUT -> showActionSnackbar(getString(R.string.timeout))
            IOErrorType.UNKNOWN_HOST -> showActionSnackbar(getString(R.string.unknown_host))
            IOErrorType.OTHER -> showTextSnackbar(message)
        }
    }

    private fun showTextSnackbar(message: String?) {
        showTextSnackbar(binding.root, binding.bottomBarUsersList, message)
    }

    private fun showActionSnackbar(message: String?) {
        showActionSnackbar(binding.root, binding.bottomBarUsersList, message) {
            viewModel.performUsersRequest(pageSize)
        }
    }
}