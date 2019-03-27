package com.example.prm_projekt1

class MyImageLibrary{
    val list = ArrayList<Int>()

    init{
        list.add(R.drawable.a)
        list.add(R.drawable.b)
        list.add(R.drawable.c)
        list.add(R.drawable.d)
        list.add(R.drawable.e)
        list.add(R.drawable.f)
        list.add(R.drawable.g)
        list.add(R.drawable.h)
        list.add(R.drawable.i)
        list.add(R.drawable.j)
        list.add(R.drawable.k)
        list.add(R.drawable.l)
        list.add(R.drawable.m)
    }

    fun getValue(index: Int): Int? {
        return list[index]
    }
}