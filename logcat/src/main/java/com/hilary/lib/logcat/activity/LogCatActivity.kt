package com.hilary.lib.logcat.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hilary.lib.logcat.LogCatAdapter
import com.hilary.lib.logcat.R
import com.hilary.lib.logcat.data.ViewLogCat
import com.hilary.lib.logcat.db.LogCatViewModel
import com.hilary.lib.logcat.launchLogCatService
import java.util.*

const val REQUEST_FILTER_CODE = 1000

class LogCatActivity : AppCompatActivity() {
    private var mRecyclerView: RecyclerView? = null

    private var mAdapter: LogCatAdapter = LogCatAdapter()

    private var mLogCatViewModel :LogCatViewModel? = null

    private var mPostData = true

    private val mHandler = Handler(Looper.getMainLooper())

    private val mObserver = Observer<List<ViewLogCat>> {
        println("#######执行完成######${it.size}")
        mPostData = false
        mAdapter.setValues(it)
    }

    private val mTimer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("###onCreate####")
        setContentView(R.layout.activity_log_cat)
        initToolbar()
        mRecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        mRecyclerView!!.layoutManager = layoutManager
        mRecyclerView?.adapter = mAdapter

        launchLogCatService(this)

        mLogCatViewModel = ViewModelProvider(this)[LogCatViewModel::class.java]

        mTimer.schedule(object : TimerTask(){
            override fun run() {
                mHandler.post { getNextData() }
            }
        }, 1000L, 1000L)
        mLogCatViewModel!!.mLiveData.observe(this, mObserver)
        getNextData()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        println("###onCreateOptionsMenu####")
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuFilter) {
            launcherFilterActivity(this, REQUEST_FILTER_CODE)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        println("###onCreateContextMenu####")
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onMenuOpened(featureId: Int, menu: Menu): Boolean {
        println("####onMenuOpened####")
        return super.onMenuOpened(featureId, menu)
    }

    private fun getNextData() {
        mLogCatViewModel?.getLogCatNextList()
    }

    override fun onDestroy() {
        super.onDestroy()
        mTimer.cancel()
    }

}
