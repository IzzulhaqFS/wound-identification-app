package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.api

import retrofit2.Call
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("/api/upload")
    fun predict(
        @Part
        file: MultipartBody.Part
    ): Call<ImagePredictResponse>
}