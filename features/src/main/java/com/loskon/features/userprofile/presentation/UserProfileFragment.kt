package com.loskon.features.userprofile.presentation

import android.content.Context
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
import com.loskon.base.extension.view.textWithGone
import com.loskon.base.viewbinding.viewBinding
import com.loskon.base.widget.appbarlayout.AppBarLayoutState
import com.loskon.base.widget.appbarlayout.setOnStateChangedListener
import com.loskon.base.widget.recyclerview.AddAnimationItemAnimator
import com.loskon.base.widget.snackbar.AppSnackbar
import com.loskon.features.R
import com.loskon.features.databinding.FragmentUserProfileBinding
import com.loskon.features.model.UserModel
import com.loskon.network.imageloader.ImageLoader
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private val viewModel: UserProfileViewModel by viewModel()
    private val binding by viewBinding(FragmentUserProfileBinding::bind)
    private val args: UserProfileFragmentArgs by navArgs()

    private val repositoriesAdapter = RepoListAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.getUser(args.username)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRepoListAdapter()
        configureRecyclerView()
        setupViewsListener()
        installObserver()
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
        binding.refreshLayoutUserProfile.setOnRefreshListener {
            viewModel.getUser(args.username)
            binding.refreshLayoutUserProfile.isRefreshing = false
        }
        binding.appBarUserProfile.setOnStateChangedListener { state ->
            if (state == AppBarLayoutState.EXPANDED) {
                binding.refreshLayoutUserProfile.isEnabled = true
            } else if (state == AppBarLayoutState.COLLAPSED) {
                binding.refreshLayoutUserProfile.isEnabled = false
            }
        }
        binding.bottomBarUserProfile.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun installObserver() {
        viewModel.getUserProfileState.observe(viewLifecycleOwner) {
            when (it) {
                is UserProfileState.Loading -> {
                    binding.indicatorUserProfile.isVisible = true
                }
                is UserProfileState.Success -> {
                    binding.indicatorUserProfile.isVisible = false
                    binding.appBarUserProfile.isVisible = true
                    setRepositoriesHeader(it.user.repositories.isEmpty())
                    setUser(it.user)
                }
                is UserProfileState.Failure -> {
                    binding.indicatorUserProfile.isVisible = false
                    showMessageSnackbar(getString(R.string.error_loading))
                }
            }
        }
    }

    private fun setRepositoriesHeader(emptyRepositories: Boolean) {
        with(binding.incUserProfileCard.tvPublicRepositoriesHeader) {
            if (emptyRepositories) {
                text = getString(R.string.no_public_repositories)
                setTextColor(getColor(R.color.error_color))
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
                ImageLoader.load(ivUserProfileAvatar, avatarUrl)
                tvUserProfileLogin.text = login
                tvUserProfileName.textWithGone(name)
                tvUserProfileLocation.textWithGone(location)
                tvUserProfileCreatedDate.text = getString(R.string.created_date, createdAt.toFormatString())
                repositoriesAdapter.setRepositories(repositories)
            }
        }
    }

    private fun showMessageSnackbar(message: String) {
        AppSnackbar().make(binding.root, binding.bottomBarUserProfile, message, false).show()
    }
}