package net.slipp.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import support.domain.AbstractEntity;

@Entity
public class Answer extends AbstractEntity {
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
	private User writer;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
	private Question question;
	
	@Lob
	private String contents;
	
	public Answer() {
	}
	
	public Answer(User writer, String contents) {
		this.writer = writer;
		this.contents = contents;
	}
	
	public User getWriter() {
		return writer;
	}
	
	public Question getQuestion() {
		return question;
	}
	
	public String getContents() {
		return contents;
	}
	
	public void toQuestion(Question question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "Answer [id=" + getId() + ", writer=" + writer + ", contents=" + contents + "]";
	}
}
