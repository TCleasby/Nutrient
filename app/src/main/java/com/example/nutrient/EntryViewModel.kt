package com.example.nutrient

import android.app.Application
import android.util.Log
import android.view.animation.Transformation
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import java.time.LocalDate


class EntryViewModel(private val app: Application): AndroidViewModel(app) {
    private val token: String? = SessionManager.getAuthToken(app)
    var entryListLiveData: LiveData<List<EntryItem>> = MutableLiveData()

    private val entryFetcher = EntryFetcher()
    private val mutableSelectedDate = MutableLiveData<String>()

    init {
        mutableSelectedDate.value = LocalDate.now().toString()

        entryListLiveData = Transformations.switchMap(mutableSelectedDate) {
            selectedDate -> entryFetcher.getEntries(token, selectedDate)
        }
    }

    fun getEntriesForDate(selectedDate : String) {
        mutableSelectedDate.value = selectedDate
    }
}