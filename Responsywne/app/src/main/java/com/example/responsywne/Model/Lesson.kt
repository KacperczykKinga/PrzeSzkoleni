package com.example.responsywne.Model

class Lesson(
    var id: Int = 0,
    var title: String = "",
    var photo: String = "",
    var listOfTopics: MutableList<String> = mutableListOf()

): Comparable<Lesson> {
    override fun compareTo(other: Lesson): Int {
        return this.id.compareTo(other.id)
    }

}