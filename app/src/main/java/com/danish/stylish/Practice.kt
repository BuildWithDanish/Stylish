package com.danish.stylish

open class Danish<T>() {

    fun dan(a: String) {
        print(a)
    }
}

class Practice : Danish<Van>() {

}

class Van() {
    fun a(){
        print("a")

    }
}