package ru.netology.nmedia3.viewmodel

import android.app.Application
import androidx.lifecycle.*
import ru.netology.nmedia3.dto.Post
import ru.netology.nmedia3.model.FeedModel
import ru.netology.nmedia3.util.SingleLiveEvent
import ru.netology.nmedia3.repository.PostRepository
import ru.netology.nmedia3.repository.PostRepositoryImpl
import ru.netology.nmedia3.util.EmptyPost.empty

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
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
        _data.postValue(FeedModel(loading = true))
        repository.getAllAsync(object : PostRepository.RepositoryCallBack<List<Post>> {
            override fun onSuccess(value: List<Post>) {
                _data.postValue(FeedModel(posts = value, empty = value.isEmpty()))
            }

            override fun onError(e: Exception, requestCode: Int) {
                _data.postValue(FeedModel(error = true))
                _requestCode.value = requestCode
            }
        })
    }

    fun save() {
        edited.value?.let { editedPost ->
            val newStatePosts = _data.value?.posts.orEmpty()
                .map { if (it.id == editedPost.id) editedPost else it }
            repository.saveAsync(editedPost, object : PostRepository.RepositoryCallBack<Post> {
                override fun onSuccess(value: Post) {
                    _data.postValue(FeedModel(posts = newStatePosts, onSuccess = true, onFailure = false))
                    _postCreated.postValue(Unit)
                    loadPosts()
                }

                override fun onError(e: Exception, requestCode: Int) {
                    _data.postValue(FeedModel(error = true))
                    _requestCode.value = requestCode
                }
            })
        }

//    fun save() {
//        edited.value?.let { editedPost ->
//            val newStatePosts = _data.value?.posts.orEmpty()
//                .map { if (it.id == editedPost.id) editedPost else it }
//            repository.saveAsync(editedPost, object : PostRepository.RepositoryCallBack<Post> {
//                override fun onSuccess(value: Post) {
//                    _postCreated.postValue(Unit)
//                    _data.postValue(FeedModel(posts = newStatePosts, onSuccess = true, onFailure = false))
//                }
//
//                                override fun onError(e: Exception, requestCode: Int) {
//                    _data.postValue(FeedModel(error = true))
//                    _requestCode.value = requestCode
//                }
//            })
//        }
        edited.value = empty
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Long) {
        val post = data.value?.posts?.find { it.id == id } ?: empty
        repository.likeByIdAsync(post, object : PostRepository.RepositoryCallBack<Post> {
            override fun onSuccess(value: Post) {
                _data.postValue(
                    _data.value?.copy(
                        posts = _data.value?.posts.orEmpty()
                            .map { if (it.id == id) value else it })
                )
            }

            override fun onError(e: Exception, requestCode: Int) {
                _data.postValue(FeedModel(error = true))
                _requestCode.value = requestCode
            }
        })
    }

    fun disLikeById(id: Long) {
        val post = data.value?.posts?.find { it.id == id } ?: empty
        repository.disLikeByIdAsync(post, object : PostRepository.RepositoryCallBack<Post> {
            override fun onSuccess(value: Post) {
                _data.postValue(
                    _data.value?.copy(
                        posts = _data.value?.posts.orEmpty()
                            .map { if (it.id == id) value else it })
                )
            }

            override fun onError(e: Exception, requestCode: Int) {
                _data.postValue(FeedModel(error = true))
                _requestCode.value = requestCode
            }
        })
    }

    fun removeById(id: Long) {
        repository.removeByIdAsync(id, object : PostRepository.RepositoryCallBack<Unit> {
            override fun onSuccess(value: Unit) {
                _data.postValue(
                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
                        .filter { it.id != id })
                )
            }

            override fun onError(e: Exception, requestCode: Int) {
                _data.postValue(FeedModel(error = true))
                _requestCode.value = requestCode
            }
        })
    }

    fun edit(post: Post) {
        edited.value = post
    }
}
