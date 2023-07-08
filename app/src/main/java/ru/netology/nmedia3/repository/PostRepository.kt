package ru.netology.nmedia3.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia3.dto.Post

interface PostRepository {
    fun getAll(): List<Post>
    fun likeById(id: Long)
    fun save(post: Post)
    fun removeById(id: Long)
}
