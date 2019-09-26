package com.rui.kotlin_mvvm

/**
 *Created by rui on 2019/9/26
当两个类继承自一个接口，非常典型的是它们两者共享相同的实现。但是Java 7中
的接口只能定义行为，但是不能去实现它。
Kotlin接口在某一方面它可以实现函数。它们与类唯一的不同之处是它们是无状态
（stateless）的，所以属性需要子类去重写。类需要去负责保存接口属性的状态。
 */
class Wings {
    fun move() {
        println("---------->move")
    }
}

interface FlyingAnimal {
    val wings: Wings
    fun fly() = wings.move()
}

class Bird : FlyingAnimal {
    override val wings: Wings = Wings()

}

class Bat : FlyingAnimal {
    override val wings: Wings
        get() = Wings()
}