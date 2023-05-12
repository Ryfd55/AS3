package ru.netology.nmedia3.adapter

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nmedia3.R
import ru.netology.nmedia3.Shortening
import ru.netology.nmedia3.databinding.CardPostBinding
import ru.netology.nmedia3.dto.Post


class PostViewHolder(
    private val binding: CardPostBinding,
//    private val onLikeClicked: (Post) -> Unit,
//    private val onShareClicked: (Post) -> Unit,
//    private val onRemoveClickedListener: (Post) -> Unit


    private val listener: PostListener


) : ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content

            likes.setImageResource(
                if (post.likedByMe) R.drawable.likes_red else R.drawable.likes_border
            )
            likes.setOnClickListener {
                listener.onLike(post)
            }
            shares.setOnClickListener {
                listener.onLShare(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_options)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
//                                onRemoveClickedListener(post)
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


            likesCount.text = Shortening.shortening(post.likes)
            sharesCount.text = Shortening.shortening(post.shares)
        }
    }

}