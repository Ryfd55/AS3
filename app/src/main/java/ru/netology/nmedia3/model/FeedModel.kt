package ru.netology.nmedia3.model

import ru.netology.nmedia3.dto.Post

data class FeedModel(
    val posts: List<Post> = emptyList(),
    val empty: Boolean = false,
)

data class  FeedModelState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val refreshing: Boolean = false,
    val onSuccess: Boolean = false,
    val onFailure: Boolean = false,
)