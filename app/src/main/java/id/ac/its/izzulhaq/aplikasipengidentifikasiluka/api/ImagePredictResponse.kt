package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.api

import com.google.gson.annotations.SerializedName

data class ImagePredictResponse(

    @field:SerializedName("wound")
    val wound: String
)
