package ru.netology.nmedia3.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia3.R
import ru.netology.nmedia3.adapter.PostAdapter
import ru.netology.nmedia3.adapter.PostListener
import ru.netology.nmedia3.databinding.ActivityMainBinding
import ru.netology.nmedia3.dto.Post
import ru.netology.nmedia3.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)


        val viewModel: PostViewModel by viewModels()
        val newPostContract = registerForActivityResult(NewPostActivity.Contract) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }
        val adapter = PostAdapter(
            object : PostListener {
                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onEdit(post: Post) {
                    viewModel.edit(post)
                    newPostContract.launch(post.content)
                }

                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onLShare(post: Post) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }
                    val startIntent =
                        Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    startActivity(startIntent)
//                    viewModel.shareById(post.id)
                }

                override fun onVideo(post: Post) {
                    val intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                }

            }
        )
        activityMainBinding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        activityMainBinding.add.setOnClickListener {
            newPostContract.launch("")
        }
    }
}
