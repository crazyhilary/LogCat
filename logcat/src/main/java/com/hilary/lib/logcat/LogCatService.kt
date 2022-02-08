package com.hilary.lib.logcat

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder

/**
 * Created by chenkai on 2022/1/4
 *
 **/
class LogCatService : Service() {

    @SuppressLint("WrongConstant")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ReadLogHelper.INSTANCE.startRead()
        return super.onStartCommand(intent,  START_STICKY_COMPATIBILITY, startId)
    }

    override fun onBind(p0: Intent?): IBinder? { return null }

    override fun onDestroy() {
        super.onDestroy()
        ReadLogHelper.INSTANCE.stopRead()
    }
}

/**
 * 启动LogCatService
 */
fun launchLogCatService(context: Context) {
    context.startService(Intent(context, LogCatService::class.java))
}