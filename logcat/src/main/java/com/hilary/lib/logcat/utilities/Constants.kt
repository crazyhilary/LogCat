package com.hilary.lib.logcat.utilities

const val DATABASE_NAME = "logCat-db"
const val LOG_DATA_FILENAME = "logCat.json"

/**
 * 日志Log正则
 */
const val LOG_PATTERN = "([0-9^-]+-[0-9^ ]+ [0-9^:]+:[0-9^:]+\\.[0-9]+) +([0-9]+) +([0-9]+) ([VDIWEF]) ([^ ]*) *: (.*)"

/**
 * 时间格式模板
 */
const val DATE_FORMAT_SSS = "yyyy-MM-dd HH:mm:ss.SSS"
/**
 * 时间格式模板
 */
const val DATE_FORMAT_SS = "yyyy-MM-dd HH:mm:ss"