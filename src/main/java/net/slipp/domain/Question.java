package net.slipp.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

import net.slipp.CannotDeleteException;
import net.slipp.UnAuthorizedException;
import net.slipp.dto.QuestionDto;
import support.domain.AbstractEntity;
import support.domain.UrlGeneratable;

@Entity
public class Question extends AbstractEntity implements UrlGeneratable {
	@Size(min = 3, max = 100)
	@Column(length = 100, nullable = false)
	private String title;

	@Size(min = 3)
	@Lob
	private String contents;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	private User writer;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	@Where(clause = "deleted = false")
	@OrderBy("id ASC")
	private List<Answer> answers = new ArrayList<>();

	private boolean deleted = false;
	
	public Question() {
	}

	public Question(String title, String contents) {
		this.title = title;
		this.contents = contents;
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

	public void writeBy(User loginUser) {
		this.writer = loginUser;
	}

	public Answer addAnswer(Answer answer) {
		answers.add(answer);
		answer.toQuestion(this);
		return answer;
	}

	public boolean isOwner(User loginUser) {
		return writer.equals(loginUser);
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public void update(User loginUser, Question updatedQuestion) {
		if (!isOwner(loginUser)) {
			throw new UnAuthorizedException();
		}

		this.title = updatedQuestion.title;
		this.contents = updatedQuestion.contents;
	}
	
    public List<DeleteHistory> delete(User loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) {
            throw new CannotDeleteException("다른 사람의 글은 삭제할 수 없다.");
        }

        List<DeleteHistory> histories = new ArrayList<>();
        for (Answer answer : answers) {
            histories.add(answer.delete(loginUser));
        }

        this.deleted = true;

        histories.add(new DeleteHistory(ContentType.QUESTION, getId(), loginUser, LocalDateTime.now()));
        return histories;
    }

	@Override
	public String generateUrl() {
		return String.format("/questions/%d", getId());
	}

	public QuestionDto _toConvertQuestionDto() {
		return new QuestionDto(getId(), this.title, this.contents);
	}

	@Override
	public String toString() {
		return "Question [id=" + getId() + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
	}
}
