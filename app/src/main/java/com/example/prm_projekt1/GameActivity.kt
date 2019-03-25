package com.example.prm_projekt1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity() : AppCompatActivity() {

    val tableLayout by lazy { TableLayout(this) }
    val memoryTable by lazy { ArrayList<Char>()}
    var clickCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        tableLayout.apply {
            layoutParams = TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            isShrinkAllColumns = true
        }
        var rows = intent.getIntExtra("rows", 5)
        var columns = intent.getIntExtra("columns", 5)
        initMemTable()
        shuffleTable(rows*columns)
        createTable(rows, columns)

    }
    private fun initMemTable(){
        var ascii = 97
        var tmp = 20
        while(tmp > 0){
            memoryTable.add(ascii.toChar())
            memoryTable.add(ascii.toChar())
            ascii++
            tmp--
        }
    }

    private fun shuffleTable(count: Int){
        var tmp = count
        while(tmp > 0){
            val i = Math.floor(Math.random() * memoryTable.size)
            val j = Math.floor(Math.random() * memoryTable.size)
            var temp = memoryTable.get(j.toInt())
            memoryTable.set(j.toInt(), memoryTable.get(i.toInt()))
            memoryTable.set(i.toInt(), temp)
            tmp--
        }
    }
    private fun createTable(rows: Int, cols: Int) {

        for (i in 0 until rows) {

            val row = TableRow(this)
            row.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

            for (j in 0 until cols) {
                row.addView(Button(this).apply {
                    layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT)
                    setOnClickListener{
                        text = memoryTable.get((i*cols +j)%memoryTable.size).toString()
                    }
                })
            }
            tableLayout.addView(row)
        }
        gameLayout.addView(tableLayout)
    }
}
