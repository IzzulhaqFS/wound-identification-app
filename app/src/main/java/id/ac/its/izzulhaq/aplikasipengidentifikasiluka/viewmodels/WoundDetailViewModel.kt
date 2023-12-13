package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.database.WoundRepository
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.models.Wound

class WoundDetailViewModel(application: Application) : ViewModel() {
    private val woundRepository: WoundRepository = WoundRepository(application)

    fun getById(id: Int) = woundRepository.getById(id)

    fun delete(wound: Wound) = woundRepository.delete(wound)
}