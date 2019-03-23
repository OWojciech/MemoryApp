package com.example.prm_projekt1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for(i in 0..(buttonBox.childCount-1)){
            buttonBox.getChildAt(i).apply {
                setOnClickListener{
                    var gridSize = 4 + i*2
                startActivity( Intent(this@MainActivity, GameActivity(gridSize,gridSize)::class.java))
                }
            }
        }
    }

}
