package jp.tm.fragmentsample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 2, exportSchema = false)
abstract class TodoRoomDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    companion object {
        @Volatile
        private var INSTANCE: TodoRoomDatabase? = null
        fun getDatabase(context: Context): TodoRoomDatabase {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                TodoRoomDatabase::class.java,
                "todo_database"
            )
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            return instance
        }
    }

}