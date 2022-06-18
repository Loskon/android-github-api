package com.loskon.githubapi.app.features.userprofile.presentation.state

sealed class UserProfileAction {
    class ShowLoadingIndicator(val loading: Boolean) : UserProfileAction()
}