package ru.netology.nmedia3.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    var likedByMe: Boolean = true,
    var likes: Long = 999,
    var sharesCount: Long = 1100
)

