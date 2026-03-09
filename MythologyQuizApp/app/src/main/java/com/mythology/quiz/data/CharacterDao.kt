package com.mythology.quiz.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters ORDER BY nameKorean")
    fun getAllCharacters(): LiveData<List<MythCharacter>>

    @Query("SELECT * FROM characters WHERE nameKorean LIKE :query OR nameGreek LIKE :query OR attributes LIKE :query OR category LIKE :query ORDER BY nameKorean")
    fun searchCharacters(query: String): LiveData<List<MythCharacter>>

    @Query("SELECT * FROM characters WHERE category = :category ORDER BY nameKorean")
    fun getCharactersByCategory(category: String): LiveData<List<MythCharacter>>

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacterById(id: Int): MythCharacter?

    @Query("SELECT * FROM characters WHERE isUnlocked = 1 ORDER BY nameKorean")
    fun getUnlockedCharacters(): LiveData<List<MythCharacter>>

    @Query("SELECT * FROM characters ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomCharacters(count: Int): List<MythCharacter>

    @Query("SELECT * FROM characters WHERE difficulty = :difficulty ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomCharactersByDifficulty(difficulty: Int, count: Int): List<MythCharacter>

    @Update
    suspend fun updateCharacter(character: MythCharacter)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(characters: List<MythCharacter>)

    @Query("SELECT COUNT(*) FROM characters")
    suspend fun getCharacterCount(): Int

    @Query("UPDATE characters SET isUnlocked = 1 WHERE id = :id")
    suspend fun unlockCharacter(id: Int)
}
