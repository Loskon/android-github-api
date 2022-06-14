package com.loskon.githubapi.app.features.userprofile.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.loskon.githubapi.R
import com.loskon.githubapi.base.extension.flow.observe
import com.loskon.githubapi.databinding.FragmentUserProfileBinding
import com.loskon.githubapi.viewbinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private val viewModel: UserProfileViewModel by viewModel(parameters = { parametersOf(args.username) })
    private val binding by viewBinding(FragmentUserProfileBinding::bind)
    private val args: UserProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUser.observe(viewLifecycleOwner) { user ->
            binding.lll2.isVisible = user != null
            binding.piUserProfile.isVisible = user == null
            user?.let { binding.tv.text = user.login }
        }

        binding.bottomAppBar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }
}