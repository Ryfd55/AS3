package ru.netology.nmedia3.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia3.R
import ru.netology.nmedia3.databinding.FragmentNewPostBinding
import ru.netology.nmedia3.utils.AndroidUtils
import ru.netology.nmedia3.viewmodel.PostViewModel
import androidx.activity.OnBackPressedCallback
import ru.netology.nmedia3.SharedPreferencesHelper

class NewPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(
            inflater, container, false
        )
        val viewModel: PostViewModel by activityViewModels()
        val currentValue = SharedPreferencesHelper.getDraftContent(requireContext())
        binding.content.setText(currentValue)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val draftContent = binding.content.text.toString()
                SharedPreferencesHelper.saveDraftContent(requireContext(), draftContent)

                findNavController().navigate(
                    R.id.action_newPostFragment_to_feedFragment
                )
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.ok.setOnClickListener {
            val text = binding.content.text.toString()
            if (text.isNotBlank()) {
                viewModel.changeContent(text)
                viewModel.save()
                SharedPreferencesHelper.saveDraftContent(requireContext(), "")
                AndroidUtils.hideKeyboard(requireView())
            }
            findNavController().navigateUp()
        }
        return binding.root
    }
}
