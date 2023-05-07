package ru.netology.nmedia3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia3.databinding.CardPostBinding
import ru.netology.nmedia3.dto.Post

class PostAdapter(
    private val onLikeClicked: (Post) -> Unit,
    private val onShareClicked: (Post) -> Unit
) : RecyclerView.Adapter<PostViewHolder>() {
    var data: List<Post> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding =
            CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PostViewHolder(binding, onLikeClicked, onShareClicked)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(data[position])
    }
}