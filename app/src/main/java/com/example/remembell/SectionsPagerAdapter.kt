package com.example.remembell

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        when (position)
        {
            0-> return NotesFragment()
            1-> return TodoFragment()
            2-> return RemindersFragment()
            3-> return AlarmsFragment()
        }
        return NotesFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position)
        {
            0-> return "Notes"
            1-> return "To-do"
            2-> return "Reminders"
            3-> return "Alarms"
        }
        return null
    }

    override fun getCount(): Int {
        // Show 4 total pages.
        return 4
    }
}