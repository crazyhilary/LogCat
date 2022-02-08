package com.hilary.lib.logcat

import android.annotation.SuppressLint
import com.hilary.lib.logcat.db.AppDatabase
import com.hilary.lib.logcat.db.LogCatItem
import com.hilary.lib.logcat.utilities.ContextHelper
import com.hilary.lib.logcat.utilities.DATE_FORMAT_SSS
import com.hilary.lib.logcat.utilities.LOG_PATTERN
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Created by chenkai on 2022/1/2
 *
 * 读取日志帮助类
 *
 *
 **/
class ReadLogHelper private constructor(){

    private val sLogcatPattern = Pattern.compile(LOG_PATTERN)
    @SuppressLint("SimpleDateFormat")
    private val sDataFormat = SimpleDateFormat(DATE_FORMAT_SSS)

    private val mLogCatListenerList : MutableList<LogCatListener> = mutableListOf()
    private var mReading: Boolean = false

    companion object {
        val INSTANCE: ReadLogHelper by lazy( mode = LazyThreadSafetyMode.SYNCHRONIZED ) {
            ReadLogHelper()
        }
    }

    /**
     * 启动日志读取
     */
    fun startRead() {
        if (mReading) {
            return
        }
        mReading = true
        Thread(this::readLog).start()
    }

    /**
     * 停止日志读取
     */
    fun stopRead() {
        mReading = false
    }

    private fun readLog() {
        AppDatabase.getInstance(ContextHelper.getContext()).logCatItemDao().clear()
        var reader :BufferedReader? = null
        try {
            val process = ProcessBuilder("logcat", "-v","year").start()
            reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (mReading) {
                line = reader.readLine()
                if (line == null) break
                val matcher = sLogcatPattern.matcher(line)
                if (!matcher.find()) continue
                val timeText = matcher.group(1)
                val pidText = matcher.group(2)
                val tidText = matcher.group(3)
                val tagText = matcher.group(4)
                val prefixText = matcher.group(5)
                val contentText = matcher.group(6)
                var parse :Date? = Date()
                try {
                    parse = sDataFormat.parse(timeText ?: "")
                } catch (e : ParseException) { }
                val item = LogCatItem(
                    0,
                    parse?.time ?: 0,
                    pidText?.toInt() ?: 0,
                    tidText?.toInt() ?: 0,
                    tagText?: "",
                    prefixText?: "",
                    contentText?: "")
                AppDatabase.getInstance(ContextHelper.getContext()).logCatItemDao().insert(item)
                mLogCatListenerList.forEach{
                    it.onDataChange(item)
                }
            }
            stopRead()
        } catch (e: Exception) {
            e.printStackTrace()
            stopRead()
        }
        try {
            reader?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

/**
 * Log日志读取监听器
 */
interface LogCatListener {
    fun onDataChange(item: LogCatItem)
}
