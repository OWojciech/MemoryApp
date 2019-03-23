package com.example.prm_projekt1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity() : AppCompatActivity() {
    var rows = 0
    var columns = 0
    val tableLayout by lazy { TableLayout(this) }


    constructor(rows: Int, columns: Int) : this() {
        this.rows = rows
        this.columns = columns
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        createTable(rows, columns)
    }

    fun createTable(rows: Int, cols: Int) {

        for (i in 0 until rows) {

            val row = TableRow(this)
            row.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

            for (j in 0 until cols) {
                row.addView(Button(this).apply {
                    layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT)
                    text = "R $i C $j"
                })
            }
            tableLayout.addView(row)
        }
        gameLayout.addView(tableLayout)
    }
}
