package ru.netology.nmedia3.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia3.R
import ru.netology.nmedia3.databinding.FragmentEditPostBinding
import ru.netology.nmedia3.util.AndroidUtils
import ru.netology.nmedia3.util.StringArg
import ru.netology.nmedia3.viewmodel.PostViewModel

class EditPostFragment : Fragment() {

    companion object {
        var Bundle.edit: String? by StringArg
    }

    private val viewModel: PostViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditPostBinding.inflate(
            inflater,
            container,
            false
        )

        arguments?.edit?.let(binding.editText::setText)
        binding.editText.setText(arguments?.getString("editedText"))


        binding.ok.setOnClickListener {
            if (binding.editText.text.isNullOrBlank()) {
                Toast.makeText(
                    activity,
                    this.getString(R.string.error_empty_content),
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                viewModel.changeContent(binding.editText.text.toString())
                viewModel.save()
                findNavController().navigateUp()
            }

            AndroidUtils.hideKeyboard(requireView())
            binding.ok.isVisible = false
            binding.editText.isVisible = false
            binding.savingProgressBar.isVisible = true
        }

        return binding.root
    }
}