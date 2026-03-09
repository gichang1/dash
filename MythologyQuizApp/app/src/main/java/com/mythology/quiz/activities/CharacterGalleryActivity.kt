package com.mythology.quiz.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mythology.quiz.adapters.GalleryAdapter
import com.mythology.quiz.databinding.ActivityCharacterGalleryBinding
import com.mythology.quiz.viewmodels.MainViewModel

class CharacterGalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterGalleryBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var galleryAdapter: GalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupRecyclerView()
        observeData()

        binding.btnBack.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        galleryAdapter = GalleryAdapter { character ->
            val intent = Intent(this, CharacterDetailActivity::class.java)
            intent.putExtra("character_id", character.id)
            intent.putExtra("character_name", character.nameKorean)
            startActivity(intent)
        }

        binding.rvGallery.apply {
            layoutManager = GridLayoutManager(this@CharacterGalleryActivity, 3)
            adapter = galleryAdapter
        }
    }

    private fun observeData() {
        viewModel.allCharacters.observe(this) { characters ->
            galleryAdapter.submitList(characters)

            val unlocked = characters.count { it.isUnlocked }
            val total = characters.size
            binding.tvProgress.text = "해금된 캐릭터: $unlocked / $total"
            binding.progressBarGallery.max = total
            binding.progressBarGallery.progress = unlocked
        }
    }
}
