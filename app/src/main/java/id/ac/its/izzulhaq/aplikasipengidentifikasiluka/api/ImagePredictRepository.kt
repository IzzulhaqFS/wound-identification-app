package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImagePredictRepository {
    private val _woundType = MutableLiveData<String>()
    private val _isProcessing = MutableLiveData<Boolean>()

    fun getWoundType(): LiveData<String> = _woundType

    fun isProcessing(): LiveData<Boolean> = _isProcessing

    fun predict(image: MultipartBody.Part) {
        _woundType.value = "Uploading..."
        _isProcessing.value = true
        val client = ApiConfig.getApiService().predict(image)
        client.enqueue(object : Callback<ImagePredictResponse> {
            override fun onResponse(
                call: Call<ImagePredictResponse>,
                response: Response<ImagePredictResponse>
            ) {
                _woundType.value = "Predicting..."
                if (response.isSuccessful) {
                    val woundType = response.body()?.wound.toString()
                    _woundType.value = woundType
                    Log.e(TAG, "onResponse: ${response.message()}, $woundType")
                    _isProcessing.value = false
                }
                else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _isProcessing.value = false
                    _woundType.value = "Failed"
                }
            }

            override fun onFailure(call: Call<ImagePredictResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                _isProcessing.value = false
                _woundType.value = "Failed"
            }

        })
    }

    companion object {
        private const val TAG = "ImagePredictRepository"
    }
}