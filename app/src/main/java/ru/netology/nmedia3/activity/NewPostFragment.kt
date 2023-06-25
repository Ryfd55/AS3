package ru.netology.nmedia3.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia3.R
import ru.netology.nmedia3.databinding.FragmentNewPostBinding
import ru.netology.nmedia3.utils.AndroidUtils
import ru.netology.nmedia3.utils.TextArg.draftText
import ru.netology.nmedia3.utils.TextArg
import ru.netology.nmedia3.viewmodel.PostViewModel
import androidx.activity.OnBackPressedCallback

class NewPostFragment : Fragment() {
    companion object {
        var Bundle.textArg: String? by TextArg
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(
            inflater, container, false
        )
        val viewModel: PostViewModel by activityViewModels()

        arguments?.let {
            val text = it.textArg
            binding.content.setText(text)
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                draftText = binding.content.text.toString()
                findNavController().navigate(
                    R.id.action_newPostFragment_to_feedFragment,
                    bundleOf("textArg" to draftText)
                )
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.ok.setOnClickListener {
            val text = binding.content.text.toString()
            if (text.isNotBlank()) {
                viewModel.changeContent(text)
                viewModel.save()
                draftText = ""
                AndroidUtils.hideKeyboard(requireView())
            }
            findNavController().navigateUp()
        }
        return binding.root
    }
}

