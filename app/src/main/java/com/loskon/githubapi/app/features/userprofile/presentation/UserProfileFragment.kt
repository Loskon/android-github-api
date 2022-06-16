package com.loskon.githubapi.app.features.userprofile.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.loskon.githubapi.R
import com.loskon.githubapi.app.features.userprofile.presentation.adapter.RepositoryListAdapter
import com.loskon.githubapi.base.extension.flow.observe
import com.loskon.githubapi.base.extension.view.textWithGone
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

    private val repositoriesAdapter = RepositoryListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRepositoryListAdapter()
        configureRecyclerView()
        setupViewsListener()
        installObserver()
    }

    private fun configureRepositoryListAdapter() {
        repositoriesAdapter.setItemClickListener { repository ->
             val action = UserProfileFragmentDirections.goRepositoryInfoBottomSheetFragment()
             findNavController().navigate(action)
        }
    }

    private fun configureRecyclerView() {
        with(binding.incUserProfile.rvRepositories) {
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            layoutManager = LinearLayoutManager(requireContext())
            adapter = repositoriesAdapter
        }
    }

    private fun installObserver() {
        viewModel.getUser.observe(viewLifecycleOwner) { user ->
            binding.incUserProfile.root.isVisible = user != null
            binding.piUserProfile.isVisible = user == null
            user?.let { setUser(it) }
        }
    }

    private fun setUser(user: UserModel) {
        with(binding.incUserProfile) {
            ImageLoader.loadImage(user.avatarUrl, ivUserProfileAvatar)
            tvUserProfileLogin.text = user.login
            tvUserProfileName.textWithGone(user.name)
            tvUserProfileLocation.textWithGone(user.location)
        }

        repositoriesAdapter.setRepositories(user.repositories)
    }

    private fun setupViewsListener() {
        binding.bottomAppBar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }
}