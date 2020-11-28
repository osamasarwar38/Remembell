package com.example.remembell

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_new_note.*

class NewNote : AppCompatActivity() {

    var id = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        val toolbar = findViewById<Toolbar>(R.id.new_note_bar)
        setSupportActionBar(toolbar)

        note_body.requestFocus()

        val textWatcher = object : TextWatcher
        {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.get(start) != '\n' && start < 50)
                    notes_title.setText(s)
                else note_body.removeTextChangedListener(this)
            }
        }

        notes_title.setOnTouchListener { v, event ->
            note_body.removeTextChangedListener(textWatcher)
            return@setOnTouchListener false
        }

        note_body.addTextChangedListener(textWatcher)
        val title = intent.getStringExtra("title")
        if (title != null)
        {
            val body = intent.getStringExtra("body")
            id = intent.getIntExtra("id", -1)
            notes_title.setText(if (title == "Untitled Note") "" else title)
            note_body.setText(body)
            note_body.setSelection(body.length)
        }
    }

    override fun onBackPressed() {
        var title = intent.getStringExtra("title")
        if (title != null)
        {
            val body = intent.getStringExtra("body")
            title = if (title == "Untitled Note") "" else title
            if (title == notes_title.text.toString() && body == note_body.text.toString())
            {
                finish()
                return
            }
        }
        if (note_body.text.toString() != "" /*and title also*/)
        {
            val intent = Intent()
            intent.putExtra("title", if (notes_title.text.toString() == "") "Untitled Note" else notes_title.text.toString())
            intent.putExtra("body", note_body.text.toString())
            intent.putExtra("id", id)
            setResult(Activity.RESULT_OK, intent)
        }
        finish()
    }
}
