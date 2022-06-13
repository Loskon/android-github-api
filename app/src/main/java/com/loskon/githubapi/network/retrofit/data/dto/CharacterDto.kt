package com.loskon.githubapi.network.retrofit.data.dto

import com.loskon.hpapiclient.network.retrofit.domain.model.CharacterModel

data class CharacterDto(
    val id: Int? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val fullName: String? = null,
    val title: String? = null,
    val family: String? = null,
    val image: String? = null,
    val imageUrl: String? = null
)

fun CharacterDto.toCharacterModel(): CharacterModel {
    return CharacterModel(
        id = id,
        fullName = fullName,
        title = title,
        family = family,
        imageUrl = imageUrl
    )
}