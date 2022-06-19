package com.loskon.githubapi.app.features.userprofile.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.snackbar.Snackbar
import com.loskon.githubapi.R
import com.loskon.githubapi.app.features.userprofile.presentation.adapter.RepoListAdapter
import com.loskon.githubapi.app.features.userprofile.presentation.state.UserProfileState
import com.loskon.githubapi.base.extension.flow.observe
import com.loskon.githubapi.base.extension.fragment.getColorPrimary
import com.loskon.githubapi.base.extension.view.setGoneVisibleKtx
import com.loskon.githubapi.base.extension.view.textWithGone
import com.loskon.githubapi.base.presentation.viewmodel.IOErrorType
import com.loskon.githubapi.base.widget.recyclerview.AddAnimationItemAnimator
import com.loskon.githubapi.databinding.FragmentUserProfileBinding
import com.loskon.githubapi.network.glide.ImageLoader
import com.loskon.githubapi.network.retrofit.model.UserModel
import com.loskon.githubapi.viewbinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

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
            setColorSchemeColors(getColorPrimary())
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

        viewModel.getIoErrorState.observe(viewLifecycleOwner) {
            if (it != null) showError(it.type, it.message)
        }
    }

    private fun displayViews(userProfileState: UserProfileState) {
        with(binding) {
            //Timber.d(userProfileState.loading.toString())
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

    private fun showError(errorType: IOErrorType?, message: String?) {
        Timber.d(errorType.toString())
        when (errorType) {
            IOErrorType.EMPTY_CACHE -> showSnackbar(getString(R.string.no_internet_connection))
            IOErrorType.NO_SUCCESSFUL -> showSnackbar(getString(R.string.problems_get_data, message))
            IOErrorType.TIMEOUT -> showSnackbar(getString(R.string.timeout))
            IOErrorType.UNKNOWN_HOST -> showSnackbar(getString(R.string.unknown_host))
            IOErrorType.OTHER -> showSnackbar(message)
            null -> {}
        }
    }

    private fun showSnackbar(errorMessage: String?) {
        val message = errorMessage ?: getString(R.string.unknown_error)
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).setAnchorView(binding.bottomBarUserProfile).show()
    }
}