package net.slipp.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Question {
	@Id
	@GeneratedValue
	private long id;

	@Column(length = 100, nullable = false)
	private String title;

	@Lob
	private String contents;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	private User writer;
	
	@OneToMany(mappedBy="question", cascade = CascadeType.ALL)
	@OrderBy("id ASC")
	private List<Answer> answers = new ArrayList<>();
	
	private LocalDateTime createDate;
	
	public Question() {
		this.createDate = LocalDateTime.now();
	}
	
	public Question(String title, String contents) {
		this.title = title;
		this.contents = contents;
		this.createDate = LocalDateTime.now();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public User getWriter() {
		return writer;
	}

	public String getFormattedCreateDate() {
		if (createDate == null) {
			return "";
		}
		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}

	public void writeBy(User loginUser) {
		this.writer = loginUser;
	}
	
	public void addAnswer(Answer answer) {
		answers.add(answer);
		answer.toQuestion(this);
	}
	
	@Override
	public String toString() {
		return "Question [id=" + id + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
	}
}
