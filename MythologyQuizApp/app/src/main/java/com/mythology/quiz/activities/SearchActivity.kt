package com.mythology.quiz.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.mythology.quiz.adapters.CharacterAdapter
import com.mythology.quiz.data.MythCharacter
import com.mythology.quiz.data.MythDataProvider
import com.mythology.quiz.databinding.ActivitySearchBinding
import com.mythology.quiz.viewmodels.MainViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupRecyclerView()
        setupSearchBar()
        setupCategoryChips()
        observeData()

        binding.btnBack.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        characterAdapter = CharacterAdapter { character ->
            val intent = Intent(this, CharacterDetailActivity::class.java)
            intent.putExtra("character_id", character.id)
            intent.putExtra("character_name", character.nameKorean)
            startActivity(intent)
        }

        binding.rvCharacters.apply {
            layoutManager = GridLayoutManager(this@SearchActivity, 2)
            adapter = characterAdapter
        }
    }

    private fun setupSearchBar() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s?.toString() ?: ""
                viewModel.search(query)
            }
        })

        binding.ivClearSearch.setOnClickListener {
            binding.etSearch.text?.clear()
        }
    }

    private fun setupCategoryChips() {
        val categories = MythDataProvider.getCategories()
        categories.forEach { category ->
            val chip = Chip(this).apply {
                text = category
                isCheckable = true
                isChecked = category == "전체"
                setOnClickListener {
                    viewModel.selectCategory(category)
                    viewModel.search(binding.etSearch.text?.toString() ?: "")
                }
            }
            binding.chipGroupCategories.addView(chip)
        }
    }

    private fun observeData() {
        viewModel.searchResults.observe(this) { characters ->
            updateAdapter(characters)
        }
    }

    private fun updateAdapter(characters: List<MythCharacter>) {
        characterAdapter.submitList(characters)
        binding.tvResultCount.text = "총 ${characters.size}개의 캐릭터"
        binding.tvEmptyState.visibility =
            if (characters.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
    }
}
