package com.harshalbenake.koltinlist

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * Custom Base Adapter
 */
class CustomBaseAdapter(list: ArrayList<String>, context: Context) : BaseAdapter() {
    //LayoutInflater initialized
    val mInflater: LayoutInflater = LayoutInflater.from(context)
    //list passed from mainactivity copyied to locallist
    var localList = list

    override fun getCount(): Int {
        return localList.size
    }

    override fun getItem(position: Int): Any {
        return localList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.rowitem_name, parent, false)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.tv_rowitemname.setTextColor(Color.parseColor("#654657"))
        viewHolder.tv_rowitemname.textSize = 20F
        viewHolder.tv_rowitemname.text = localList.get(position).toString()
        return view
    }

    /**
     * View Holder class
     */
    private class ViewHolder(row: View) {
        var tv_rowitemname: TextView
        //view initialized using findViewById
        init {
            this.tv_rowitemname = row.findViewById<TextView>(R.id.tv_rowitemname)
        }
    }
}