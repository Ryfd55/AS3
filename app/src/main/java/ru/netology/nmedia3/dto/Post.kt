package ru.netology.nmedia3.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean,
    val likes: Long = 2000,
    val shares: Long = 999998
)

