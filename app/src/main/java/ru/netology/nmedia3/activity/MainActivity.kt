package ru.netology.nmedia3.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import ru.netology.nmedia3.R
import ru.netology.nmedia3.adapter.PostAdapter
import ru.netology.nmedia3.adapter.PostListener
import ru.netology.nmedia3.databinding.ActivityMainBinding
import ru.netology.nmedia3.dto.Post
import ru.netology.nmedia3.utils.AndroidUtils
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
            }
        )
        activityMainBinding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        activityMainBinding.add.setOnClickListener {
            newPostContract.launch()
        }

//        viewModel.edited.observe(this) {
//            if (it.id == 0L) {
//                return@observe
//            }
//            activityMainBinding.content.requestFocus()
//            activityMainBinding.content.setText(it.content)
//            activityMainBinding.textForEdit.setText(it.content)
//            activityMainBinding.group.visibility = View.VISIBLE
////            activityMainBinding.content.marginBottom = View (R.dimen.half_spacing)
////            android:layout_marginBottom="@dimen/quarter_spacing"
//        }
//
//        activityMainBinding.save.setOnClickListener {
//            with(activityMainBinding.content) {
//                val content = text.toString()
//                if (content.isNullOrBlank()) {
//                    Toast.makeText(
//                        this@MainActivity, R.string.empty_post_error,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@setOnClickListener
//                }
//                viewModel.changeContent(content)
//                viewModel.save()
//
//                activityMainBinding.group.visibility = View.GONE
//                setText("")
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//            }
//        }
//
//        activityMainBinding.cancel.setOnClickListener {
//            with(activityMainBinding.content) {
//                viewModel.clearEdit()
//                activityMainBinding.group.visibility = View.GONE
//                setText("")
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//            }
//        }
     }
}
