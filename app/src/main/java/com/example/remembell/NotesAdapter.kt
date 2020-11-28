package com.example.remembell

import android.content.Intent
import android.text.format.DateUtils
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class NotesAdapter(private val listOfNotes: List<Notes>) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfNotes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notesObj = listOfNotes[position]
        when {
            DateUtils.isToday(notesObj.time) -> {
                holder.timeView.text = "Today, " + SimpleDateFormat("hh:mm a", Locale.getDefault()).format(notesObj.time)
            }
            DateUtils.isToday(notesObj.time + DateUtils.DAY_IN_MILLIS) -> {
                holder.timeView.text = "Yesterday, " + SimpleDateFormat("hh:mm a", Locale.getDefault()).format(notesObj.time)
            }
            else -> holder.timeView.text = SimpleDateFormat("dd MMM, yyyy  hh:mm a", Locale.getDefault()).format(notesObj.time)
        }
        holder.titleView.text = notesObj.title
        holder.notesView.text = notesObj.noteBody
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnCreateContextMenuListener
    {
        init {
            itemView.setOnClickListener(this)
            itemView.setOnCreateContextMenuListener(this)
        }
        val timeView = itemView.findViewById(R.id.time) as TextView
        val titleView = itemView.findViewById(R.id.title) as TextView
        val notesView = itemView.findViewById(R.id.note_body) as TextView

        override fun onClick(v: View?) {
            val intent = Intent(v?.context, NewNote::class.java)
            intent.putExtra("title", if (listOfNotes[adapterPosition].title == "Untitled Note") "" else listOfNotes[adapterPosition].title )
            intent.putExtra("body", listOfNotes[adapterPosition].noteBody)
            intent.putExtra("id", listOfNotes[adapterPosition].id)
            val activity = v?.context as MainActivity
            activity.startActivityForResult(intent, 222)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add(listOfNotes[adapterPosition].id, 0, 0, "Delete")
            menu?.add(adapterPosition, 1, 1, "Copy to clipboard")
            menu?.add(adapterPosition, 2, 2, "Set reminder")
        }
    }
}