package com.loskon.features.repoinfo.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs
import com.loskon.base.customtab.CustomTab
import com.loskon.base.datetime.toFormatString
import com.loskon.base.extension.coroutines.observe
import com.loskon.base.extension.view.setDebounceClickListener
import com.loskon.base.extension.view.setGoneVisibleKtx
import com.loskon.base.extension.view.setTextClickListener
import com.loskon.base.presentation.sheetfragment.BaseSheetFragment
import com.loskon.base.viewbinding.viewBinding
import com.loskon.features.R
import com.loskon.features.databinding.SheetRepoInfoBinding
import com.loskon.features.model.RepoModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepoInfoSheetFragment : BaseSheetFragment(R.layout.sheet_repo_info) {

    private val viewModel by viewModel<RepoInfoViewModel>()
    private val binding by viewBinding(SheetRepoInfoBinding::bind)
    private val args by navArgs<RepoInfoSheetFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) viewModel.getCachedRepo(args.repo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewsListener()
        installObserver()
    }

    private fun setupViewsListener() {
        binding.btnSheetRepoInfoClose.setDebounceClickListener { dismiss() }
        binding.tvRepoUrl.setTextClickListener { openUrl(it) }
    }

    private fun openUrl(url: String) {
        if (CustomTab.isAvailableCustomTab(requireContext())) {
            CustomTab.launchCustomTab(requireContext(), url)
        } else {
            val browserIntent = Intent(Intent.ACTION_VIEW, url.toUri())
            requireContext().startActivity(browserIntent)
        }
    }

    private fun installObserver() {
        viewModel.getRepoInfoState.observe(viewLifecycleOwner) { repos ->
            repos?.let { getCachedRepo(it) }
        }
    }

    private fun getCachedRepo(repo: RepoModel) {
        with(binding) {
            repo.apply {
                tvRepoName.text = name

                if (description.isNotEmpty()) {
                    tvRepoDescreption.text = description
                } else {
                    tvRepoDescreptionHeader.setGoneVisibleKtx(false)
                    tvRepoDescreption.setGoneVisibleKtx(false)
                }

                if (language.isNotEmpty()) {
                    tvRepoLanguage.text = language
                } else {
                    tvRepoLanguageHeader.setGoneVisibleKtx(false)
                    tvRepoLanguage.setGoneVisibleKtx(false)
                }

                tvRepoSize.text = size.toString()
                tvRepoCreatedDate.text = createdAt.toFormatString()
                tvRepoUpdatedDate.text = updatedAt.toFormatString()
                tvRepoPushedDate.text = pushedAt.toFormatString()
                tvRepoUrl.text = htmlUrl
            }
        }
    }
}