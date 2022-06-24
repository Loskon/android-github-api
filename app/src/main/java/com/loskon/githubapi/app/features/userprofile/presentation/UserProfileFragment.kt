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
import com.loskon.githubapi.base.datetime.toFormatString
import com.loskon.githubapi.base.extension.flow.observe
import com.loskon.githubapi.base.extension.fragment.colorPrimary
import com.loskon.githubapi.base.extension.fragment.getColor
import com.loskon.githubapi.base.extension.view.setGoneVisibleKtx
import com.loskon.githubapi.base.extension.view.textWithGone
import com.loskon.githubapi.base.presentation.dialogfragment.BaseSnackbarFragment
import com.loskon.githubapi.base.presentation.viewmodel.IOErrorType
import com.loskon.githubapi.base.widget.appbarlayout.AppBarLayoutState
import com.loskon.githubapi.base.widget.appbarlayout.OnAppBarLayoutStateChangeListener
import com.loskon.githubapi.base.widget.recyclerview.AddAnimationItemAnimator
import com.loskon.githubapi.databinding.FragmentUserProfile3Binding
import com.loskon.githubapi.network.model.UserModel
import com.loskon.githubapi.utils.ImageLoader
import com.loskon.githubapi.utils.NetworkUtil
import com.loskon.githubapi.viewbinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserProfileFragment : BaseSnackbarFragment(R.layout.fragment_user_profile3) {

    private val viewModel: UserProfileViewModel by viewModel(parameters = { parametersOf(args.username) })
    private val binding by viewBinding(FragmentUserProfile3Binding::bind)
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
            setProgressBackgroundColorSchemeColor(getColor(R.color.swipe_background_color))
            setColorSchemeColors(colorPrimary)
            setOnRefreshListener {
                viewModel.performUserRequest()
                isRefreshing = false
            }
        }
    }

    private fun configureRepoListAdapter() {
        repositoriesAdapter.setItemClickListener { repository ->
            val action = UserProfileFragmentDirections.openRepositoryInfoBottomSheetFragment(repository)
            findNavController().navigate(action)
        }
    }

    private fun configureRecyclerView() {
        with(binding.rvRepositories) {
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = AddAnimationItemAnimator()
            adapter = repositoriesAdapter
        }
    }

    private fun setupViewsListener() {
        binding.appBarUserProfile.addOnOffsetChangedListener(OnAppBarLayoutStateChangeListener { _, state ->
            if (state == AppBarLayoutState.EXPANDED) {
                binding.refreshLayoutUserProfile.isEnabled = true
            } else if (state == AppBarLayoutState.COLLAPSED) {
                binding.refreshLayoutUserProfile.isEnabled = false
            }
        })
        binding.bottomBarUserProfile.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    private fun installObserver() {
        viewModel.getUserProfileState.observe(viewLifecycleOwner) { userProfileState ->
            changeRepositoriesHeader(userProfileState.user?.repositories?.isEmpty() == true)
            displayViews(userProfileState)
            if (userProfileState.user != null) setUser(userProfileState.user)
        }

        viewModel.getIoErrorState.observe(viewLifecycleOwner) { ioErrorState ->
            if (ioErrorState?.type != null) showError(ioErrorState.type, ioErrorState.message)
        }
    }

    private fun changeRepositoriesHeader(isEmpty: Boolean) {
        with(binding.incUserProfileCard.tvPublicRepositoriesHeader) {
            if (isEmpty) {
                text = getString(R.string.no_public_repositories)
                setTextColor(getColor(R.color.error_color))
            } else {
                text = getString(R.string.public_repositories)
                setTextColor(colorPrimary)
            }
        }
    }

    private fun displayViews(userProfileState: UserProfileState) {
        with(binding) {
            appBarUserProfile.setGoneVisibleKtx(userProfileState.user != null)
            indicatorUserProfile.setGoneVisibleKtx(userProfileState.loading)
            tvOfflineModeUserProfile.setGoneVisibleKtx(userProfileState.fromCache)
        }
    }

    private fun setUser(user: UserModel) {
        with(binding.incUserProfileCard) {
            user.apply {
                binding.toolbarUserProfile.title = login
                ImageLoader.loadImage(avatarUrl, ivUserProfileAvatar)
                tvUserProfileLogin.text = login
                tvUserProfileName.textWithGone(name)
                tvUserProfileLocation.textWithGone(location)
                tvUserProfileCreatedDate.text = getString(R.string.created_date, createdAt.toFormatString())
                repositoriesAdapter.setRepositories(repositories.take(10))
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