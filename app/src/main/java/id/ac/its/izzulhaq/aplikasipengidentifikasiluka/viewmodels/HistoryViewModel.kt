package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.database.WoundRepository
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.models.Wound

class HistoryViewModel(application: Application) : ViewModel() {
    private val woundRepository: WoundRepository = WoundRepository(application)

    fun getAll(): LiveData<List<Wound>> = woundRepository.getAll()
}