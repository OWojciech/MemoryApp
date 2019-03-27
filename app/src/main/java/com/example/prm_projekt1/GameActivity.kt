package com.example.prm_projekt1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TableRow
import kotlinx.android.synthetic.main.activity_game.*
import kotlin.concurrent.thread

class GameActivity : AppCompatActivity() {

    private val memoryTable by lazy { ArrayList<Int>() }
    private val savedLetters by lazy { ArrayList<ImageButton>() }
    private val buttonList by lazy { ArrayList<ImageButton>() }
    private var tableSize: Int = 0
    private var sum: Int = 0
    private var rows: Int = 0
    private var columns: Int = 0
    private var count: Int = 0
    private var minutes: Int = 0
    private var seconds: Int = 0
    private val handler by lazy { Handler() }
    private val myImageLibrary = MyImageLibrary()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        tableLayout
        tableLayout.apply {
            isShrinkAllColumns = true

        }
        functionButtons()

        rows = intent.getIntExtra("rows", 5)
        columns = intent.getIntExtra("columns", 5)
        tableSize = rows * columns
        initMemTable(tableSize)
        shuffleTable(tableSize)
        createTable(rows, columns)
        startTimer()
    }

    private fun startTimer() {
        handler.apply {
            val runnable = object : Runnable {
                override fun run() {
                    count++
                    minutes = count / 60
                    seconds = count % 60
                    val s = "$minutes : $seconds"
                    timer.text = s
                    postDelayed(this, 1000)
                }
            }
            postDelayed(runnable, 1000)
        }
    }

    private fun stopTimer() {
        handler.removeCallbacksAndMessages(null)
    }

    private fun initMemTable(size: Int) {
        var ascii = 0
        while (memoryTable.size < size) {
            memoryTable.add(myImageLibrary.getValue(ascii)!!)
            memoryTable.add(myImageLibrary.getValue(ascii)!!)
            ascii++
            if (ascii == 12) {//113
                ascii = 0
            }
        }
    }

    private fun shuffleTable(count: Int) {
        var tmp = count
        while (tmp > 0) {
            val i = Math.floor(Math.random() * memoryTable.size)
            val j = Math.floor(Math.random() * memoryTable.size)
            val temp = memoryTable[j.toInt()]
            memoryTable[j.toInt()] = memoryTable[i.toInt()]
            memoryTable[i.toInt()] = temp
            tmp--
        }
    }

    private fun functionButtons() {
        tak.setOnClickListener {
            val intent = Intent(this@GameActivity, GameActivity::class.java)
            intent.putExtra("rows", rows)
            intent.putExtra("columns", columns)
            this.finish()
            startActivity(intent)
        }
        nie.setOnClickListener {
            this.finish()
        }

    }

    private fun createTable(rows: Int, cols: Int) {
        for (i in 0 until rows) {
            val row = TableRow(this)
            row.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            for (j in 0 until cols) {
                row.addView(ImageButton(this).apply {
                    setImageResource(memoryTable[((i * cols + j) % memoryTable.size)])
                    imageAlpha = 0
                    tag = memoryTable[((i * cols + j) % memoryTable.size)]
                    layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                    setOnClickListener {
                        imageAlpha = 255
                        savedLetters.add(this)
                        thread {
                            runOnUiThread {
                                checkButtons()
                            }
                        }
                    }

                    buttonList.add(this)
                })
            }
            tableLayout.addView(row)
        }


    }

    private fun checkButtons() {
        if (savedLetters.size == 2 && (savedLetters[0].tag != savedLetters[1].tag || savedLetters[0] == savedLetters[1])
        ) {
            for(e in buttonList){
                e.isClickable = false
            }
            Handler().postDelayed({

                savedLetters[0].imageAlpha = 0
                savedLetters[1].imageAlpha = 0
                savedLetters.clear()

                for(e in buttonList){
                    e.isClickable = true
                }
            }, 1000)


        } else if (savedLetters.size == 2 && savedLetters[0].tag == savedLetters[1].tag) {
            savedLetters[0].isEnabled = false
            savedLetters[1].isEnabled = false
            savedLetters[0].imageAlpha = 140
            savedLetters[1].imageAlpha = 140
            sum += 2
            if (sum == tableSize) {
                stopTimer()
                showWinnerUI()
            }
            savedLetters.clear()
        }
    }

    private fun showWinnerUI() {
        tak.visibility = View.VISIBLE
        nie.visibility = View.VISIBLE
        pytanko.visibility = View.VISIBLE
        winner.visibility = View.VISIBLE
    }
}
