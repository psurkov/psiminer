package data

abstract class Main {
    init {
        val a = 10
    }

    protected abstract fun abstractMethod()

    @Override
    fun overrideMethod(): String {
        return "test"
    }

    fun emptyMethod() {

    }

    fun smallMethod() {
        val a = 5
        val s = "asd"
        val t = false
    }

    fun largeMethod() {
        var mySuperVal = 0 + 123
        val myString = "asd"
        val b = true
        val a: Boolean = !b
        val hashMap = HashMap<String, Int>()
        for (i in 0..10) {
            mySuperVal += 1
            hashMap[myString] = i
            if (i > 0) {
            }
            hashMap.remove(myString)
        }
        val f = 5
    }
}