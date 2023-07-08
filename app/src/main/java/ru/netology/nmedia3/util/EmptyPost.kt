package ru.netology.nmedia3.util

import ru.netology.nmedia3.dto.Post

object EmptyPost {
    val empty = Post(
        id = 0,
        content = "",
        author = "",
        likes = 0,
        likedByMe = false,
        published = ""
    )
}