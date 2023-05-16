package com.alierdemalkoc.noteapp.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.alierdemalkoc.noteapp.R
import com.alierdemalkoc.noteapp.model.Note

class CustomNoteAdapter (private val context: Activity, private val list: List<Note>) : ArrayAdapter<Note>(context, R.layout.custom_list_item, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rootView = context.layoutInflater.inflate(R.layout.custom_list_item, null, true)

        val r_title = rootView.findViewById<TextView>(R.id.r_title)
        val r_date = rootView.findViewById<TextView>(R.id.r_date)

        val note = list.get(position)

        r_title.text = "${note.title}"
        r_date.text = "${note.date}"

        return rootView
    }

}