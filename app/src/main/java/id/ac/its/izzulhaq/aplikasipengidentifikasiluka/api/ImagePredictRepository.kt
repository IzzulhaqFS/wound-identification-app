package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImagePredictRepository {
    private var woundType: String = "Predict Failed"
    private val _isProcessing = MutableLiveData<Boolean>()

    fun getWoundType(): String = woundType

    fun isProcessing(): LiveData<Boolean> = _isProcessing

    fun predict(image: MultipartBody.Part) {
        //var woundType: String = "Predict Failed"
        _isProcessing.value = true
        val client = ApiConfig.getApiService().predict(image)
        client.enqueue(object : Callback<ImagePredictResponse> {
            override fun onResponse(
                call: Call<ImagePredictResponse>,
                response: Response<ImagePredictResponse>
            ) {
                if (response.isSuccessful) {
                    _isProcessing.value = false
                    woundType = response.body()?.wound.toString()
                    Log.e(TAG, "onResponse: ${response.message()}, $woundType")
                }
                else {
                    Log.e(TAG, "onFailure: ${response.message()}, $woundType")
                }
            }

            override fun onFailure(call: Call<ImagePredictResponse>, t: Throwable) {
                _isProcessing.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "ImagePredictRepository"
    }
}