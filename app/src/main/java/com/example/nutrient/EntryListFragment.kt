package com.example.nutrient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import okhttp3.internal.format
import java.text.SimpleDateFormat
import java.time.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private const val REQUEST_DATE = 0
private const val DIALOG_DATE = "DialogDate"

class EntryListFragment : Fragment(R.layout.fragment_user_food_list), DatePickerFragment.Callbacks {

    private lateinit var dateButton: Button
    private var nestedEntryListFragment = NestedEntryListFragment()
    private var callbacks: NavCallbacks? = null

    var date: String = LocalDate.now().toString()

    var formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as NavCallbacks?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dateButton = view.findViewById(R.id.dateButton)

        Log.d("Date Format", date)

        dateButton.text = LocalDate.now().format(formatter).toString()

        dateButton.setOnClickListener {
            DatePickerFragment.newInstance(Date()).apply {
                setTargetFragment(this@EntryListFragment, REQUEST_DATE)
                show(this@EntryListFragment.parentFragmentManager, DIALOG_DATE)
            }
        }

        childFragmentManager.beginTransaction().apply {
            replace(R.id.flNestedUserFoodLayout, nestedEntryListFragment)
            commit()
        }

    }

    override fun onDateSelected(date: Date) {
        dateButton.text = date.dateToString("MMMM dd, yyyy")

        this.date = date.dateToString("yyyy-MM-dd")

        callbacks?.setSelectedDate(this.date)

        nestedEntryListFragment.getEntriesForDate(this.date)

        Log.d("Updated date: ", this.date)
    }

    private fun Date.dateToString(format: String): String{
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
        return dateFormatter.format(this)
    }
}