package com.loskon.githubapi.app.features.userprofile.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.loskon.githubapi.R
import com.loskon.githubapi.app.features.userprofile.presentation.adapter.RepoListAdapter
import com.loskon.githubapi.app.features.userprofile.presentation.state.UserProfileState
import com.loskon.githubapi.base.extension.flow.observe
import com.loskon.githubapi.base.extension.fragment.getColorPrimary
import com.loskon.githubapi.base.extension.view.setGoneVisibleKtx
import com.loskon.githubapi.base.extension.view.textWithGone
import com.loskon.githubapi.base.presentation.dialogfragment.BaseSnackbarFragment
import com.loskon.githubapi.base.presentation.viewmodel.IOErrorType
import com.loskon.githubapi.base.widget.recyclerview.AddAnimationItemAnimator
import com.loskon.githubapi.databinding.FragmentUserProfileBinding
import com.loskon.githubapi.network.model.UserModel
import com.loskon.githubapi.utils.ImageLoader
import com.loskon.githubapi.utils.NetworkUtil
import com.loskon.githubapi.viewbinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserProfileFragment : BaseSnackbarFragment(R.layout.fragment_user_profile) {

    private val viewModel: UserProfileViewModel by viewModel(parameters = { parametersOf(args.username) })
    private val binding by viewBinding(FragmentUserProfileBinding::bind)
    private val args: UserProfileFragmentArgs by navArgs()

    private val repositoriesAdapter = RepoListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRefreshLayout()
        configureRepoListAdapter()
        configureRecyclerView()
        setupViewsListener()
        installObserver()
    }

    private fun configureRefreshLayout() {
        with(binding.refreshLayoutUserProfile) {
            setOnRefreshListener {
                viewModel.performUserRequest()
                isRefreshing = false
            }
            setColorSchemeColors(getColorPrimary)
        }
    }

    private fun configureRepoListAdapter() {
        repositoriesAdapter.setItemClickListener { repository ->
            val action = UserProfileFragmentDirections.goRepositoryInfoBottomSheetFragment(repository)
            findNavController().navigate(action)
        }
    }

    private fun configureRecyclerView() {
        with(binding.incUserProfile.rvRepositories) {
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = AddAnimationItemAnimator()
            adapter = repositoriesAdapter
        }
    }

    private fun setupViewsListener() {
        binding.bottomBarUserProfile.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    private fun installObserver() {
        viewModel.getUserProfileState.observe(viewLifecycleOwner) { userProfileState ->
            displayViews(userProfileState)
            userProfileState.user?.let { setUser(it) }
        }

        viewModel.getIoErrorState.observe(viewLifecycleOwner) { ioErrorState ->
            if (ioErrorState?.type != null) showError(ioErrorState.type, ioErrorState.message)
        }
    }

    private fun displayViews(userProfileState: UserProfileState) {
        with(binding) {
            incUserProfile.root.setGoneVisibleKtx(userProfileState.user != null)
            indicatorUserProfile.setGoneVisibleKtx(userProfileState.loading)
            tvOfflineModeUserProfile.setGoneVisibleKtx(userProfileState.fromCache)
        }
    }

    private fun setUser(user: UserModel) {
        with(binding.incUserProfile) {
            user.apply {
                ImageLoader.loadImage(avatarUrl, ivUserProfileAvatar)
                tvUserProfileLogin.text = login
                tvUserProfileName.textWithGone(name)
                tvUserProfileLocation.textWithGone(location)
                tvRepositoriesEmpty.setGoneVisibleKtx(repositories.isEmpty())
                repositoriesAdapter.setRepositories(repositories)
            }
        }
    }

    private fun showError(errorType: IOErrorType, message: String?) {
        when (errorType) {
            IOErrorType.EMPTY_CACHE -> showTextSnackbar(getString(R.string.no_internet_connection))
            IOErrorType.NO_SUCCESSFUL -> showTextSnackbar(getString(R.string.problems_get_data, message))
            IOErrorType.TIMEOUT -> showTimeoutSnackbar()
            IOErrorType.UNKNOWN_HOST -> showUnknownHostSnackbar()
            IOErrorType.OTHER -> showActionSnackbar(message)
        }
    }

    // TODO
    private fun showTimeoutSnackbar() {
        if (NetworkUtil.hasConnected(requireContext()).not()) {
            showTextSnackbar(getString(R.string.no_internet_connection))
        } else {
            showActionSnackbar(getString(R.string.timeout))
        }
    }

    private fun showUnknownHostSnackbar() {
        if (NetworkUtil.hasConnected(requireContext()).not()) {
            showTextSnackbar(getString(R.string.no_internet_connection))
        } else {
            showActionSnackbar(getString(R.string.unknown_host))
        }
    }

    private fun showActionSnackbar(message: String?) {
        showActionSnackbar(binding.root, binding.bottomBarUserProfile, message) {
            viewModel.performUserRequest()
        }
    }

    private fun showTextSnackbar(message: String?) {
        showTextSnackbar(binding.root, binding.bottomBarUserProfile, message)
    }
}