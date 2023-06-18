package ru.netology.nmedia3.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia3.R
import ru.netology.nmedia3.adapter.PostListener
import ru.netology.nmedia3.adapter.PostViewHolder
import ru.netology.nmedia3.databinding.FragmentDetailsPostBinding
import ru.netology.nmedia3.dto.Post
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

            viewModel.data.observe(viewLifecycleOwner) { list ->
                list.find { it.id == postId }?.let { post ->

                    PostViewHolder(
                        binding.postDetailsFragment,
                        object : PostListener {
                            override fun onRemove(post: Post) {
                                viewModel.removeById(post.id)
                                findNavController().navigate(
                                    R.id.action_postDetailsFragment_to_feedFragment
                                )
                            }

                            override fun onEdit(post: Post) {
                                viewModel.edit(post)
                                findNavController().navigate(
                                    R.id.action_postDetailsFragment_to_newPostFragment,
                                    bundleOf("textArg" to post.content)
                                )
                            }

                            override fun onLike(post: Post) {
                                viewModel.likeById(post.id)
                            }

                            override fun onShare(post: Post) {
                                viewModel.shareById(post.id)
                                val intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, post.content)
                                    type = "text/plain"
                                }

                                val startIntent =
                                    Intent.createChooser(
                                        intent,
                                        getString(R.string.chooser_share_post)
                                    )
                                startActivity(startIntent)
                            }

                            override fun onVideo(post: Post) {
                                val intent =
                                    Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                                startActivity(intent)
                            }

                            override fun onDetailsClicked(post: Post) {}
                        }
                    ).bind(post)
                }
            }
        }
        return binding.root
    }
}
