package jp.tm.fragmentsample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "detail")
    val detail: String,

    @ColumnInfo(name = "send_flag")
    val sendFlag: Boolean

)