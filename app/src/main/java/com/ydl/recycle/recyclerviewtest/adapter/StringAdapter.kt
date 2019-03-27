package com.ydl.recycle.recyclerviewtest.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ydl.recycle.recyclerviewtest.R

/**
 * @author <a href="https://www.jianshu.com/u/c1e5310dd724">xujian</a>
 * @描述:
 * @Copyright Copyright (c) 2019
 * @Company 壹点灵
 * @date 2019/03/26
 */
class StringAdapter(var mContext: Context, var list: ArrayList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        return StringHolder(LayoutInflater.from(mContext).inflate(R.layout.string_item, p0, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StringHolder -> {
                holder.txt!!.text = list[position]
            }
        }
    }

    inner class StringHolder(view: View): RecyclerView.ViewHolder(view) {

        var txt: TextView? = null

        init {
            txt = view.findViewById(R.id.txt)
        }

    }


}