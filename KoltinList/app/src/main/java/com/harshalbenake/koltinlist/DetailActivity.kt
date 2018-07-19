package com.harshalbenake.koltinlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * Detail Activity class
 */
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        //intent data extracted
        var strName= intent.getStringExtra("strNamePass")
        tv_detailname.text=strName
    }
}
