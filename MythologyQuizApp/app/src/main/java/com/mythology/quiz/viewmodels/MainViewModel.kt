package com.mythology.quiz.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.mythology.quiz.data.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MythRepository
    val allCharacters: LiveData<List<MythCharacter>>
    val unlockedCharacters: LiveData<List<MythCharacter>>

    private val _searchQuery = MutableLiveData<String>()
    private val _selectedCategory = MutableLiveData<String>("전체")
    private val _quizQuestions = MutableLiveData<List<QuizQuestion>>()
    private val _isLoading = MutableLiveData<Boolean>(false)

    val quizQuestions: LiveData<List<QuizQuestion>> = _quizQuestions
    val isLoading: LiveData<Boolean> = _isLoading
    val selectedCategory: LiveData<String> = _selectedCategory

    val searchResults: LiveData<List<MythCharacter>> = _searchQuery.switchMap { query ->
        if (query.isBlank()) {
            repository.allCharacters
        } else {
            repository.searchCharacters(query)
        }
    }

    val filteredCharacters: LiveData<List<MythCharacter>> = _selectedCategory.switchMap { category ->
        if (category == "전체") {
            repository.allCharacters
        } else {
            repository.getCharactersByCategory(category)
        }
    }

    init {
        val db = MythDatabase.getDatabase(application)
        repository = MythRepository(db.characterDao())
        allCharacters = repository.allCharacters
        unlockedCharacters = repository.unlockedCharacters

        viewModelScope.launch {
            repository.initializeIfEmpty()
        }
    }

    fun search(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun generateQuiz(count: Int = 10, difficulty: Int = 0) {
        _isLoading.value = true
        viewModelScope.launch {
            val questions = repository.generateQuizQuestions(count, difficulty)
            _quizQuestions.value = questions
            _isLoading.value = false
        }
    }

    fun unlockCharacter(id: Int) {
        viewModelScope.launch {
            repository.unlockCharacter(id)
        }
    }
}
