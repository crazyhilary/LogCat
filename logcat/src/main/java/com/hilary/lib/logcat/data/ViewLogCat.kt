package com.hilary.lib.logcat.data

data class ViewLogCat(
    var id: Int = 0,
    var time: String,
    var pid: Int,
    var tid: Int,
    var priority: String,
    var tag: String,
    var content: String
)
