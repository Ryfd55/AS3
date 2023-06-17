package ru.netology.nmedia3.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.netology.nmedia3.Shortening
import ru.netology.nmedia3.databinding.FragmentDetailsPostBinding
import ru.netology.nmedia3.utils.LongArg
import ru.netology.nmedia3.viewmodel.PostViewModel

class PostDetailsFragment : Fragment() {

    private val viewModel: PostViewModel by activityViewModels()

    companion object {
        var Bundle.idArg: Long? by LongArg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        val binding = FragmentDetailsPostBinding.inflate(
            layoutInflater,
            container,
            false
        )
        arguments?.let { it ->
            val postId = it.getLong("postId", -1)
//        val postId = arguments?.idArg ?: -1
            viewModel.data.observe(viewLifecycleOwner) { posts ->
                val post = posts.find { it.id == postId } ?: return@observe

                with(binding) {
                    author.text = post.author
                    content.text = post.content
                    published.text = post.published

                    likes.isChecked = post.likedByMe
                    likes.text = post.likes.toString()
                    shares.text = post.shares.toString()
                    views.text = post.views

                    if (!post.video.isNullOrBlank()) {
                        videoPreview.visibility = View.VISIBLE
                        playVideoButton.visibility = View.VISIBLE
                    } else {
                        videoPreview.visibility = View.GONE
                        playVideoButton.visibility = View.GONE
                    }
                    likes.text = Shortening.shortening(post.likes)
                    shares.text = Shortening.shortening(post.shares)

                }
            }
        }
        return binding.root
    }
}
