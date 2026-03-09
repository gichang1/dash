package com.mythology.quiz.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.mythology.quiz.databinding.ActivitySplashBinding
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()

        scope.launch {
            delay(2800)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun startAnimations() {
        // Logo scale animation
        binding.ivSplashLogo.alpha = 0f
        binding.ivSplashLogo.scaleX = 0.3f
        binding.ivSplashLogo.scaleY = 0.3f

        val logoAlpha = ObjectAnimator.ofFloat(binding.ivSplashLogo, View.ALPHA, 0f, 1f)
        val logoScaleX = ObjectAnimator.ofFloat(binding.ivSplashLogo, View.SCALE_X, 0.3f, 1f)
        val logoScaleY = ObjectAnimator.ofFloat(binding.ivSplashLogo, View.SCALE_Y, 0.3f, 1f)

        val logoAnim = AnimatorSet().apply {
            playTogether(logoAlpha, logoScaleX, logoScaleY)
            duration = 800
            interpolator = AccelerateDecelerateInterpolator()
        }

        // Title slide up animation
        binding.tvSplashTitle.translationY = 100f
        binding.tvSplashTitle.alpha = 0f

        val titleTranslate = ObjectAnimator.ofFloat(binding.tvSplashTitle, View.TRANSLATION_Y, 100f, 0f)
        val titleAlpha = ObjectAnimator.ofFloat(binding.tvSplashTitle, View.ALPHA, 0f, 1f)

        val titleAnim = AnimatorSet().apply {
            playTogether(titleTranslate, titleAlpha)
            duration = 600
            startDelay = 600
        }

        // Subtitle animation
        binding.tvSplashSubtitle.translationY = 80f
        binding.tvSplashSubtitle.alpha = 0f

        val subtitleTranslate = ObjectAnimator.ofFloat(binding.tvSplashSubtitle, View.TRANSLATION_Y, 80f, 0f)
        val subtitleAlpha = ObjectAnimator.ofFloat(binding.tvSplashSubtitle, View.ALPHA, 0f, 1f)

        val subtitleAnim = AnimatorSet().apply {
            playTogether(subtitleTranslate, subtitleAlpha)
            duration = 600
            startDelay = 900
        }

        // Stars animation
        animateStars()

        AnimatorSet().apply {
            playTogether(logoAnim, titleAnim, subtitleAnim)
            start()
        }
    }

    private fun animateStars() {
        val stars = listOf(
            binding.tvStar1, binding.tvStar2, binding.tvStar3,
            binding.tvStar4, binding.tvStar5
        )

        stars.forEachIndexed { index, star ->
            star.alpha = 0f
            val delay = 200L + (index * 150L)

            ObjectAnimator.ofFloat(star, View.ALPHA, 0f, 1f, 0.5f, 1f).apply {
                duration = 1500
                startDelay = delay
                repeatCount = ObjectAnimator.INFINITE
                start()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}
