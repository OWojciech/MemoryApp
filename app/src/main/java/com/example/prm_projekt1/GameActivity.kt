package com.example.prm_projekt1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableRow
import kotlinx.android.synthetic.main.activity_game.*
import kotlin.concurrent.thread

class GameActivity : AppCompatActivity() {

    private val memoryTable by lazy { ArrayList<Char>() }
    private val savedLetters by lazy { ArrayList<Button>() }
    private var tableSize: Int = 0
    private var sum: Int = 0
    private var rows: Int = 0
    private var columns: Int = 0
    private var count: Int = 0
    private var minutes: Int = 0
    private var seconds: Int = 0
    private val handler by lazy { Handler() }
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
        var ascii = 97
        while (memoryTable.size < size) {
            memoryTable.add(ascii.toChar())
            memoryTable.add(ascii.toChar())
            ascii++
            if (ascii == 113) {
                ascii = 97
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
                row.addView(Button(this).apply {
                    layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                    setOnClickListener {
                        text = memoryTable[((i * cols + j) % memoryTable.size)].toString()
                        savedLetters.add(this)
                        thread {
                            runOnUiThread {
                                checkButtons()
                            }
                        }
                    }
                })
            }
            tableLayout.addView(row)
        }


    }

    private fun checkButtons() {
        if (savedLetters.size == 2 && (savedLetters[0].text != savedLetters[1].text || savedLetters[0] == savedLetters[1])
        ) {
            Handler().postDelayed({
                savedLetters[0].text = null
                savedLetters[1].text = null
                savedLetters.clear()
            }, 1000)

        } else if (savedLetters.size == 2 && savedLetters[0].text == savedLetters[1].text) {
            savedLetters[0].isEnabled = false
            savedLetters[1].isEnabled = false
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
