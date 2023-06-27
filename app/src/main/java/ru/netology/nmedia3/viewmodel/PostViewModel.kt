package ru.netology.nmedia3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia3.db.AppDb
import ru.netology.nmedia3.dto.Post
import ru.netology.nmedia3.repository.PostRepository
import ru.netology.nmedia3.repository.PostRepositorySQLiteImpl

private val empty = Post(
    id = 0,
    author = "",
    published = "",
    content = "",
    likedByMe = false,
    likes = 999999,
    shares = 1199,
    views = 0
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao
    )
    val data: LiveData<List<Post>> = repository.getAll()
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)

    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }
    fun changeContent(content: String) {
        edited.value?.let { post ->
            if (content != post.content) {
                edited.value = post.copy(content = content)
            }
        }
    }

    fun clearEdit() {
        edited.value = empty
    }
}

