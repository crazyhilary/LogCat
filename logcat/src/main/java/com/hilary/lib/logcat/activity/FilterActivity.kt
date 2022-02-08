package com.hilary.lib.logcat.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hilary.lib.logcat.R

class FilterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
    }
}

/**
 * 启动过滤器Activity
 */
fun launcherFilterActivity(activity: Activity, requestCode: Int) {
    val intent = Intent(activity, FilterActivity::class.java)
    activity.startActivityForResult(intent, requestCode)
}