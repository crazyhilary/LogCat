package com.hilary.lib.logcat.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.hilary.lib.logcat.utilities.DATABASE_NAME
import com.hilary.lib.logcat.utilities.LOG_DATA_FILENAME
import com.hilary.lib.logcat.workers.SeedDatabaseWorker
import com.hilary.lib.logcat.workers.SeedDatabaseWorker.Companion.KEY_FILENAME

@Database(entities = [LogCatItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun logCatItemDao(): LogCatItemDao

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                            .setInputData(workDataOf(KEY_FILENAME to LOG_DATA_FILENAME))
                            .build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                }).build()
        }
    }


}