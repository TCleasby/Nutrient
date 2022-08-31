package com.example.nutrient

import android.content.Context
import android.os.Bundle
import android.se.omapi.Session
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


class singleEntryFragment(entry: EntryItem) : Fragment() {

    private var singleEntryItem: EntryItem = entry
    private lateinit var entryNameTextView: TextView
    private lateinit var entryServingSizeTextView: TextView
    private lateinit var deleteButton: Button
    private val entryFetcher = EntryFetcher()
    private var callbacks: NavCallbacks? = null

    var nestedSingleEntryFragment = NestedSingleEntryFragment(entry.nutrients)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as NavCallbacks?
    }

        override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_single_entry, container, false)
        entryNameTextView = view.findViewById(R.id.textSingleEntry)
        entryServingSizeTextView = view.findViewById(R.id.textServingSizeEntry)
        deleteButton = view.findViewById(R.id.deleteButton)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var servingSize: String

        servingSize = singleEntryItem.attributes.servingSize.toString().plus(" ").plus(singleEntryItem.attributes.servingSizeUnit)

        if(singleEntryItem.attributes.householdServingFullText != null){
            servingSize = servingSize.plus(" / ").plus(singleEntryItem.attributes.householdServingFullText)
        }

        entryServingSizeTextView.text = servingSize
        entryNameTextView.text = singleEntryItem.attributes.name

        deleteButton.setOnClickListener{
            this.activity.let { it2 -> entryFetcher.deleteEntry(it2?.let { it1 -> SessionManager.getAuthToken(it1) }, singleEntryItem) }
            Log.d("deleteButton", "Past this.activity")
            callbacks?.onEntryChanged()
        }

        childFragmentManager.beginTransaction().apply {
            replace(R.id.flNestedSingleEntryLayout, nestedSingleEntryFragment)
            commit()
        }
    }
}