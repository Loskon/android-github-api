package com.loskon.features.repolist.presentation

sealed class RepoListAction {
    object Failure : RepoListAction()
    object Refresh : RepoListAction()
    object ConnectionFailure : RepoListAction()
}