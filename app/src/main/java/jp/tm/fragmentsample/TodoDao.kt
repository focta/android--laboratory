package jp.tm.fragmentsample

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDao {

    @Insert
    suspend fun insert(todo: Todo)

    @Query("SELECT * FROM todo")
    suspend fun getAll(): MutableList<Todo>

    // TODO deleteとかが後回し
}