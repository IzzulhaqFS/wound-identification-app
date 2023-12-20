package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.api

import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImagePredictRepository {
    fun predict(image: MultipartBody.Part): String {
        var woundType: String = "Predict Failed"
        val client = ApiConfig.getApiService().predict(image)
        client.enqueue(object : Callback<ImagePredictResponse> {
            override fun onResponse(
                call: Call<ImagePredictResponse>,
                response: Response<ImagePredictResponse>
            ) {
                if (response.isSuccessful) {
                    Log.e(TAG, "onResponse: ${response.message()}")
                    woundType = response.body()?.wound.toString()
                }
                else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ImagePredictResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })

        return woundType
    }

    companion object {
        private const val TAG = "ImagePredictRepository"
    }
}