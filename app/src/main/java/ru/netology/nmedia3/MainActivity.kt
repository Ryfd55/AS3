package ru.netology.nmedia3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia3.adapter.PostAdapter
import ru.netology.nmedia3.databinding.ActivityMainBinding
import ru.netology.nmedia3.databinding.CardPostBinding
import ru.netology.nmedia3.dto.Post
import ru.netology.nmedia3.utils.AndroidUtils
import ru.netology.nmedia3.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(
            onLikeClicked = { viewModel.likeById(it.id) },
            onShareClicked = { viewModel.shareById(it.id) },
            onRemoveClickedListener = {
                viewModel.removeById(it.id)
            }
        )
        activityMainBinding.save.setOnClickListener {
            with(activityMainBinding.content) {
                val content = text.toString()
                if (content.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity, R.string.empty_post_error,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(content)
                viewModel.save()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        activityMainBinding.list.adapter = adapter

    }
}
