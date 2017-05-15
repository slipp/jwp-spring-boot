package net.slipp.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class AnswerDto(var id: Long, var contents: String) {
    constructor(): this(0, "")

    constructor(contents: String): this(0, contents)
}
