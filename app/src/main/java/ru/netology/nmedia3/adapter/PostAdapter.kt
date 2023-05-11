package ru.netology.nmedia3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia3.databinding.CardPostBinding
import ru.netology.nmedia3.dto.Post

interface PostListener {
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onLike(post: Post)
    fun onLShare(post: Post)

}

class PostAdapter(
//    private val onLikeClicked: (Post) -> Unit,
//    private val onShareClicked: (Post) -> Unit,
//    private val onRemoveClickedListener: (Post) -> Unit,
    private val listener: PostListener


) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding =
            CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PostViewHolder(
            binding = binding,
//            onLikeClicked, onShareClicked, onRemoveClickedListener)
            listener = this.listener
        )
    }
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}