package com.loskon.features.userprofile.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.loskon.base.datetime.toFormatString
import com.loskon.base.extension.coroutines.observe
import com.loskon.base.extension.fragment.getColor
import com.loskon.base.extension.fragment.primaryColor
import com.loskon.base.extension.view.setOnCanceledRefreshListener
import com.loskon.base.extension.view.textWithGone
import com.loskon.base.viewbinding.viewBinding
import com.loskon.base.widget.appbarlayout.AppBarLayoutState
import com.loskon.base.widget.appbarlayout.setChangeStateListener
import com.loskon.base.widget.recyclerview.AddAnimationItemAnimator
import com.loskon.base.widget.snackbar.WarningSnackbar
import com.loskon.features.R
import com.loskon.features.databinding.FragmentUserProfileBinding
import com.loskon.features.model.UserModel
import com.loskon.features.util.imageloader.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private val viewModel: UserProfileViewModel by viewModel()
    private val binding by viewBinding(FragmentUserProfileBinding::bind)
    private val args: UserProfileFragmentArgs by navArgs()

    private val repoAdapter = RepoListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) viewModel.getUser(args.username)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()
        setupViewsListener()
        installObserver()
    }

    private fun configureRecyclerView() {
        with(binding.rvRepositories) {
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = AddAnimationItemAnimator()
            adapter = repoAdapter
        }
    }

    private fun setupViewsListener() {
        binding.refreshLayoutUserProfile.setOnCanceledRefreshListener {
            viewModel.getUser(args.username)
        }
        binding.appBarUserProfile.setChangeStateListener { state ->
            if (state == AppBarLayoutState.EXPANDED) {
                binding.refreshLayoutUserProfile.isEnabled = true
            } else if (state == AppBarLayoutState.COLLAPSED) {
                binding.refreshLayoutUserProfile.isEnabled = false
            }
        }
        repoAdapter.setItemClickListener { repository ->
            val action = UserProfileFragmentDirections.openRepositoryInfoSheetFragment(repository)
            findNavController().navigate(action)
        }
        binding.bottomBarUserProfile.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun installObserver() {
        viewModel.getUserProfileState.observe(viewLifecycleOwner) {
            when (it) {
                is UserProfileState.Loading -> {
                    binding.indUserProfile.isVisible = true
                }

                is UserProfileState.Success -> {
                    binding.indUserProfile.isVisible = false
                    binding.ivUserProfile.isVisible = false
                    binding.coordLayoutUserProfile.isVisible = true
                    binding.tvNoInternetUserProfile.isVisible = false
                    setUser(it.user)
                }

                is UserProfileState.Failure -> {
                    binding.indUserProfile.isVisible = false
                    binding.ivUserProfile.isVisible = true
                    binding.tvNoInternetUserProfile.isVisible = false
                    showWarningSnackbar(getString(R.string.error_loading))
                }

                is UserProfileState.ConnectionFailure -> {
                    binding.indUserProfile.isVisible = false
                    binding.tvNoInternetUserProfile.isVisible = true

                    if (it.user != null) {
                        binding.coordLayoutUserProfile.isVisible = true
                        setUser(it.user)
                    } else {
                        binding.ivUserProfile.isVisible = true
                    }

                    showWarningSnackbar(getString(R.string.no_internet_connection))
                }
            }
        }
    }

    private fun setRepositoriesHeader(emptyRepositories: Boolean) {
        with(binding.incUserProfileCard.tvPublicRepositoriesHeader) {
            if (emptyRepositories) {
                text = getString(R.string.no_public_repositories)
                setTextColor(getColor(R.color.repositories_text_color))
            } else {
                text = getString(R.string.public_repositories)
                setTextColor(primaryColor)
            }
        }
    }

    private fun setUser(user: UserModel) {
        with(binding.incUserProfileCard) {
            user.apply {
                binding.toolbarUserProfile.title = login
                ivUserProfileAvatar.loadImage(avatarUrl)
                tvUserProfileLogin.text = login
                tvUserProfileName.textWithGone(name)
                tvUserProfileLocation.textWithGone(location)
                tvUserProfileCreatedDate.text = getString(R.string.created_date, createdAt.toFormatString())
                repoAdapter.setRepositories(repositories)
                setRepositoriesHeader(repositories.isEmpty())
            }
        }
    }

    private fun showWarningSnackbar(message: String) {
        WarningSnackbar()
            .make(binding.root, binding.bottomBarUserProfile, message, success = false)
            .setAction(getString(R.string.retry)) { viewModel.getUser(args.username) }
            .show()
    }
}