package ru.netology.nmedia3.adapter

import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nmedia3.R
import ru.netology.nmedia3.Shortening
import ru.netology.nmedia3.databinding.CardPostBinding
import ru.netology.nmedia3.dto.Post

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostListener
) : ViewHolder(binding.root) {

    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content

            likes.isChecked = post.likedByMe
            likes.text = post.likes.toString()
            shares.text = post.shares.toString()
            views.text = post.views

            likes.setOnClickListener {
                listener.onLike(post)
            }
            if (!post.video.isNullOrBlank()) {
                videoPreview.visibility = View.VISIBLE
                playVideoButton.visibility = View.VISIBLE
            } else {
                videoPreview.visibility = View.GONE
                playVideoButton.visibility = View.GONE
            }
            videoContainer.setOnClickListener {
                listener.onVideo(post)
            }
            shares.setOnClickListener {
                listener.onShare(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_options)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }

                            R.id.edit -> {
                                listener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                        false
                    }
                }.show()
            }
            root.setOnClickListener {
                listener.onDetailsClicked(post)
            }
            likes.text = Shortening.shortening(post.likes)
            shares.text = Shortening.shortening(post.shares)
        }
    }
}