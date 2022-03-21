package com.rui.kotlin_mvvm

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import kotlinx.coroutines.*
import org.junit.Test

class CoroutineTest {

    lateinit var imageView: ImageView
    lateinit var context: Context

    @Test
    fun test1() = runBlocking {
        launch(Dispatchers.Main) {//主线程调用
            println("rui currentThread.1 : ${Thread.currentThread().name}")
            val image = suspendingGetImage("id")//io线程去获取图片
            //...
            //可以定义多个suspend方法，组成所需要数据
            imageView.setImageBitmap(image)//切回主线程中设置ui
            println("rui image : $image")
        }
    }


    private suspend fun suspendingGetImage(imageId: String): Bitmap {
        return withContext(Dispatchers.IO) {
            getImage(imageId)
        }
    }

    private suspend fun getImage(imageId: String): Bitmap {
        println("rui currentThread.2 : ${Thread.currentThread().name}")
        println("rui imageId : $imageId")
        delay(1000)
        return Bitmap.createBitmap(1, 2, Bitmap.Config.ARGB_8888)
    }

}