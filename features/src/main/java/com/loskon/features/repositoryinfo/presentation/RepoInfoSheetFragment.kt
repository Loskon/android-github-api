package com.loskon.features.repositoryinfo.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.loskon.base.datetime.toFormatString
import com.loskon.base.extension.coroutines.observe
import com.loskon.base.extension.view.setDebounceClickListener
import com.loskon.base.extension.view.setGoneVisibleKtx
import com.loskon.base.presentation.sheetdialogfragment.BaseBottomSheetDialogFragment
import com.loskon.base.viewbinding.viewBinding
import com.loskon.features.R
import com.loskon.features.databinding.SheetRepositoryInfoBinding
import com.loskon.features.model.RepositoryModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepoInfoSheetFragment : BaseBottomSheetDialogFragment(R.layout.sheet_repository_info) {

    private val viewModel by viewModel<RepoInfoViewModel>()
    private val binding by viewBinding(SheetRepositoryInfoBinding::bind)
    private val args: RepoInfoSheetFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) viewModel.setRepository(args.repository)
    }

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