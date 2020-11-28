package com.example.remembell

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.ref.WeakReference
import java.util.Date

class NotesFragment : Fragment(), DatabaseOperations {

    private val notesOperations: NotesOperations = NotesOperations()

    override fun insertItem(item: RemembellItems) {
        Thread(Runnable {
            context?.let {
                val db = RemembellDatabase.getInstance(it)
                val dao = db?.notesModel()
                dao?.insertNote(item as Notes)
            }
        }).start()
    }

    override fun deleteItem(Id: Int) {
        Thread(Runnable {
            context?.let {
                val db = RemembellDatabase.getInstance(it)
                val dao = db?.notesModel()
                dao?.deleteNote(Id)
            }
        }).start()
    }

    override fun fetchItemsAndView() {
        context?.let {
            val reference: WeakReference<Context> = WeakReference(it)
            val operations: WeakReference<NotesOperations> = WeakReference(notesOperations)
            GetNotesAsync(reference, operations).execute()
        }
    }

    class GetNotesAsync(private val contextRef: WeakReference<Context>, private val operations: WeakReference<NotesOperations>) : AsyncTask<Void, Void, List<Notes>>() {
        override fun doInBackground(vararg params: Void?): List<Notes>? {
            val database = RemembellDatabase.getInstance(contextRef.get()!!)
            val dao = database?.notesModel()
            return dao?.getAllNotes()
        }

        override fun onPostExecute(result: List<Notes>?) {
            result?.let {
                operations.get()?.setList(it.toMutableList())
                operations.get()?.sortWrtTime()
                operations.get()?.initItemList(contextRef.get()!!)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.notes, container, false)
        fetchItemsAndView()
        val fab = view.findViewById<FloatingActionButton>(R.id.btn_notes)
        fab.setOnClickListener { v ->
            val intent = Intent(v.context, NewNote::class.java)
            startActivityForResult(intent, 111)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 111 && resultCode == Activity.RESULT_OK)
        {
            data?.let {
                val note = Notes(Date().time, it.getStringExtra("title"), it.getStringExtra("body"))
                insertItem(note)
                notesOperations.insertAndUpdate(note)
            }
        }
        if (requestCode == 222 && resultCode == Activity.RESULT_OK)
        {
            data?.let {
                val note = Notes(Date().time, it.getStringExtra("title"), it.getStringExtra("body"))
                val id = it.getIntExtra("id", -1)
                insertItem(note)
                deleteItem(id)
                notesOperations.deleteAndUpdate(id)
                notesOperations.insertAndUpdate(note)
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId)
        {
            0 ->
            {
                deleteItem(item.groupId)
                notesOperations.deleteAndUpdate(item.groupId)
            }
            1 ->
            {

            }
        }
        return true
    }
}