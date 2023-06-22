package ru.netology.nmedia3.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean,
    val views: Int,
    val video: String? = null,
    val likes: Int,
    val shares: Int
)

