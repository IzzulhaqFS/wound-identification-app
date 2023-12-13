package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.models.Wound

@Dao
interface WoundDao {
    @Query("SELECT * FROM wound")
    fun getAll(): LiveData<List<Wound>>

    @Query("SELECT * FROM wound WHERE id = :id")
    fun getById(id: Int): Wound

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWound(wound: Wound)

    @Delete
    suspend fun deleteWound(wound: Wound)
}