package com.dev.colordots

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var colorDotList =ArrayList<Int>()
        colorDotList.add(resources.getColor(R.color.colorPrimary))
        colorDotList.add(resources.getColor(R.color.colorPrimaryDark))
        colorDotList.add(resources.getColor(R.color.colorAccent))
        dotMultiColor.setColorCode(colorDotList)
    }
}
