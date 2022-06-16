package com.loskon.githubapi.app.features.repositoryinfo

import android.os.Bundle
import android.view.View
import com.loskon.githubapi.R
import com.loskon.githubapi.base.presentation.BaseBottomSheetDialogFragment
import com.loskon.githubapi.databinding.Shhet2Binding
import com.loskon.githubapi.viewbinding.viewBinding

class RepositoryInfoBottomSheetFragment: BaseBottomSheetDialogFragment(R.layout.shhet_2) {

    private val binding by viewBinding(Shhet2Binding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView.text = "HIIIIIIIIIIII"
    }
}