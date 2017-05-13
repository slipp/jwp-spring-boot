package net.slipp.dto

data class QuestionsDTO(var contents: List<QuestionDTO>) {
    constructor(): this(emptyList())

    fun size(): Int {
        return contents.size
    }
}
