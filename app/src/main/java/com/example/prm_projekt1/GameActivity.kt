package com.example.prm_projekt1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableRow
import kotlinx.android.synthetic.main.activity_game.*
import kotlin.concurrent.thread

class GameActivity : AppCompatActivity() {

    //private val tableLayout by lazy { TableLayout(this) }
    private val memoryTable by lazy { ArrayList<Char>()}
    private val savedLetters by lazy { ArrayList<Button>() }
    private var tableSize: Int = 0
    private var sum: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        tableLayout
        tableLayout.apply {
            //layoutParams = TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            isShrinkAllColumns = true

        }

        val rows = intent.getIntExtra("rows", 5)
        val columns = intent.getIntExtra("columns", 5)
        tableSize = rows*columns
        initMemTable(tableSize)
        shuffleTable(tableSize)
        createTable(rows, columns)
    }
    private fun initMemTable(size: Int){
        var ascii = 97
        while(memoryTable.size < size){
            memoryTable.add(ascii.toChar())
            memoryTable.add(ascii.toChar())
            ascii++
            if(ascii == 113){
                ascii = 97
            }
        }
    }

    private fun shuffleTable(count: Int){
        var tmp = count
        while(tmp > 0){
            val i = Math.floor(Math.random() * memoryTable.size)
            val j = Math.floor(Math.random() * memoryTable.size)
            val temp = memoryTable[j.toInt()]
            memoryTable[j.toInt()] = memoryTable[i.toInt()]
            memoryTable[i.toInt()] = temp
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
                        text = memoryTable[((i*cols +j)%memoryTable.size)].toString()
                        savedLetters.add(this)
                        thread {
                            runOnUiThread {
                                if (savedLetters.size == 2 && (savedLetters[0].text != savedLetters[1].text || savedLetters[0] == savedLetters[1])
                                ) {
                                    Thread.sleep(1000)
                                    savedLetters[0].text = null
                                    savedLetters[1].text = null
                                    savedLetters.clear()

                                } else if (savedLetters.size == 2 && savedLetters[0].text == savedLetters[1].text) {
                                    sum += 2
                                    if(sum == tableSize){
                                        winner.visibility = View.VISIBLE
                                    }
                                    savedLetters.clear()
                                }
                            }
                        }
                    }
                })
            }
            tableLayout.addView(row)
        }
        //gameLayout.addView(tableLayout)
    }
}
