package com.mythology.quiz

import android.app.Application
import com.mythology.quiz.data.MythDatabase
import com.mythology.quiz.data.MythRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MythologyApp : Application() {

    val database by lazy { MythDatabase.getDatabase(this) }
    val repository by lazy { MythRepository(database.characterDao()) }

    override fun onCreate() {
        super.onCreate()
        // Initialize database with characters if empty
        CoroutineScope(Dispatchers.IO).launch {
            repository.initializeIfEmpty()
        }
    }
}
