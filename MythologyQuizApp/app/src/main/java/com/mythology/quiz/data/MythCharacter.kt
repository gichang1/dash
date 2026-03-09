package com.mythology.quiz.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class MythCharacter(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nameKorean: String,        // 한국어 이름
    val nameGreek: String,         // 그리스어/영어 이름
    val category: String,          // 올림포스 12신, 영웅, 괴물 등
    val description: String,       // 설명
    val shortHint: String,         // 퀴즈 힌트
    val attributes: String,        // 특징 (예: 번개, 바다, 전쟁)
    val emoji: String,             // 대표 이모지
    val characterDrawable: String, // 귀여운 캐릭터 drawable 이름
    val difficulty: Int,           // 난이도 1-3
    val isUnlocked: Boolean = false
)

data class QuizQuestion(
    val character: MythCharacter,
    val options: List<String>,     // 4개 선택지 (정답 포함)
    val correctAnswer: String,
    val hint: String
)

data class QuizResult(
    val totalQuestions: Int,
    val correctAnswers: Int,
    val unlockedCharacters: List<MythCharacter>
)
