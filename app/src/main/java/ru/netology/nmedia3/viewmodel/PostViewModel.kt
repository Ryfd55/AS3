package ru.netology.nmedia3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia3.dto.Post
import ru.netology.nmedia3.repository.PostRepository
import ru.netology.nmedia3.repository.PostRepositoryInMemory

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemory()
    val data: LiveData<Post> = repository.getData()
    fun like() = repository.like()
    fun share() = repository.share()
}