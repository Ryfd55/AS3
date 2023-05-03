package ru.netology.nmedia3.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    var likedByMe: Boolean,
    var likes: Long = 2000,
    var shares: Long = 999998
)

