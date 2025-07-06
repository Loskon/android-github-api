package com.loskon.features.userprofile.presentation

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.navArgs
import com.loskon.base.R
import com.loskon.base.viewbinding.viewBinding
import com.loskon.features.databinding.FragmentComposeUserProfileBinding
import com.loskon.features.model.RepoModel
import com.loskon.features.theme.GitHubTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserProfileComposeFragment : Fragment(com.loskon.features.R.layout.fragment_compose_user_profile) {

    private val viewModel: UserProfileViewModel by viewModel()
    private val binding by viewBinding(FragmentComposeUserProfileBinding::bind)
    private val args by navArgs<UserProfileComposeFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) viewModel.getUser(args.username)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeContainer.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent { GitHubTheme { UserProfileScreen(viewModel, args.username) } }
        }
    }
}

@Composable
fun UserProfileScreen(viewModel: UserProfileViewModel, username: String) {
    val userProfileState by viewModel.userProfileStateFlow.collectAsStateWithLifecycle()
    val isRefreshing by remember { mutableStateOf(false) }

    var repoList: List<RepoModel> = emptyList()

    when (userProfileState) {
        is UserProfileState.Loading -> {

        }

        is UserProfileState.Success -> {
            repoList = (userProfileState as UserProfileState.Success).user.repos
        }

        is UserProfileState.Failure -> {

        }

        is UserProfileState.ConnectionFailure -> {

        }
    }

    OrderScreen(viewModel, username, repoList)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderScreen(viewModel: UserProfileViewModel, username: String, repoList: List<RepoModel>) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = { viewModel.getUser(username) },
        refreshThreshold = 64.dp
    )

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        val (refreshIndicator, column, bar) = createRefs()

        PullRefreshIndicator(
            refreshing = false,
            state = pullRefreshState,
            backgroundColor = colorResource(R.color.progress_background),
            contentColor = colorResource(R.color.primary_app_color),
            modifier = Modifier
                .constrainAs(refreshIndicator) {
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top)
                }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(column) {
                    top.linkTo(parent.top)
                    bottom.linkTo(bar.top)
                }
        ) {
            items(items = repoList) { Text(text = it.name) }
        }

        BottomAppBar(
            backgroundColor = colorResource(R.color.bottom_bar_background),
            modifier = Modifier
                .height(dimensionResource(R.dimen.bottom_bar_height))
                .constrainAs(bar) {
                    bottom.linkTo(parent.bottom)
                }
        ) {
            IconButton(
                modifier = Modifier.padding(12.dp).size(12.dp),
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_navigate_before_24),
                    tint = colorResource(R.color.primary_app_color),
                    contentDescription = null
                )
            }
        }
    }
}