package ru.netology.nmedia3.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia3.R
import ru.netology.nmedia3.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?. let{
            if(it.action != Intent.ACTION_SEND){
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()){
                Snackbar.make(binding.root, R.string.error_empty_content, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok){
                        finish()
                    }
                    .show()
            }
            findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_feedFragment_to_newPostFragment2,
                    bundleOf("textArg" to text)
                )
        }
    }
}