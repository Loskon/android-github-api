package com.loskon.features.repositoryinfo.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.loskon.base.customtab.CustomTab
import com.loskon.base.datetime.toFormatString
import com.loskon.base.extension.coroutines.observe
import com.loskon.base.extension.view.setClickListener
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
    private val args by navArgs<RepoInfoSheetFragmentArgs>()

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
        binding.tvRepositoryUrl.setClickListener { openUrl(it) }
    }

    private fun openUrl(url: String) {
        if (CustomTab.isAvailableCustomTab(requireContext())) {
            CustomTab.launchCustomTab(requireContext(), url)
        } else {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            requireContext().startActivity(browserIntent)
        }
    }

    private fun installObserver() {
        viewModel.getRepoInfoState.observe(viewLifecycleOwner) { repository ->
            repository?.let { setRepository(it) }
        }
    }

    private fun setRepository(repository: RepositoryModel) {
        with(binding) {
            repository.apply {
                tvRepositoryName.text = name

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