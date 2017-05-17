
## Intro to Kotlin

![LOGO](https://kotlinlang.org/assets/images/open-graph/kotlin_250x250.png)

A practical intro to kotlin for java devs.

---

## What is it?

- developed by jetbrains
- more consice than java
- interoperable
- pragmatic
- safe

---

## Where can I use it?

- jvm
- android
- javascript
- native targets

---

## Enough chit-chat, let's get into the details

---

#### val/var

```kotlin
// val == 'final'
val aString = "really, a string" // <- semicolons unnecessary

// var == 'not final'
var anotherString = aString
```

---

#### type inference

```kotlin
val anInt: Int = 42
val aCompileError: String = 98L
```

---

#### function structure

```kotlin
fun add(left: Int, right: Int): Int {
    return left + right
}
```

---

#### named & default args

```kotlin
// note optional return type
fun sayHi(greeting: String = "Howdy",
          name: String = "Honored Guest") {
    println("$greeting, $name!")
}

fun main(args: Array<String>) {
    sayHi()
    sayHi(name = "Shane")
    // order doesn't matter with named args
    sayHi(name = "Dave",
            greeting = "Welcome to the conference room")
}
```

- `fun sayBye(name = "Builder Pattern")`      <!-- .element: class="fragment" -->

---

## expression returns

```kotlin
fun getServiceResult(condition: Boolean): String {
    val result = if (condition) {
        "Service OK"
    } else {
        "Service Failure"
    }
    return result
}
```

+++

## expression returns

```kotlin
// or even better:
fun getServiceResult(condition: Boolean): String {
    return if (condition) {
        "Service OK"
    } else {
        "Service Failure"
    }
}
```

+++

## expression returns

```kotlin
// whaaat
fun getServiceResult(condition: Boolean) = if (condition) {
    "Service OK"
} else {
    "Service Failure"
}
```