package com.loskon.githubapi.app.features.userprofile.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.loskon.githubapi.R
import com.loskon.githubapi.app.features.userprofile.presentation.adapter.RepoListAdapter
import com.loskon.githubapi.base.extension.flow.observe
import com.loskon.githubapi.base.extension.view.setGoneVisibleKtx
import com.loskon.githubapi.base.extension.view.textWithGone
import com.loskon.githubapi.base.widget.AddAnimationItemAnimator
import com.loskon.githubapi.databinding.FragmentUserProfileBinding
import com.loskon.githubapi.network.glide.ImageLoader
import com.loskon.githubapi.network.retrofit.model.UserModel
import com.loskon.githubapi.viewbinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private val viewModel: UserProfileViewModel by viewModel(parameters = { parametersOf(args.username) })
    private val binding by viewBinding(FragmentUserProfileBinding::bind)
    private val args: UserProfileFragmentArgs by navArgs()

    private val repositoriesAdapter = RepoListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRepositoryListAdapter()
        configureRecyclerView()
        setupViewsListener()
        installObserver()
    }

    private fun configureRepositoryListAdapter() {
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

    private fun installObserver() {
        viewModel.getUserProfileState.observe(viewLifecycleOwner) { user ->
            binding.incUserProfile.root.setGoneVisibleKtx(user != null)
            binding.piUserProfile.setGoneVisibleKtx(user == null)
            user?.let { setUser(it) }
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

    private fun setupViewsListener() {
        binding.bottomAppBar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }
}