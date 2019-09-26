package com.rui.kotlin_mvvm

import org.junit.Test

/**
 *Created by rui on 2019/9/26
委托模式是一个很有用的模式，它可以用来从类中抽取出主要负责的部分。委托模
式是Kotlin原生支持的，所以它避免了我们需要去调用委托对象。委托者只需要指
定实现的接口的实例。
 */

interface CanFly {
    fun fly()
}

class AnimalWithWings : CanFly {
    val wings: Wings = Wings()
    override fun fly() = wings.move()
}

class BirdA(f: CanFly) : CanFly by f

class DelegateTest {
    @Test
    fun test() {
        val birdWithWings = BirdA(AnimalWithWings())
        birdWithWings.fly()
    }
}