package ru.netology.nmedia3.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia3.Entity.PostEntity
import ru.netology.nmedia3.api.PostsApi
import ru.netology.nmedia3.dao.PostDao
import ru.netology.nmedia3.dto.Post

class PostRepositoryImpl(
    private val dao: PostDao
) : PostRepository {

    override val data: LiveData<List<Post>> = dao.getAll().map {
        it.map(PostEntity::toDto)
    }

    override suspend fun getAll() {
        val response = PostsApi.retrofitService.getAll()
        if (!response.isSuccessful) {
            throw RuntimeException(response.message())
        }
        val posts = response.body() ?: throw RuntimeException("body is empty")
        dao.insert(posts.map(PostEntity::fromDto))

    }

    override suspend fun likeById(post: Post): Post {
        TODO("Not yet implemented")
    }


    override suspend fun disLikeById(post: Post): Post {
        TODO("Not yet implemented")
    }

    override suspend fun save(post: Post): Post {
        TODO("Not yet implemented")
    }

    override suspend fun removeById(id: Long) {
        TODO("Not yet implemented")
    }
}


