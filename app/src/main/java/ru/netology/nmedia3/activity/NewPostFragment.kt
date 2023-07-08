package ru.netology.nmedia3.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia3.databinding.FragmentNewPostBinding
import ru.netology.nmedia3.util.AndroidUtils
import ru.netology.nmedia3.util.StringArg
import ru.netology.nmedia3.viewmodel.PostViewModel

class NewPostFragment : Fragment() {

    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: PostViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )

        arguments?.textArg
            ?.let(binding.content::setText)

        binding.ok.setOnClickListener {
            viewModel.changeContent(binding.content.text.toString())
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())
        }
        viewModel.postCreated.observe(viewLifecycleOwner) {
            viewModel.loadPosts()
            findNavController().navigateUp()
        }
        return binding.root
    }
}