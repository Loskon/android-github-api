package com.loskon.githubapi.app.features.userlist.presentation.state

sealed class UserListAction {
    class ShowLoadingIndicator(val loading: Boolean) : UserListAction()
    class ShowError(val type: ErrorType, val message: String? = null) : UserListAction()
}