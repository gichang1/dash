package com.mythology.quiz.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mythology.quiz.data.QuizQuestion
import com.mythology.quiz.databinding.ActivityQuizBinding
import com.mythology.quiz.viewmodels.MainViewModel
import com.google.android.material.button.MaterialButton
import android.widget.Toast

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var viewModel: MainViewModel

    private var questions: List<QuizQuestion> = emptyList()
    private var currentQuestionIndex = 0
    private var score = 0
    private var correctCount = 0
    private var timer: CountDownTimer? = null
    private var isAnswered = false

    private val optionButtons: List<MaterialButton> by lazy {
        listOf(binding.btnOption1, binding.btnOption2, binding.btnOption3, binding.btnOption4)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.btnBack.setOnClickListener { finish() }

        setupDifficultySelection()
    }

    private fun setupDifficultySelection() {
        binding.layoutDifficultySelect.visibility = View.VISIBLE
        binding.layoutQuiz.visibility = View.GONE

        binding.btnEasy.setOnClickListener { startQuiz(1) }
        binding.btnNormal.setOnClickListener { startQuiz(0) }
        binding.btnHard.setOnClickListener { startQuiz(3) }
    }

    private fun startQuiz(difficulty: Int) {
        binding.layoutDifficultySelect.visibility = View.GONE
        binding.layoutQuiz.visibility = View.VISIBLE
        binding.layoutLoading.visibility = View.VISIBLE

        viewModel.generateQuiz(10, difficulty)

        viewModel.quizQuestions.observe(this) { generatedQuestions ->
            if (generatedQuestions.isNotEmpty()) {
                questions = generatedQuestions
                binding.layoutLoading.visibility = View.GONE
                showQuestion(0)
            }
        }
    }

    private fun showQuestion(index: Int) {
        if (index >= questions.size) {
            showResult()
            return
        }

        currentQuestionIndex = index
        isAnswered = false
        val question = questions[index]

        // Update progress
        binding.progressBar.max = questions.size
        binding.progressBar.progress = index + 1
        binding.tvProgress.text = "${index + 1} / ${questions.size}"

        // Update score
        binding.tvScore.text = "점수: $score"

        // Animate question in
        binding.cardQuestion.alpha = 0f
        binding.cardQuestion.translationX = 100f

        binding.cardQuestion.animate()
            .alpha(1f)
            .translationX(0f)
            .setDuration(300)
            .start()

        // Set character image or emoji
        val resId = resources.getIdentifier(
            question.character.characterDrawable, "drawable", packageName)
        if (resId != 0) {
            binding.ivQuestionImage.setImageResource(resId)
            binding.ivQuestionImage.visibility = View.VISIBLE
            binding.tvQuestionEmoji.visibility = View.GONE
        } else {
            binding.ivQuestionImage.visibility = View.GONE
            binding.tvQuestionEmoji.visibility = View.VISIBLE
            binding.tvQuestionEmoji.text = question.character.emoji
        }
        binding.tvQuestionHint.text = question.hint
        binding.tvCategory.text = question.character.category
        binding.tvDifficulty.text = "★".repeat(question.character.difficulty)

        // Reset and set options
        optionButtons.forEachIndexed { i, btn ->
            btn.text = if (i < question.options.size) question.options[i] else ""
            btn.isEnabled = true
            btn.setBackgroundColor(getColor(com.mythology.quiz.R.color.option_default))
            btn.setTextColor(Color.WHITE)
            btn.visibility = if (i < question.options.size) View.VISIBLE else View.GONE

            btn.setOnClickListener {
                if (!isAnswered) {
                    checkAnswer(question, question.options[i], btn)
                }
            }
        }

        // Start timer
        startTimer()
    }

    private fun startTimer() {
        timer?.cancel()

        binding.tvTimer.setTextColor(getColor(android.R.color.holo_green_dark))
        var timeLeft = 20

        timer = object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = (millisUntilFinished / 1000).toInt()
                binding.tvTimer.text = "⏱ ${timeLeft}초"
                binding.timerProgressBar.progress = timeLeft

                if (timeLeft <= 5) {
                    binding.tvTimer.setTextColor(getColor(android.R.color.holo_red_light))
                    // Shake animation for urgency
                    binding.tvTimer.animate()
                        .translationX(5f)
                        .setDuration(50)
                        .withEndAction {
                            binding.tvTimer.animate().translationX(-5f).setDuration(50).withEndAction {
                                binding.tvTimer.animate().translationX(0f).setDuration(50).start()
                            }.start()
                        }.start()
                }
            }

            override fun onFinish() {
                if (!isAnswered) {
                    binding.tvTimer.text = "⏱ 시간 초과!"
                    showTimeOut()
                }
            }
        }.start()
    }

    private fun checkAnswer(question: QuizQuestion, selectedAnswer: String, selectedBtn: MaterialButton) {
        isAnswered = true
        timer?.cancel()

        if (selectedAnswer == question.correctAnswer) {
            // Correct!
            score += 10 + (question.character.difficulty * 5)
            correctCount++

            selectedBtn.setBackgroundColor(getColor(com.mythology.quiz.R.color.correct_green))
            showCorrectAnimation()

            // Show character unlock
            binding.layoutCorrect.visibility = View.VISIBLE
            binding.tvCorrectCharName.text = question.character.nameKorean
            binding.tvCorrectEmoji.text = question.character.emoji
            binding.tvCorrectGreekName.text = question.character.nameGreek

            viewModel.unlockCharacter(question.character.id)

            // Auto next after delay
            binding.btnNext.isEnabled = false
            binding.cardQuestion.postDelayed({
                binding.layoutCorrect.visibility = View.GONE
                binding.btnNext.isEnabled = true
                nextQuestion()
            }, 2500)

        } else {
            // Wrong!
            selectedBtn.setBackgroundColor(getColor(com.mythology.quiz.R.color.wrong_red))

            // Show correct answer
            optionButtons.forEach { btn ->
                if (btn.text == question.correctAnswer) {
                    btn.setBackgroundColor(getColor(com.mythology.quiz.R.color.correct_green))
                }
            }

            showWrongAnimation()
            binding.layoutWrong.visibility = View.VISIBLE
            binding.tvWrongAnswer.text = "정답: ${question.correctAnswer}"
            binding.tvWrongEmoji.text = question.character.emoji

            binding.cardQuestion.postDelayed({
                binding.layoutWrong.visibility = View.GONE
                nextQuestion()
            }, 2000)
        }

        binding.btnNext.setOnClickListener {
            timer?.cancel()
            binding.layoutCorrect.visibility = View.GONE
            binding.layoutWrong.visibility = View.GONE
            nextQuestion()
        }
    }

    private fun showTimeOut() {
        isAnswered = true
        Toast.makeText(this, "⏰ 시간 초과!", Toast.LENGTH_SHORT).show()

        val question = questions[currentQuestionIndex]
        optionButtons.forEach { btn ->
            if (btn.text == question.correctAnswer) {
                btn.setBackgroundColor(getColor(com.mythology.quiz.R.color.correct_green))
            }
            btn.isEnabled = false
        }

        binding.cardQuestion.postDelayed({
            nextQuestion()
        }, 1500)
    }

    private fun showCorrectAnimation() {
        val scaleX = ObjectAnimator.ofFloat(binding.tvCorrectBig, View.SCALE_X, 0f, 1.2f, 1f)
        val scaleY = ObjectAnimator.ofFloat(binding.tvCorrectBig, View.SCALE_Y, 0f, 1.2f, 1f)
        val alpha = ObjectAnimator.ofFloat(binding.tvCorrectBig, View.ALPHA, 0f, 1f)

        AnimatorSet().apply {
            playTogether(scaleX, scaleY, alpha)
            duration = 400
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    private fun showWrongAnimation() {
        val shakeAnim = ObjectAnimator.ofFloat(binding.cardQuestion, View.TRANSLATION_X,
            0f, -20f, 20f, -20f, 20f, -10f, 10f, 0f)
        shakeAnim.duration = 500
        shakeAnim.start()
    }

    private fun nextQuestion() {
        showQuestion(currentQuestionIndex + 1)
    }

    private fun showResult() {
        timer?.cancel()
        val intent = Intent(this, QuizResultActivity::class.java).apply {
            putExtra("score", score)
            putExtra("correct", correctCount)
            putExtra("total", questions.size)
            putIntegerArrayListExtra("character_ids",
                ArrayList(questions.map { it.character.id }))
        }
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}
