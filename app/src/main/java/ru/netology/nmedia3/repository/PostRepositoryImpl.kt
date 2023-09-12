package ru.netology.nmedia3.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.netology.nmedia3.Entity.PostEntity
import ru.netology.nmedia3.Entity.toDto
import ru.netology.nmedia3.Entity.toEntity
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

    private val newerPostsId = mutableListOf<Long>()
    override val data: Flow<List<Post>> = dao.getAll()
        .map {
            it.filter { postEntity ->
                !postEntity.hidden
            }
        }
        .map(List<PostEntity>::toDto)
        .flowOn(Dispatchers.Default)

    override fun getNewerCount(id: Long): Flow<Int> = flow {
        while (true) {
            delay(10_000L)
            val response = PostsApi.service.getNewer(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val posts = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(posts.toEntity(hidden = true))
            posts.forEach {
                newerPostsId.add(it.id)
            }
            emit(posts.size)
        }
    }

    override suspend fun getAll() {
        try {
            val response = PostsApi.service.getAll()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            for (i in body) {
                i.hidden = true
            }
            dao.insert(body.toEntity())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
            dao.likeById(post.id)
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
            dao.removeById(id)
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun updateShownStatus() {
        dao.updateShownStatus()
    }
}


