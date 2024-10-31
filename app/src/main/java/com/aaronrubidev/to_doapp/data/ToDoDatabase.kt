package com.aaronrubidev.to_doapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aaronrubidev.to_doapp.data.models.ToDoData

@Database(entities = [ToDoData::class], version = 2, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun ToDoDao(): ToDoDao

    companion object {

        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase {
            // It will check if the instance exists into the project
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            // Avoid multiple instance creation into a multiple threads
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "todo_database"
                ).build()
                // Ensure only one thread can be called inside our synchronized block
                INSTANCE = instance
                return instance
            }
        }
    }
}