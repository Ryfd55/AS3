package ru.netology.nmedia3.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia3.R
import ru.netology.nmedia3.adapter.OnInteractionListener
import ru.netology.nmedia3.adapter.PostsAdapter
import ru.netology.nmedia3.databinding.FragmentFeedBinding
import ru.netology.nmedia3.dto.Post
import ru.netology.nmedia3.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater,
            container,
            false
        )

        val adapter = PostsAdapter(object : OnInteractionListener {

            override fun onLike(post: Post) {
                viewModel.likeById(post)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                val text = post.content
                val bundle = Bundle()
                bundle.putString("editedText", text)
                findNavController().navigate(R.id.action_feedFragment_to_editPostFragment, bundle)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }
        })
        binding.list.adapter = adapter
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.progress.isVisible = state.loading
            binding.swiperefresh.isRefreshing = state.refreshing
            if (state.error){
                Snackbar.make(binding.root,R.string.error_loading, Snackbar.LENGTH_LONG)
                    .setAction(R.string.retry_loading){
                        viewModel.loadPosts()
                    }
                    .show()
            }
        }
        viewModel.data.observe(viewLifecycleOwner){
            binding.emptyText.isVisible = it.empty
            adapter.submitList(it.posts)
        }

        viewModel.requestCode.observe(viewLifecycleOwner) { requestCode ->
            errorToast(requestCode)
        }

        binding.retryButton.setOnClickListener {
            viewModel.loadPosts()
        }

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        binding.swiperefresh.setOnRefreshListener {
            binding.swiperefresh.isRefreshing = false
            viewModel.refresh()
        }

        return binding.root
    }

    private fun errorToast(requestCode: Int) {
        if (requestCode.toString().startsWith("1")) {
            Toast.makeText(context, "Информационный код ответа", Toast.LENGTH_LONG).show()
        }
        if (requestCode.toString().startsWith("3")) {
            Toast.makeText(context, "Перенаправление", Toast.LENGTH_LONG).show()
        }
        if (requestCode.toString().startsWith("4")) {
            Toast.makeText(context, "Ошибка клиента", Toast.LENGTH_LONG).show()
        }
        if (requestCode.toString().startsWith("5")) {
            Toast.makeText(context, "Ошибка сервера", Toast.LENGTH_LONG).show()
        }
    }
}
