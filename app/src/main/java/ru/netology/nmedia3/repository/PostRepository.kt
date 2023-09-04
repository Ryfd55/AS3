package ru.netology.nmedia3.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia3.dto.Post

interface PostRepository {
    val data: Flow<List<Post>>
    fun getNewerCount(id:Long):Flow<Int>
    suspend fun getAll()
    suspend fun likeById(post: Post)
    suspend fun save(post: Post)
    suspend fun removeById(id: Long)
    suspend fun updateShownStatus()

}
