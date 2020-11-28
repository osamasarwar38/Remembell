package com.example.remembell

import androidx.room.Entity
import androidx.room.Ignore

@Entity
class Notes(@Ignore override val time: Long, val title: String, val noteBody: String) : RemembellItems(time)