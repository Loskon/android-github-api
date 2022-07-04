package com.loskon.githubapi.app.presentation.screens.repositoryinfo

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.loskon.githubapi.R
import com.loskon.githubapi.app.base.datetime.toFormatString
import com.loskon.githubapi.app.base.extension.flow.observe
import com.loskon.githubapi.app.base.extension.view.setDebounceClickListener
import com.loskon.githubapi.app.base.extension.view.setGoneVisibleKtx
import com.loskon.githubapi.app.base.presentation.dialogfragment.BaseBottomSheetDialogFragment
import com.loskon.githubapi.databinding.SheetRepositoryInfoBinding
import com.loskon.githubapi.domain.model.RepositoryModel
import com.loskon.githubapi.viewbinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RepoInfoBottomSheetFragment : BaseBottomSheetDialogFragment(R.layout.sheet_repository_info) {

    private val viewModel: RepoInfoViewModel by viewModel(parameters = { parametersOf(args.repository) })
    private val binding by viewBinding(SheetRepositoryInfoBinding::bind)
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
                tvRepositoryCreatedDate.text = createdAt.toFormatString()
                tvRepositoryUpdatedDate.text = updatedAt.toFormatString()
                tvRepositoryPushedDate.text = pushedAt.toFormatString()
                tvRepositoryUrl.text = htmlUrl
            }
        }
    }
}