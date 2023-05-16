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
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.alierdemalkoc.noteapp.R
import com.alierdemalkoc.noteapp.databinding.FragmentAddBinding
import com.alierdemalkoc.noteapp.databinding.FragmentNotesBinding
import com.alierdemalkoc.noteapp.db.DB
import java.util.*

class AddFragment : Fragment() {
    private var _binding : FragmentAddBinding? = null
    private val binding get() = _binding!!

    lateinit var saveButton: Button
    lateinit var dateButton: Button
    lateinit var titleText: EditText
    lateinit var detailText: EditText
    var selectDate = ""

    lateinit var db: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addButton = activity?.findViewById<Button>(R.id.adButton)
        addButton!!.visibility = View.GONE
        dateButton = binding.dateButton
        saveButton = binding.saveButton
        titleText = binding.titleText
        detailText = binding.detailText
        db = DB(requireContext())
        val calender = Calendar.getInstance()
        dateButton.setOnClickListener(View.OnClickListener {
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
                },
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH),
            )
            datePickerDialog.show()
        })

        saveButton.setOnClickListener {
            if (selectDate != "" && detailText.text.toString() != "" && titleText.text.toString() != "") {
                db.addNote(titleText.text.toString(), detailText.text.toString(), selectDate)
                selectDate = ""
                findNavController().navigate(R.id.addToNotes)
            } else if (selectDate.equals("")) {
                Toast.makeText(requireContext(), "Plase Select Date!", Toast.LENGTH_LONG).show()
            } else if (titleText.text.toString().isBlank()){
                Toast.makeText(requireContext(), "Plase Insert Title!", Toast.LENGTH_LONG).show()
            } else if (detailText.text.toString().isBlank()){
                Toast.makeText(requireContext(), "Plase Insert Detail!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Plase Insert!", Toast.LENGTH_LONG).show()
            }
        }
    }
}