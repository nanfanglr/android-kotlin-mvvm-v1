package com.rui.kotlin_mvvm

import com.rui.mvvm.toDateString
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.DateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testLongToDateString() {
        println(Date().time.toDateString(DateFormat.FULL))
        println(Date().time.toDateString(DateFormat.LONG))
        println(Date().time.toDateString())
        println(Date().time.toDateString(DateFormat.SHORT))
    }
}
