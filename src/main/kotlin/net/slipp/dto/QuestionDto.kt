package net.slipp.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import javax.validation.constraints.Size

@JsonIgnoreProperties(ignoreUnknown = true)
data class QuestionDto(var id: Long,

                       @field:Size(min = 3, max = 100)
                       var title: String,
                    
                       @field:Size(min = 3)
                       var contents: String) {
    constructor() : this(0, "", "")

    constructor(title: String, contents: String) : this(0, title, contents)
}
