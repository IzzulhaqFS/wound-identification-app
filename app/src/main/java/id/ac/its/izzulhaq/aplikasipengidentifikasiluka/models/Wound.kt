package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.models

import java.util.Date

data class Wound(
    val id: Int = 0,
    val photoUri: String? = null,
    val checkDate: Date,
    val woundType: String? = null
)
