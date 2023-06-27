package ru.netology.nmedia3.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia3.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean,
    val views: Int,
    val video: String? = null,
    val likes: Int,
    val shares: Int
){
    fun toDto() = Post(id, author, published, content, likedByMe, views, video, likes, shares)

    companion object{
        fun fromDto(post: Post) = with(post)
        { PostEntity(id, author, published, content, likedByMe, views, video, likes, shares)}
    }
}