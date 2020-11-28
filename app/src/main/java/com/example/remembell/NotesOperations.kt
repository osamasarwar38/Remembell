package com.example.remembell

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotesOperations : FrontendOperations() {
    override fun initItemList(context: Context) {
        val activity = context as MainActivity
        recyclerView = activity.findViewById(R.id.list_of_notes)
        @Suppress("UNCHECKED_CAST")
        adapter = listOfItems?.let { NotesAdapter(it as List<Notes>) as RecyclerView.Adapter<RecyclerView.ViewHolder> }
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context)
        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        recyclerView?.addItemDecoration(itemDecoration)
    }
}