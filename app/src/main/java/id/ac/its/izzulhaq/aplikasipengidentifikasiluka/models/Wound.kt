package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "wound")
data class Wound(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "photo_uri")
    val photoUri: String? = null,

    @ColumnInfo(name = "check_date")
    val checkDate: String,

    @ColumnInfo(name = "wound_type")
    val woundType: String? = null
)
