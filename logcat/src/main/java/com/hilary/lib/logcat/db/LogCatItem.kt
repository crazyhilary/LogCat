package com.hilary.lib.logcat.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 日志数据Model
 */
const val TABLE_NAME_LOG_CAT_ITEM = "logCatItem"
@Entity(tableName = TABLE_NAME_LOG_CAT_ITEM,
    indices = [Index(value = ["timestamp", "pid", "tid", "priority", "tag"])]
)
data class LogCatItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var timestamp: Long,
    var pid: Int,
    var tid: Int,
    var priority: String,
    var tag: String,
    var content: String)
