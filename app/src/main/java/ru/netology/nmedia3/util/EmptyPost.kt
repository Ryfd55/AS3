package ru.netology.nmedia3.util

import ru.netology.nmedia3.dto.Post

object EmptyPost {
    val empty = Post(
        id = 0,
        author = "",
        authorAvatar = "",
        content = "",
        published = "",
        likedByMe = false,
        likes = 0,
        hidden = false
    )
}