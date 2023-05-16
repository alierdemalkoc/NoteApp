package com.alierdemalkoc.noteapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.alierdemalkoc.noteapp.R
import com.alierdemalkoc.noteapp.adapter.CustomNoteAdapter
import com.alierdemalkoc.noteapp.databinding.FragmentNotesBinding
import com.alierdemalkoc.noteapp.db.DB
import com.alierdemalkoc.noteapp.model.Note

class NotesFragment : Fragment() {
    private var _binding : FragmentNotesBinding? = null
    private val binding get() = _binding!!
    lateinit var listView: ListView
    lateinit var list: List<Note>
    lateinit var db: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addButton = activity?.findViewById<Button>(R.id.adButton)
        addButton!!.visibility = View.VISIBLE
        addButton!!.setOnClickListener {
            findNavController().navigate(R.id.notesToAdd)
        }
        listView = binding.notesListView
        db = DB(requireContext())
        list = db.allNote()
        val customAdapter = CustomNoteAdapter(requireActivity(), list)
        listView.adapter = customAdapter
        listView.setOnItemClickListener { adapterView, view, i, l ->
            val bundle = Bundle()
            bundle.putInt("list", i)
            findNavController().navigate(R.id.notesToDetail, bundle)
        }

    }

}