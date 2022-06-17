package com.loskon.githubapi.app.features.repositoryinfo.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.loskon.githubapi.R
import com.loskon.githubapi.base.datetime.toFormattedString
import com.loskon.githubapi.base.extension.flow.observe
import com.loskon.githubapi.base.extension.view.setDebounceClickListener
import com.loskon.githubapi.base.extension.view.setGoneVisibleKtx
import com.loskon.githubapi.base.presentation.BaseBottomSheetDialogFragment
import com.loskon.githubapi.databinding.SheetRepositoryInfo2Binding
import com.loskon.githubapi.network.retrofit.model.RepositoryModel
import com.loskon.githubapi.viewbinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RepoInfoBottomSheetFragment : BaseBottomSheetDialogFragment(R.layout.sheet_repository_info2) {

    private val viewModel: RepoInfoViewModel by viewModel(parameters = { parametersOf(args.repository) })
    private val binding by viewBinding(SheetRepositoryInfo2Binding::bind)
    private val args: RepoInfoBottomSheetFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewsListener()
        installObserver()
    }

    private fun setupViewsListener() {
        binding.btnSheetRepositoryInfoClose.setDebounceClickListener { dismiss() }
    }

    private fun installObserver() {
        viewModel.getRepoInfoState.observe(viewLifecycleOwner) { repository ->
            repository?.let { setRepository(it) }
        }
    }

    private fun setRepository(repository: RepositoryModel) {
        with(binding) {
            repository.apply {
                tvRepositoryName.text = fullName

                if (description.isNotEmpty()) {
                    tvRepositoryDescreption.text = description
                } else {
                    tvRepositoryDescreptionHeader.setGoneVisibleKtx(false)
                    tvRepositoryDescreption.setGoneVisibleKtx(false)
                }

                if (language.isNotEmpty()) {
                    tvRepositoryLanguage.text = language
                } else {
                    tvRepositoryLanguageHeader.setGoneVisibleKtx(false)
                    tvRepositoryLanguage.setGoneVisibleKtx(false)
                }

                tvRepositorySize.text = size.toString()
                tvRepositoryCreatedDate.text = createdAt.toFormattedString()
                tvRepositoryUpdatedDate.text = updatedAt.toFormattedString()
                tvRepositoryPushedDate.text = pushedAt.toFormattedString()
                tvRepositoryUrl.text = htmlUrl
            }
        }
    }
}