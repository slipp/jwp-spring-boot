package net.slipp.dto

data class QuestionsDto(var contents: List<QuestionDto>) {
    constructor(): this(emptyList())

    fun size(): Int {
        return contents.size
    }
}
