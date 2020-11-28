package com.example.remembell

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

abstract class FrontendOperations {

    protected var listOfItems: MutableList<RemembellItems>? = null
    protected var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
    protected var recyclerView: RecyclerView? = null

    fun setList (list: MutableList<RemembellItems>)
    {
        listOfItems = list
    }

    fun getList (): List<RemembellItems>?
    {
        return listOfItems
    }

    fun sortWrtTime ()
    {
        listOfItems = listOfItems?.sortedWith(compareByDescending { it.time })?.toMutableList()
    }

    fun deleteAndUpdate (id: Int)
    {
        for ( (index, note) in listOfItems!!.withIndex() )
        {
            if (note.id == id)
            {
                listOfItems!!.removeAt(index)
                adapter?.notifyItemRemoved(index)
                break
            }
        }
    }

    fun insertAndUpdate (item: RemembellItems)
    {
        listOfItems?.add(0, item)
        adapter?.notifyItemInserted(0)
        recyclerView?.scrollToPosition(0)
    }

    abstract fun initItemList(context: Context)

}