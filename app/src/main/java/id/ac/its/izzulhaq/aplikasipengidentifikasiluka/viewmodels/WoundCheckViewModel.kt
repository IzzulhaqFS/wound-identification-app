package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.api.ImagePredictRepository
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.database.WoundRepository
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.models.Wound
import okhttp3.MultipartBody

class WoundCheckViewModel(application: Application) : ViewModel() {
    private val woundRepository: WoundRepository = WoundRepository(application)
    private val imagePredictRepository: ImagePredictRepository = ImagePredictRepository()

    fun isProcessing(): LiveData<Boolean> = imagePredictRepository.isProcessing()

    fun insert(wound: Wound) = woundRepository.insert(wound)

    fun predict(image: MultipartBody.Part) = imagePredictRepository.predict(image)

    fun getWoundType(): String = imagePredictRepository.getWoundType()
}