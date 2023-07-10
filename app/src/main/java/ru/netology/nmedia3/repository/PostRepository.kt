package ru.netology.nmedia3.repository

import ru.netology.nmedia3.dto.Post

interface PostRepository {
    fun getAll(): List<Post>
    fun likeById(post: Post): Post
    fun save(post: Post)
    fun removeById(id: Long)


    fun getAllAsync(callBack: GetAllCallBack)
    interface GetAllCallBack {
        fun onSuccess(post: List<Post>)
        fun onError()
    }

}
