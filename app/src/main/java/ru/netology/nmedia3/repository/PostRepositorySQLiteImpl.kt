package ru.netology.nmedia3.repository

import androidx.lifecycle.map
import ru.netology.nmedia3.Entity.PostEntity
import ru.netology.nmedia3.dto.Post
import ru.netology.nmedia3.dao.PostDao

class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {

    override fun getAll() = dao.getAll().map { list ->
        list.map { it.toDto() }
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
    }
    override fun likeById(id: Long) {
        dao.likeById(id)

    }

    override fun shareById(id: Long) {
        dao.shareById(id)
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }
}

