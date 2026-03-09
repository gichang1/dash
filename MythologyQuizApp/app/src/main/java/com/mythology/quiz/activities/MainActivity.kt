package com.mythology.quiz.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mythology.quiz.databinding.ActivityMainBinding
import com.mythology.quiz.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupUI()
        startEntranceAnimations()
    }

    private fun setupUI() {
        binding.btnSearch.setOnClickListener {
            it.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                startActivity(Intent(this, SearchActivity::class.java))
            }.start()
        }

        binding.btnQuiz.setOnClickListener {
            it.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                startActivity(Intent(this, QuizActivity::class.java))
            }.start()
        }

        binding.btnGallery.setOnClickListener {
            it.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                startActivity(Intent(this, CharacterGalleryActivity::class.java))
            }.start()
        }

        // Mascot bounce animation
        startMascotAnimation()
    }

    private fun startMascotAnimation() {
        binding.ivMascot.apply {
            val bounceY = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, 0f, -20f, 0f)
            bounceY.duration = 1500
            bounceY.repeatCount = ObjectAnimator.INFINITE
            bounceY.interpolator = BounceInterpolator()
            bounceY.start()
        }
    }

    private fun startEntranceAnimations() {
        val views = listOf(
            binding.tvMainTitle,
            binding.ivMascot,
            binding.tvWelcome,
            binding.btnSearch,
            binding.btnQuiz,
            binding.btnGallery
        )

        views.forEachIndexed { index, view ->
            view.alpha = 0f
            view.translationY = 60f

            view.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .setStartDelay(index * 150L)
                .start()
        }
    }
}
