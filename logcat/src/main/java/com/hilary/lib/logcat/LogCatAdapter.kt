package com.hilary.lib.logcat

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hilary.lib.logcat.data.ViewLogCat

/**
 * Created by chenkai on 2022/1/2
 *
 **/
class LogCatAdapter : RecyclerView.Adapter<LogCatViewHolder>() {
    private val mList: ArrayList<ViewLogCat> = arrayListOf()

    private val colorArray = mutableMapOf(
        "V" to Color.parseColor("#D8D8D8"),
        "D" to Color.parseColor("#0070BB"),
        "I" to Color.parseColor("#48BB31"),
        "W" to Color.parseColor("#BBBB23"),
        "E" to Color.parseColor("#FF0006"),
        "A" to Color.parseColor("#8F0005"),
        "F" to Color.parseColor("#18FFFF")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogCatViewHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_logcat_item, parent, false)
        return LogCatViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: LogCatViewHolder, position: Int) {
        val item = mList[position]
        val topStr = "${item.time} ${item.pid} ${item.tid} ${item.tag}"
        holder.topView.text = topStr
        holder.contentView.text = item.content

        var color: Int? = colorArray[item.priority]
        color = color?: Color.parseColor("#18FFFF")
        holder.topView.setTextColor(color)
        holder.contentView.setTextColor(color)

    }

    override fun getItemCount(): Int = mList.size

    fun addItem(item: ViewLogCat) {
        mList.add(0, item)
        notifyItemInserted(0)
        println("##size = ${mList.size}###")
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setValues(items: List<ViewLogCat>) {
        mList.clear()
        mList.addAll(items)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: ArrayList<ViewLogCat>) {
        mList.addAll(items)
        notifyDataSetChanged()
        println("#####notifyDataSetChanged######${mList.size}")
    }
}

class LogCatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var topView: TextView = itemView.findViewById(R.id.topView)
    var contentView: TextView = itemView.findViewById(R.id.contentView)
}