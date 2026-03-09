package com.mythology.quiz.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mythology.quiz.data.MythCharacter
import com.mythology.quiz.data.MythDataProvider
import com.mythology.quiz.databinding.ActivityCharacterDetailBinding
import com.mythology.quiz.viewmodels.MainViewModel

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val characterName = intent.getStringExtra("character_name") ?: ""
        binding.tvCharacterTitle.text = characterName

        binding.btnBack.setOnClickListener { finish() }

        // Find and display character
        val allChars = MythDataProvider.getInitialCharacters()
        val character = allChars.find { it.nameKorean == characterName }
        character?.let { displayCharacter(it) }
    }

    private fun displayCharacter(character: MythCharacter) {
        binding.apply {
            val resId = resources.getIdentifier(
                character.characterDrawable, "drawable", packageName)
            if (resId != 0) {
                ivCharacterImage.setImageResource(resId)
                ivCharacterImage.visibility = View.VISIBLE
                tvCuteCharacter.visibility = View.GONE
            } else {
                ivCharacterImage.visibility = View.GONE
                tvCuteCharacter.visibility = View.VISIBLE
            }
            tvCharacterEmoji.text = character.emoji
            tvCharacterName.text = character.nameKorean
            tvGreekName.text = character.nameGreek
            tvCategory.text = character.category
            tvDescription.text = character.description
            tvDifficulty.text = "★".repeat(character.difficulty) + "☆".repeat(3 - character.difficulty)

            // Attributes as chips
            val attrs = character.attributes.split(",")
            tvAttributes.text = attrs.joinToString("  ") { "• ${it.trim()}" }
        }

        // Display cute character
        displayCuteCharacter(character)

        // Entrance animations
        startAnimations()
    }

    private fun displayCuteCharacter(character: MythCharacter) {
        // Show cute character art using emoji combinations
        val cuteChar = getCuteCharacterDisplay(character)
        binding.tvCuteCharacter.text = cuteChar

        // Bounce animation for cute character
        val bounceAnim = ObjectAnimator.ofFloat(binding.tvCuteCharacter, View.TRANSLATION_Y, 0f, -15f, 0f)
        bounceAnim.duration = 1200
        bounceAnim.repeatCount = ObjectAnimator.INFINITE
        bounceAnim.interpolator = BounceInterpolator()
        bounceAnim.start()
    }

    private fun getCuteCharacterDisplay(character: MythCharacter): String {
        return when (character.nameGreek.lowercase()) {
            "zeus" -> "⚡👑\n(◕‿◕)\n/||\\"
            "hera" -> "👑🦚\n(◠‿◠)\n/||\\"
            "poseidon" -> "🔱🌊\n(≧◡≦)\n/||\\"
            "athena" -> "🦉🛡️\n(⌐■_■)\n/||\\"
            "apollo" -> "☀️🎵\n(♡‿♡)\n/||\\"
            "artemis" -> "🌙🏹\n(◕ᴗ◕)\n/||\\"
            "aphrodite" -> "🌹💕\n(❁◡❁)\n/||\\"
            "ares" -> "⚔️🔥\n(ò_ó)\n/||\\"
            "hephaestus" -> "🔨⚙️\n(ó‿ò)\n/||\\"
            "hermes" -> "🪄👟\n(°▽°)\n/||\\"
            "demeter" -> "🌾🌻\n(◡‿◡)\n/||\\"
            "dionysus" -> "🍇🍷\n(＾▽＾)\n/||\\"
            "hades" -> "💀🌑\n(>_<)\n/||\\"
            "persephone" -> "🌸🌺\n(✿◠‿◠)\n/||\\"
            "eros" -> "💘🏹\n(♥ω♥)\n/||\\"
            "nike" -> "🏆🪶\n(≧▽≦)\n/||\\"
            "hypnos" -> "😴💤\n(-.-)Zzz\n/||\\"
            "heracles" -> "💪🦁\n(ノ͡° ͜ʖ ͡°)ノ\n/||\\"
            "perseus" -> "🛡️⚔️\n(ᵔ◡ᵔ)\n/||\\"
            "theseus" -> "🗡️🏛️\n(•̀ᴗ•́)\n/||\\"
            "odysseus" -> "🐴⚓\n(ó‿ò✿)\n/||\\"
            "achilles" -> "⚡🎯\n(ꏿ_ꏿ)\n/||\\"
            "jason" -> "⚓🌊\n(◕‿↼)\n/||\\"
            "orpheus" -> "🎵🎶\n(♪‿♪)\n/||\\"
            "medusa" -> "🐍👀\n(◉_◉)\n~~~"
            "minotaur" -> "🐂👊\n(ò‿ó)\n|  |"
            "cyclops" -> "👁️💥\n(  ●  )\n/||\\"
            "cerberus" -> "🐕🐕🐕\n(•ω•)(•ω•)(•ω•)\n|  |"
            "hydra" -> "🐲🐲\n(≖_≖)(≖_≖)\n~~~"
            "sphinx" -> "🦁❓\n(◔_◔)\n/||\\"
            "prometheus" -> "🔥⛓️\n(ToT)\n/||\\"
            "cronos" -> "⏰🗡️\n(›_‹)\n/||\\"
            "atlas" -> "🌍💪\n(;-;)\n/ \\"
            "pandora" -> "📦❓\n(OwO)\n/||\\"
            "icarus" -> "🪶☀️\n(°o°)\n↓↓"
            "narcissus" -> "🌼💧\n(❤‿❤)\n/||\\"
            "midas" -> "✨👑\n(ʘ‿ʘ)\n/||\\"
            else -> "${character.emoji}\n(◕‿◕)\n/||\\"
        }
    }

    private fun startAnimations() {
        val views = listOf(
            binding.cardCuteChar,
            binding.cardInfo,
            binding.cardDescription
        )

        views.forEachIndexed { index, view ->
            view.alpha = 0f
            view.translationY = 80f
            view.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .setStartDelay(index * 200L)
                .start()
        }
    }
}
