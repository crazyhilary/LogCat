package com.hilary.lib.logcat.db

import androidx.lifecycle.LiveData
import androidx.room.*

const val TABLE = TABLE_NAME_LOG_CAT_ITEM

@Dao
interface LogCatItemDao {

    @Query("SELECT * FROM $TABLE ")
    fun getAll(): LiveData<List<LogCatItem>>

    @Query("SELECT * FROM $TABLE LIMIT :size OFFSET :offset")
    fun getByDay(offset: Int, size: Int): LiveData<List<LogCatItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<LogCatItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: LogCatItem): Long

    @Query("DELETE FROM $TABLE")
    fun clear()

}