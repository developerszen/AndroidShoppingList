package com.example.shoppinglist

class Item {
    var id: Long = 0
    var title: String? = null
    var done: Int? = null

    constructor(title: String) {
        this.title = title
    }

    constructor(id: Long, title: String, done: Int) {
        this.id = id
        this.title = title
        this.done = done
    }
}