package ru.netology.nmedia3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia3.adapter.PostAdapter
import ru.netology.nmedia3.databinding.ActivityMainBinding
import ru.netology.nmedia3.databinding.CardPostBinding
import ru.netology.nmedia3.dto.Post
import ru.netology.nmedia3.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(
            onLikeClicked = { viewModel.likeById(it.id) },
            onShareClicked = { viewModel.shareById(it.id) }
        )
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        activityMainBinding.list.adapter = adapter

    }
}
