package ru.netology.nmedia3.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.netology.nmedia3.api.PostsApi
import ru.netology.nmedia3.dto.Post

class PostRepositoryImpl : PostRepository {

    override fun getAllAsync(callBack: PostRepository.RepositoryCallBack<List<Post>>) {
        PostsApi.retrofitService.getAll().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (!response.isSuccessful) {
                    callBack.onError(RuntimeException(response.errorBody()?.string()), response.code())
                    return
                }
                callBack.onSuccess(response.body() ?: throw RuntimeException("body is empty"))
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                callBack.onError(Exception(t),404)
            }
        })
    }

    override fun removeByIdAsync(id: Long, callBack: PostRepository.RepositoryCallBack<Unit>) {
        PostsApi.retrofitService.removeById(id).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (!response.isSuccessful) {
                    callBack.onError(RuntimeException(response.errorBody()?.string()), response.code())
                    return
                }
                callBack.onSuccess(Unit)
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                callBack.onError(Exception(t),404)
            }
        })
    }

    override fun saveAsync(post: Post, callBack: PostRepository.RepositoryCallBack<Post>) {
        PostsApi.retrofitService.save(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    callBack.onError(RuntimeException(response.errorBody()?.string()), response.code())
                    return
                }
                callBack.onSuccess(response.body() ?: throw RuntimeException("body is empty"))
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callBack.onError(Exception(t),404)
            }
        })
    }

    override fun likeByIdAsync(post: Post, callBack: PostRepository.RepositoryCallBack<Post>) {

        PostsApi.retrofitService.likeById(post.id)
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (!response.isSuccessful) {
                        callBack.onError(RuntimeException(response.errorBody()?.string()), response.code())
                        return
                    }
                    callBack.onSuccess(response.body() ?: throw RuntimeException("body is empty"))
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callBack.onError(Exception(t),404)
                }
            })
    }

    override fun disLikeByIdAsync(post: Post, callBack: PostRepository.RepositoryCallBack<Post>) {
        PostsApi.retrofitService.dislikeById(post.id)
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (!response.isSuccessful) {
                        callBack.onError(RuntimeException(response.errorBody()?.string()), response.code())
                        return
                    }
                    callBack.onSuccess(response.body() ?: throw RuntimeException("body is empty"))
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callBack.onError(Exception(t),404)
                }
            })
    }
}

