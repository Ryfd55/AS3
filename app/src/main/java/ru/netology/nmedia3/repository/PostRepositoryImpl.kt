package ru.netology.nmedia3.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia3.Entity.PostEntity
import ru.netology.nmedia3.api.PostsApi
import ru.netology.nmedia3.appError.ApiError
import ru.netology.nmedia3.appError.NetworkError
import ru.netology.nmedia3.appError.UnknownError
import ru.netology.nmedia3.dao.PostDao
import ru.netology.nmedia3.dto.Post
import java.io.IOException

class PostRepositoryImpl(
    private val dao: PostDao
) : PostRepository {

    override val data: LiveData<List<Post>> = dao.getAll().map {
        it.map(PostEntity::toDto)
    }

    override suspend fun getAll() {
        val response = PostsApi.service.getAll()
        if (!response.isSuccessful) {
            throw RuntimeException(response.message())
        }
        val posts = response.body() ?: throw RuntimeException("body is empty")
        dao.insert(posts.map(PostEntity::fromDto))

    }

    override suspend fun likeById(post: Post) {
        dao.likeById(post.id)
        try {
            val response = if (post.likedByMe) {
                PostsApi.service.dislikeById(post.id)
            } else {
                PostsApi.service.likeById(post.id)
            }
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(PostEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun save(post: Post) {
        try {
            val response = PostsApi.service.save(post)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(PostEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun removeById(id: Long) {
        dao.removeById(id)
        try {
            val response = PostsApi.service.removeById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }
}


