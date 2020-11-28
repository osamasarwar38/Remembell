package com.example.remembell

interface DatabaseOperations {

    fun insertItem (item: RemembellItems)
    fun deleteItem (Id: Int)
    fun fetchItemsAndView ()
}