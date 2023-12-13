package id.ac.its.izzulhaq.aplikasipengidentifikasiluka.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.its.izzulhaq.aplikasipengidentifikasiluka.models.Wound

@Database(entities = [Wound::class], version = 1)
abstract class WoundDatabase : RoomDatabase() {
    abstract fun woundDao(): WoundDao

    companion object {
        @Volatile
        private var INSTANCE: WoundDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): WoundDatabase {
            if (INSTANCE == null) {
                synchronized(WoundDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        WoundDatabase::class.java,
                        "wound_database"
                    ).build()
                }
            }
            return INSTANCE as WoundDatabase
        }
    }
}