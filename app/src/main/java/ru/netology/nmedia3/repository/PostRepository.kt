package ru.netology.nmedia3.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia3.dto.Post

interface PostRepository {

    fun getData(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
}