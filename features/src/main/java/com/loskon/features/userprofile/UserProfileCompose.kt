package com.loskon.features.userprofile

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.loskon.base.extension.context.getPrimaryColorKtx
import com.loskon.base.viewbinding.viewBinding
import com.loskon.features.R
import com.loskon.features.databinding.FragmentUserProfileComposeBinding
import com.loskon.features.theme.GitHubTheme
import com.loskon.features.userprofile.presentation.UserProfileState
import com.loskon.features.userprofile.presentation.UserProfileViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserProfileCompose : Fragment(R.layout.fragment_user_profile_compose) {

    private val viewModel by viewModel<UserProfileViewModel>()
    private val binding by viewBinding(FragmentUserProfileComposeBinding::bind)
    private val args by navArgs<UserProfileComposeArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCompose()
    }

    private fun setupCompose() {
        binding.composeContainer.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                GitHubTheme { ComposeProgress(viewModel, args.username, findNavController()) }
            }
        }
    }
}

@Composable
fun ComposeProgress(viewModel: UserProfileViewModel, username: String, navController: NavController) {
    val scope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    val userProfileState by viewModel.getUserProfileState.collectAsStateWithLifecycle()
    val userProfileList by viewModel.getUserProfileList.collectAsStateWithLifecycle()

    fun refresh() = scope.launch {
        refreshing = true
        viewModel.getUser(username)
        refreshing = false
    }

    // TODO Для того чтобы задать такую же высоту для вызова обновления как у SwipeRefreshLayout
    val metrics = LocalContext.current.resources.displayMetrics.density.toInt() + 64
    val pullRefreshState = rememberPullRefreshState(refreshing, ::refresh, refreshThreshold = metrics.dp)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        val (refreshIndicator, column, bar, linearIndicator) = createRefs()

        when (userProfileState) {
            is UserProfileState.Success -> {}
            is UserProfileState.Loading -> {
                LinearProgressIndicator(
                    modifier = Modifier
                        .constrainAs(linearIndicator, constrainBlock = { bottom.linkTo(bar.top) })
                        .fillMaxWidth()
                        .height(2.dp),
                    backgroundColor = Color.Transparent,
                    color = Color(LocalContext.current.getPrimaryColorKtx())
                )
            }
            is UserProfileState.Failure -> {}
        }

        LazyColumn(
            modifier = Modifier
                .constrainAs(column, constrainBlock = {
                    top.linkTo(parent.top)
                    bottom.linkTo(bar.top)
                })
                .fillMaxSize()
        ) {
            items(items = userProfileList.repositories) { Text(text = it.fullName) }
        }

        BottomAppBar(
            modifier = Modifier
                .height(dimensionResource(id = com.loskon.base.R.dimen.bottom_bar_height))
                .constrainAs(bar, constrainBlock = { bottom.linkTo(parent.bottom) })
        ) {
            IconButton(onClick = singleClick { navController.popBackStack() }) {
                Icon(
                    painterResource(R.drawable.baseline_navigate_before_black_24),
                    contentDescription = null,
                    tint = Color(LocalContext.current.getPrimaryColorKtx())
                )
            }
        }

        PullRefreshIndicator(
            refreshing,
            pullRefreshState,
            contentColor = Color(LocalContext.current.getPrimaryColorKtx()),
            modifier = Modifier.constrainAs(refreshIndicator, constrainBlock = {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        )
    }

    viewModel.getUser(username)
}

fun singleClick(onClick: () -> Unit): () -> Unit {
    var latest: Long = 0
    return {
        val now = System.currentTimeMillis()
        if (now - latest >= 600L) {
            onClick()
            latest = now
        }
    }
}