package ru.netology.nmedia3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia3.databinding.ActivityMainBinding
import ru.netology.nmedia3.dto.Post
import ru.netology.nmedia3.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content

                if (post.likedByMe)
                    likes.setImageResource(R.drawable.likes_red) else likes.setImageResource(R.drawable.likes_border)

                likesCount.text = Shortening.shortening(post.likes)
                sharesCount.text = Shortening.shortening(post.shares)
            }
        }
        binding.likes.setOnClickListener {
            viewModel.like()
        }
        binding.shares.setOnClickListener {
            viewModel.share()
        }
    }
}
