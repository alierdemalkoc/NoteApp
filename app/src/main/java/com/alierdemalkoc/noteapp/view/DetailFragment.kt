package com.alierdemalkoc.noteapp.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.alierdemalkoc.noteapp.R
import com.alierdemalkoc.noteapp.databinding.FragmentDetailBinding
import com.alierdemalkoc.noteapp.databinding.FragmentNotesBinding
import com.alierdemalkoc.noteapp.databinding.FragmentSplashBinding
import com.alierdemalkoc.noteapp.db.DB
import java.util.*

class DetailFragment : Fragment() {
    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var removeButton: Button
    lateinit var updateButton: Button
    lateinit var dateEditButton: Button
    lateinit var titleEditText: EditText
    lateinit var detailEditText: EditText
    lateinit var dateEditText: EditText
    lateinit var selectDate: String

    lateinit var db: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addButton = activity?.findViewById<Button>(R.id.adButton)
        addButton!!.visibility = View.GONE
        db = DB(requireContext())
        updateButton = binding.updateButton
        removeButton = binding.removeButton
        detailEditText = binding.detailEditText
        titleEditText = binding.titleEditText
        dateEditText = binding.dateEditText
        dateEditButton = binding.dateEditButton
        dateEditText.isEnabled = false
        val list = db.allNote()
        val id = arguments?.getInt("list")
        titleEditText.setText(id?.let { list.get(it).title.toString() })
        detailEditText.setText(id?.let { list.get(it).detail.toString() })
        dateEditText.setText(id?.let { list.get(it).date.toString() })
        val calender = Calendar.getInstance()
        selectDate = dateEditText.text.toString()
        dateEditButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                    Log.d("i", i.toString()) // yıl
                    Log.d("i2", (i2 + 1).toString()) // ay
                    Log.d("i3", i3.toString()) // gün
                    var ay = "${i2 + 1}"
                    if (i2 + 1 < 10) {
                        ay = "0${i2 + 1}"
                    }
                    selectDate = "$i3.$ay.$i"
                    dateEditText.setText(selectDate)
                },
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH),
            )
            datePickerDialog.show()
        }

        val nid = id?.let { list.get(it).nid }

        updateButton.setOnClickListener {

            if (selectDate != "" && detailEditText.text.toString() != "" && titleEditText.text.toString() != "") {
                db.updateNote(titleEditText.text.toString(), detailEditText.text.toString(), selectDate, nid!!)
                selectDate = ""
                findNavController().navigate(R.id.detailToNotes)
            } else if (selectDate.equals("")) {
                Toast.makeText(requireContext(), "Plase Select Date!", Toast.LENGTH_LONG).show()
            } else if (titleEditText.text.toString().isBlank()){
                Toast.makeText(requireContext(), "Plase Insert Title!", Toast.LENGTH_LONG).show()
            } else if (detailEditText.text.toString().isBlank()){
                Toast.makeText(requireContext(), "Plase Insert Detail!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Plase Insert!", Toast.LENGTH_LONG).show()
            }
        }

        removeButton.setOnClickListener {
            db.deleteNote(nid!!)
            findNavController().navigate(R.id.detailToNotes)
        }


    }
}