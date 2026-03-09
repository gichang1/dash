package com.mythology.quiz.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MythRepository(private val characterDao: CharacterDao) {

    val allCharacters: LiveData<List<MythCharacter>> = characterDao.getAllCharacters()
    val unlockedCharacters: LiveData<List<MythCharacter>> = characterDao.getUnlockedCharacters()

    fun searchCharacters(query: String): LiveData<List<MythCharacter>> {
        return characterDao.searchCharacters("%$query%")
    }

    fun getCharactersByCategory(category: String): LiveData<List<MythCharacter>> {
        return characterDao.getCharactersByCategory(category)
    }

    suspend fun getRandomCharacters(count: Int): List<MythCharacter> {
        return withContext(Dispatchers.IO) {
            characterDao.getRandomCharacters(count)
        }
    }

    suspend fun generateQuizQuestions(count: Int, difficulty: Int = 0): List<QuizQuestion> {
        return withContext(Dispatchers.IO) {
            val characters = if (difficulty == 0) {
                characterDao.getRandomCharacters(count)
            } else {
                characterDao.getRandomCharactersByDifficulty(difficulty, count)
            }

            val allChars = characterDao.getRandomCharacters(50)

            characters.map { targetChar ->
                val wrongOptions = allChars
                    .filter { it.id != targetChar.id }
                    .shuffled()
                    .take(3)
                    .map { it.nameKorean }

                val options = (wrongOptions + targetChar.nameKorean).shuffled()

                QuizQuestion(
                    character = targetChar,
                    options = options,
                    correctAnswer = targetChar.nameKorean,
                    hint = targetChar.shortHint
                )
            }
        }
    }

    suspend fun unlockCharacter(id: Int) {
        withContext(Dispatchers.IO) {
            characterDao.unlockCharacter(id)
        }
    }

    suspend fun initializeIfEmpty() {
        withContext(Dispatchers.IO) {
            if (characterDao.getCharacterCount() == 0) {
                characterDao.insertAll(MythDataProvider.getInitialCharacters())
            }
        }
    }
}
