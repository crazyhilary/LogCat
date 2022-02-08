package com.hilary.lib.logcat.utilities

import android.annotation.SuppressLint
import android.content.Context

class ContextHelper {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mContext :Context? = null

        fun getContext(): Context {
            return mContext!!
        }

        fun initContextHelper(context: Context) {
            mContext = context
        }
    }

}