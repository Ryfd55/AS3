package ru.netology.nmedia3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia3.databinding.ActivityMainBinding
import ru.netology.nmedia3.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            likedByMe = false,
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb"
        )
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
//            if (post.likedByMe) {
//                likes?.setImageResource(R.drawable.likes_red)
//            }
            likesCount.text = post.likes.toString()
            sharesCount.text = Shortening.shortening(post.sharesCount)


            likes?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                likes.setImageResource(
                    if (post.likedByMe) R.drawable.likes_red else R.drawable.likes_border
                )
                if (post.likedByMe) post.likes++ else post.likes--
                likesCount.text = Shortening.shortening(post.likes)
            }

            shares?.setOnClickListener {
                post.sharesCount++
                sharesCount.text = Shortening.shortening(post.sharesCount)
            }
        }
    }
}
