package net.slipp.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class QuestionDTO(var id: Long, var title: String, var contents: String) {
    constructor(): this(0, "", "")

    constructor(title: String, contents: String): this(0, title, contents)
}
