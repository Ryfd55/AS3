package ru.netology.nmedia3.repository

import ru.netology.nmedia3.dto.Post

interface PostRepository {

    fun getAllAsync(callBack: RepositoryCallBack<List<Post>>)
    fun likeByIdAsync(post: Post, callBack: RepositoryCallBack<Post>)
    fun saveAsync(post: Post, callBack: RepositoryCallBack<Unit>)
    fun removeByIdAsync(id: Long, callBack: RepositoryCallBack<Unit>)

    interface RepositoryCallBack <T>{
        fun onSuccess(value: T)
        fun onError()
    }
}
