package ru.netology.nmedia3.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia3.dto.Post

interface PostRepository {

    fun getData(): LiveData<Post>
    fun like()
    fun share()
}