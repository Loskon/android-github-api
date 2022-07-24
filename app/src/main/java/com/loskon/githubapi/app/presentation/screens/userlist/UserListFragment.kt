package com.loskon.githubapi.app.presentation.screens.userlist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.loskon.githubapi.R
import com.loskon.githubapi.app.base.extension.flow.observe
import com.loskon.githubapi.app.base.extension.view.setGoneVisibleKtx
import com.loskon.githubapi.app.base.presentation.fragment.BaseSnackbarFragment
import com.loskon.githubapi.app.base.presentation.viewmodel.IOErrorType
import com.loskon.githubapi.app.base.widget.recyclerview.AddAnimationItemAnimator
import com.loskon.githubapi.app.presentation.screens.userlist.adapter.UserListAdapter
import com.loskon.githubapi.app.presentation.screens.userlist.state.UserListState
import com.loskon.githubapi.databinding.FragmentUserListBinding
import com.loskon.githubapi.utils.AppPreference
import com.loskon.githubapi.viewbinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment : BaseSnackbarFragment(R.layout.fragment_user_list) {

    private val viewModel: UserListViewModel by viewModel()
    private val binding by viewBinding(FragmentUserListBinding::bind)

    private val usersAdapter = UserListAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        performUsersRequest()
    }

    private fun performUsersRequest() {
        val pageSize = AppPreference.getPageSize(requireContext())
        val since = AppPreference.getSince(requireContext())
        viewModel.performUsersRequest(pageSize, since)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureUserListAdapter()
        configureRecyclerView()
        setupViewsListener()
        installObservers()
    }

    private fun configureUserListAdapter() {
        usersAdapter.setOnItemClickListener { user ->
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

    private fun setupViewsListener() {
        binding.refreshLayoutUserList.setOnRefreshListener {
            performUsersRequest()
            binding.refreshLayoutUserList.isRefreshing = false
        }

        binding.bottomBarUsersList.setNavigationOnClickListener {
            val action = UserListFragmentDirections.openSettingsFragment()
            findNavController().navigate(action)
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
        showActionSnackbar(binding.root, binding.bottomBarUsersList, message) { performUsersRequest() }
    }
}