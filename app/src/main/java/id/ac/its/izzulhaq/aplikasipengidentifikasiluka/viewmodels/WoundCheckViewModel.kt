package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.database.WoundRepository
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.models.Wound

class WoundCheckViewModel(application: Application) : ViewModel() {
    private val woundRepository: WoundRepository = WoundRepository(application)

    fun insert(wound: Wound) = woundRepository.insert(wound)
}