package com.loskon.features.repolist.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.loskon.base.extension.coroutines.observe
import com.loskon.base.extension.paging.observe
import com.loskon.base.viewbinding.viewBinding
import com.loskon.base.widget.recyclerview.AddAnimationItemAnimator
import com.loskon.base.widget.snackbar.WarningSnackbar
import com.loskon.features.R
import com.loskon.features.databinding.FragmentRepoListBinding
import com.loskon.features.userlist.presentation.UserListFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepoListFragment : Fragment(R.layout.fragment_repo_list) {

    private val viewModel: RepoListViewModel by viewModel()
    private val binding by viewBinding(FragmentRepoListBinding::bind)

    private val repoListAdapter = RepoListPagingAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()
        setupViewsListener()
        installObservers()
    }

    private fun configureRecyclerView() {
        with(binding.rvRepoList) {
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = AddAnimationItemAnimator()
            adapter = repoListAdapter.withLoadStateFooter(RepoListLoadAdapter())
            setHasFixedSize(true)
        }
    }

    private fun setupViewsListener() {
        binding.refreshLayoutRepoList.setOnRefreshListener {
            viewModel.refreshUsers()
            binding.refreshLayoutRepoList.isRefreshing = false
        }
        repoListAdapter.setOnItemClickListener { user ->
            val action = UserListFragmentDirections.openUserProfileFragment(user.login)
            findNavController().navigate(action)
        }
        binding.bottomBarRepoList.setNavigationOnClickListener {
            val action = UserListFragmentDirections.openSettingsFragment()
            findNavController().navigate(action)
        }
    }

    private fun installObservers() {
        viewModel.getUsers.observe(viewLifecycleOwner) {
           repoListAdapter.submitData(it)
        }
        repoListAdapter.observe(lifecycleScope) {
            when (it) {
                is LoadState.Loading -> {
                    binding.indicatorRepoList.isVisible = true
                }

                is LoadState.Error -> {
                    viewModel.checkInternet()
                }

                else -> {
                    binding.indicatorRepoList.isVisible = false
                    binding.tvNoInternetRepoList.isVisible = false
                }
            }
        }

        viewModel.getUserListAction.observe(viewLifecycleOwner) {
            when (it) {
                RepoListAction.Failure -> {
                    binding.indicatorRepoList.isVisible = false
                    binding.tvNoInternetRepoList.isVisible = false
                    showWarningSnackbar(getString(R.string.error_loading))
                }
                RepoListAction.Refresh -> {
                    repoListAdapter.refresh()
                }
                RepoListAction.ConnectionFailure -> {
                    binding.indicatorRepoList.isVisible = false
                    binding.tvNoInternetRepoList.isVisible = true
                }
                else -> {}
            }
        }
    }

    private fun showWarningSnackbar(message: String) {
        WarningSnackbar().make(binding.root, binding.bottomBarRepoList, message, success = false).show()
    }
}