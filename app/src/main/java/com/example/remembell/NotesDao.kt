package com.example.remembell

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotesDao {

    @Insert
    fun insertNote(obj: Notes)

    @Query("select * from notes")
    fun getAllNotes() : List<Notes>

    @Query("delete from notes where id = :ID")
    fun deleteNote(ID: Int)
}