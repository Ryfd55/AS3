package ru.netology.nmedia3.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean,
    val views: String = "25M",
    val video: String? = null,
    val likes: Long = 1999,
    val shares: Long = 999998
)

