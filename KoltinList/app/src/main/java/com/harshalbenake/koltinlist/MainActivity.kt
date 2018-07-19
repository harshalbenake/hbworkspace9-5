package com.harshalbenake.koltinlist

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main Activity
 */
class MainActivity : AppCompatActivity() {
    //array variable initialized
    var listItems = arrayListOf<String>()
    //listview initialized
    private lateinit var lv_nameslist: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //smartcast - textview appearance changed without findViewById
        tv_name.text = "Enter name for adding to list"
        tv_name.setTextColor(Color.parseColor("#006500"))
        tv_name.textSize = 20F

        //editext variable using findViewById
        val et_name = findViewById<EditText>(R.id.et_name)

        lv_nameslist = findViewById<ListView>(R.id.lv_nameslist)

        //arraylist item added
        listItems.add("harshalbenake1")
        listItems.add("harshalbenake2")
        listItems.add("harshalbenake3")

        //variable value initialized and added to list
        var newName = "harshalbenake4"
        newName = "harshalbenake5"
        listItems.add(newName)

        //adapter set
        setAdapter(listItems)

        //button onclick event
        bt_add.setOnClickListener() {
            //methods called to add name
            addName("harshalbenake6")
            addName(et_name.text.toString())
        }

        //listview onitemclick event
        lv_nameslist.setOnItemClickListener { parent, view, position, id ->
            //item value retrived from positon
            var strName = parent.getItemAtPosition(position) as String
            //toast display
            Toast.makeText(this,strName,Toast.LENGTH_SHORT).show()
            //data passed to another activity
            val intent=Intent(this,DetailActivity::class.java)
            intent.putExtra("strNamePass", strName)
            startActivity(intent);
        }

    }

    override fun onPause() {
        super.onPause()
        //lifecycle method override
        println("onPause called")
    }

    /**
     * add Name
     */
    fun addName(strString: String) {
        //empty value check
        if(strString!="") {
            listItems.add(strString)
            setAdapter(listItems)
        }else{
            Toast.makeText(this,"No Name Added",Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * set Adapter
     */
    fun setAdapter(items: ArrayList<String>) {
        //CustomBaseAdapter class parameterized constructor called
        var adapter = CustomBaseAdapter(items, this)
        //adapter set to listview
        lv_nameslist.adapter = adapter
    }
}
