package com.example.remembell

import androidx.room.PrimaryKey

open class RemembellItems (open val time: Long) {

    @PrimaryKey(autoGenerate = true) open var id: Int = 0
}