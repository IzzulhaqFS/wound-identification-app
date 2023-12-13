package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.database

import android.app.Application
import androidx.lifecycle.LiveData
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.models.Wound
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WoundRepository(application: Application) {
    private val woundDao: WoundDao

    init {
        val db = WoundDatabase.getInstance(application)
        woundDao = db.woundDao()
    }

    fun getAll(): LiveData<List<Wound>> = woundDao.getAll()

    fun getById(id: Int): LiveData<Wound> = woundDao.getById(id)

    fun insert(wound: Wound) {
        CoroutineScope(Dispatchers.IO).launch {
            woundDao.insertWound(wound)
        }
    }

    fun delete(wound: Wound) {
        CoroutineScope(Dispatchers.IO).launch {
            woundDao.deleteWound(wound)
        }
    }
}