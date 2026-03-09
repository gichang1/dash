package com.mythology.quiz.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mythology.quiz.databinding.ActivityQuizResultBinding
import com.mythology.quiz.viewmodels.MainViewModel

class QuizResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizResultBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val score = intent.getIntExtra("score", 0)
        val correct = intent.getIntExtra("correct", 0)
        val total = intent.getIntExtra("total", 10)
        val characterIds = intent.getIntegerArrayListExtra("character_ids") ?: arrayListOf()

        displayResult(score, correct, total)
        setupButtons(score, correct, total)
    }

    private fun displayResult(score: Int, correct: Int, total: Int) {
        val percentage = (correct.toFloat() / total * 100).toInt()

        // Determine result grade
        val (grade, emoji, message, bgColor) = when {
            percentage >= 90 -> ResultInfo("S", "🏆", "완벽해요! 신화 마스터!", "#FFD700")
            percentage >= 80 -> ResultInfo("A", "⭐", "훌륭해요! 신화 박사!", "#C0C0C0")
            percentage >= 70 -> ResultInfo("B", "👍", "잘했어요! 신화 탐험가!", "#CD7F32")
            percentage >= 60 -> ResultInfo("C", "😊", "좋아요! 계속 도전하세요!", "#4CAF50")
            percentage >= 40 -> ResultInfo("D", "💪", "조금 더 공부해봐요!", "#FF9800")
            else -> ResultInfo("F", "📚", "처음부터 다시 배워봐요!", "#F44336")
        }

        binding.tvGrade.text = grade
        binding.tvGradeEmoji.text = emoji
        binding.tvResultMessage.text = message
        binding.tvScore.text = "$score 점"
        binding.tvCorrectCount.text = "$correct / $total 문제"
        binding.tvPercentage.text = "$percentage%"

        // Animate score
        animateScore(score)
        animateGrade()

        // Confetti for high scores
        if (percentage >= 70) {
            startConfettiAnimation()
        }
    }

    private fun animateScore(finalScore: Int) {
        var currentScore = 0
        val step = (finalScore / 30).coerceAtLeast(1)

        val runnable = object : Runnable {
            override fun run() {
                currentScore += step
                if (currentScore >= finalScore) {
                    currentScore = finalScore
                    binding.tvScore.text = "$finalScore 점"
                } else {
                    binding.tvScore.text = "$currentScore 점"
                    binding.tvScore.postDelayed(this, 30)
                }
            }
        }
        binding.tvScore.postDelayed(runnable, 500)
    }

    private fun animateGrade() {
        binding.tvGrade.scaleX = 0f
        binding.tvGrade.scaleY = 0f

        val scaleX = ObjectAnimator.ofFloat(binding.tvGrade, View.SCALE_X, 0f, 1.3f, 1f)
        val scaleY = ObjectAnimator.ofFloat(binding.tvGrade, View.SCALE_Y, 0f, 1.3f, 1f)

        AnimatorSet().apply {
            playTogether(scaleX, scaleY)
            duration = 600
            startDelay = 300
            interpolator = BounceInterpolator()
            start()
        }
    }

    private fun startConfettiAnimation() {
        val confettiEmojis = listOf(
            binding.tvConfetti1, binding.tvConfetti2,
            binding.tvConfetti3, binding.tvConfetti4, binding.tvConfetti5
        )

        val emojis = listOf("⭐", "🎉", "✨", "🏆", "🌟")

        confettiEmojis.forEachIndexed { index, tv ->
            tv.text = emojis[index % emojis.size]
            tv.alpha = 0f

            val startX = (Math.random() * 300 - 150).toFloat()
            val startY = -200f

            tv.translationX = startX
            tv.translationY = startY

            tv.animate()
                .alpha(1f)
                .translationY(600f)
                .setDuration(2000)
                .setStartDelay(index * 300L)
                .withEndAction { tv.alpha = 0f }
                .start()
        }
    }

    private fun setupButtons(score: Int, correct: Int, total: Int) {
        binding.btnPlayAgain.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnViewGallery.setOnClickListener {
            startActivity(Intent(this, CharacterGalleryActivity::class.java))
            finish()
        }

        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        // Animate buttons in
        val buttons = listOf(binding.btnPlayAgain, binding.btnViewGallery, binding.btnHome)
        buttons.forEachIndexed { index, btn ->
            btn.alpha = 0f
            btn.translationY = 50f
            btn.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(400)
                .setStartDelay(800L + index * 150)
                .start()
        }
    }

    private data class ResultInfo(
        val grade: String,
        val emoji: String,
        val message: String,
        val color: String
    )
}
