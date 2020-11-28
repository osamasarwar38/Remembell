package com.example.remembell

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class RemembellDatabase : RoomDatabase() {
    abstract fun notesModel() : NotesDao
    companion object{
        private var instance: RemembellDatabase? = null
        fun getInstance (context: Context) : RemembellDatabase?
        {
            if (instance == null)
            {
                instance = Room.databaseBuilder(context, RemembellDatabase::class.java, "Remembell Database").build()
            }
            return instance
        }
    }
}