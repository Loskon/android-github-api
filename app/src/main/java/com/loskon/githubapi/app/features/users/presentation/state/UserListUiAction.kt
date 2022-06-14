package com.loskon.githubapi.app.features.users.presentation.state

sealed class UserListUiAction {
    class ShowLoadingIndicator(val loading: Boolean) : UserListUiAction()
    class ShowError(val type: ErrorType, val message: String? = null) : UserListUiAction()
}