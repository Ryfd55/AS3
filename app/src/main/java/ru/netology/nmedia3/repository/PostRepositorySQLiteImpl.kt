package ru.netology.nmedia3.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia3.dto.Post
import ru.netology.nmedia3.dao.PostDao

class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

//    companion object {
//        private const val FILE_NAME = "posts.json"
//    }

//    private val gson = Gson()
//    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
//    private var posts: List<Post> = readPosts()
//        set(value) {
//            field = value
//            sync()
//        }
//    private val data = MutableLiveData(posts)

//    private fun sync() {
//        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use {
//            it.write(gson.toJson(posts))
//        }
//    }

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        posts = if (id == 0L) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
        data.value = posts
    }

//    override fun save(post: Post) {
//        if (post.id == 0L) {
//            posts = listOf(
//                post.copy(
//                    id = (posts.firstOrNull()?.id ?: 0L) + 1,
//                    published = "Now",
//                    author = "Me"
//                )
//            ) + posts
//            data.value = posts
//            return
//        }
//
//        posts = posts.map {
//            if (it.id != post.id)
//                it
//            else
//                it.copy(content = post.content)
//        }
//        data.value = posts
//    }

//    private fun readPosts(): List<Post> {
//        val file = context.filesDir.resolve(FILE_NAME)
//        return if (file.exists()) {
//            context.openFileInput(FILE_NAME).bufferedReader().use {
//                gson.fromJson(it, type)
//            }
//        } else {
//            emptyList()
//        }
//    }


//    override fun likeById(id: Long) {
//        dao.likeById(id)
//        posts = posts.map { post ->
//            if (post.id == id) {
//                post.copy(
//                    likedByMe = !post.likedByMe,
//                    likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
//                )
//            } else {
//                post
//            }
//        }
//        data.value = posts
//    }

    override fun likeById(id: Long) {
        dao.likeById(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    shares = post.shares + 1
                )
            } else {
                post
            }
        }
        data.value = posts
    }



    override fun removeById(id: Long) {
        dao.removeById(id)
        posts = posts.filter {
            it.id != id
        }
        data.value = posts
    }


}