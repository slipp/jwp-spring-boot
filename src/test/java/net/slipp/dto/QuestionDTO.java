package net.slipp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDTO {
	private String title;
	private String contents;

	public String getTitle() {
		return title;
	}

	public QuestionDTO setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getContents() {
		return contents;
	}

	public QuestionDTO setContents(String contents) {
		this.contents = contents;
		return this;
	}
}
