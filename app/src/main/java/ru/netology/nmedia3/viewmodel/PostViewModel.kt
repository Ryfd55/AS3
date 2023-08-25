package ru.netology.nmedia3.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.netology.nmedia3.R
import ru.netology.nmedia3.db.AppDb
import ru.netology.nmedia3.dto.Post
import ru.netology.nmedia3.model.FeedModel
import ru.netology.nmedia3.model.FeedModelState
import ru.netology.nmedia3.util.SingleLiveEvent
import ru.netology.nmedia3.repository.PostRepository
import ru.netology.nmedia3.repository.PostRepositoryImpl
import ru.netology.nmedia3.util.EmptyPost.empty

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryImpl(
        AppDb.getInstance(application).postDao()
    )
    private val _state = MutableLiveData(FeedModelState())
    val state: LiveData<FeedModelState>
        get() = _state
    val data: LiveData<FeedModel> = repository.data.map {
        FeedModel(posts = it, empty = it.isEmpty())
    }

    val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated
    private val _requestCode = MutableLiveData<Int>()
    val requestCode: LiveData<Int> = _requestCode

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            _state.value = FeedModelState(loading = true)
            try {
                repository.getAll()
                _state.value = FeedModelState()
            } catch (e: Exception) {
                _state.value = FeedModelState(error = true)
            }
        }
    }

    fun save() {
        viewModelScope.launch {
            edited.value?.let {
                repository.save(it)
                _postCreated.value = Unit
            }
        }
        edited.value = empty
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(post: Post) {
        viewModelScope.launch {
            repository.likeById(post)
        }
    }

    fun disLikeById(post: Post) {
        viewModelScope.launch {
            repository.disLikeById(post)
        }
    }

    fun removeById(id: Long) {
        viewModelScope.launch {
        repository.removeByIdAsync(id)
            }

    }

    fun edit(post: Post) {
        edited.value = post
    }
}
