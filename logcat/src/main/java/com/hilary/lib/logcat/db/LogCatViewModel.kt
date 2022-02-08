package com.hilary.lib.logcat.db

import android.app.Application
import androidx.lifecycle.*
import com.hilary.lib.logcat.data.ViewLogCat
import java.text.SimpleDateFormat
import java.util.*

const val PAGE_SIZE = 50

class LogCatViewModel(application: Application) : AndroidViewModel(application) {

    val mLiveData = MediatorLiveData<List<ViewLogCat>>()

    private var mLogCats = mutableListOf<ViewLogCat>()

    private var mOffset = 0

    private val sDataFormat = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()
    )

    fun getLogCatNextList() {
        println("###mOffset: $mOffset##")
        val byDay = AppDatabase.getInstance(getApplication())
                .logCatItemDao().getByDay(mOffset, PAGE_SIZE)
        mLiveData.addSource(byDay) {
            it.forEach{ item->
                mLogCats.add(ViewLogCat(item.id,
                    sDataFormat.format(Date(item.timestamp)),
                    item.pid,
                    item.tid,
                    item.priority,
                    item.tag,
                    item.content
                ))
            }
            mOffset = mLogCats.size
            mLiveData.value = mLogCats
            mLiveData.removeSource(byDay)
        }
    }
}