package ru.netology.nmedia3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia3.dto.Post
import ru.netology.nmedia3.repository.PostRepository
import ru.netology.nmedia3.repository.PostRepositoryInMemory

private val empty = Post(
    id = 0,
    author = "",
    published = "",
    content = "",
    likedByMe = false
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemory()
    val data: LiveData<List<Post>> = repository.getData()
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)

    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }

        fun edit(post: Post) {
            edited.value = post
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
}