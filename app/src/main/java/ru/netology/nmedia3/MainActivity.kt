package ru.netology.nmedia3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.marginBottom
import ru.netology.nmedia3.adapter.PostAdapter
import ru.netology.nmedia3.adapter.PostListener
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
                    viewModel.shareById(post.id)
                }
            }
        )
        viewModel.edited.observe(this) {
            if (it.id == 0L) {
                return@observe
            }
            activityMainBinding.content.requestFocus()
            activityMainBinding.content.setText(it.content)
            activityMainBinding.textForEdit.setText(it.content)
            activityMainBinding.group.visibility = View.VISIBLE
//            activityMainBinding.content.marginBottom = View (R.dimen.half_spacing)
//            android:layout_marginBottom="@dimen/quarter_spacing"
        }

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

                activityMainBinding.group.visibility = View.GONE
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }

        activityMainBinding.cancel.setOnClickListener {
            with(activityMainBinding.content) {
                viewModel.clearEdit()
                activityMainBinding.group.visibility = View.GONE
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
