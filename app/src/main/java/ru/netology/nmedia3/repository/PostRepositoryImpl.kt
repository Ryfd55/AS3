package ru.netology.nmedia3.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import ru.netology.nmedia3.dto.Post
import java.io.IOException
import java.util.concurrent.TimeUnit

class PostRepositoryImpl : PostRepository {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    private val typeToken = object : TypeToken<List<Post>>() {}

    companion object {
        private const val BASE_URL = "http://10.0.2.2:9999"
        private val jsonType = "application/json".toMediaType()
    }

    override fun getAllAsync(callBack: PostRepository.RepositoryCallBack<List<Post>>) {
        val request: Request = Request.Builder()
            .url("${BASE_URL}/api/slow/posts")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val body = response.body?.string() ?: throw RuntimeException("body is null")
                        callBack.onSuccess(gson.fromJson(body, typeToken.type))
                    } catch (e: Exception) {
                        callBack.onError()
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    callBack.onError()
                }
            })
    }

    override fun likeByIdAsync(post: Post, callBack: PostRepository.RepositoryCallBack<Post>) {
        val requestOutput: Request = if (!post.likedByMe) {
            Request.Builder()
                .post(gson.toJson(post).toRequestBody(jsonType))
                .url("${BASE_URL}/api/slow/posts/${post.id}/likes")
                .build()
        } else {
            Request.Builder()
                .delete(gson.toJson(post).toRequestBody(jsonType))
                .url("${BASE_URL}/api/slow/posts/${post.id}/likes")
                .build()
        }
        client.newCall(requestOutput)
            .enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string() ?: throw RuntimeException("body is null")
                    try {
                        callBack.onSuccess(gson.fromJson(body, Post::class.java))
                    } catch (e: Exception) {
                        callBack.onError()
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    callBack.onError()
                }
            })
    }

    override fun saveAsync(post: Post, callBack: PostRepository.RepositoryCallBack<Unit>) {
        val request: Request = Request.Builder()
            .post(gson.toJson(post).toRequestBody(jsonType))
            .url("${BASE_URL}/api/slow/posts")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    try {
                        callBack.onSuccess(Unit)
                    } catch (e: Exception) {
                        callBack.onError()
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    callBack.onError()
                }
            })
    }

    override fun removeByIdAsync(id: Long, callBack: PostRepository.RepositoryCallBack<Unit>) {
        val request: Request = Request.Builder()
            .delete()
            .url("${BASE_URL}/api/slow/posts/$id")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    try {
                        callBack.onSuccess(Unit)
                    } catch (e: Exception) {
                        callBack.onError()
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    callBack.onError()
                }
            })
    }
}
