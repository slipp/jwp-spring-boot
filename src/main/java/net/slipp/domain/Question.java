package net.slipp.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

import net.slipp.CannotDeleteException;
import net.slipp.dto.QuestionDto;
import support.domain.AbstractUserEntity;
import support.domain.UrlGeneratable;

@Entity
public class Question extends AbstractUserEntity implements UrlGeneratable {
    @Size(min = 3, max = 100)
    @Column(length = 100, nullable = false)
    private String title;

    @Size(min = 3)
    @Lob
    private String contents;

    @Embedded
    private Answers answers = new Answers();

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

    public Answer addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
        return answer;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void update(User loginUser, Question updatedQuestion) {
        verifyAuthorizedOwner(loginUser);

        this.title = updatedQuestion.title;
        this.contents = updatedQuestion.contents;
    }
    
    public List<DeleteHistory> delete(User loginUser) throws CannotDeleteException {
        verifyAuthorizedOwner(loginUser);

        List<DeleteHistory> histories = answers.delete(loginUser);

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
        return super.toString() + "Question [id=" + getId() + ", title=" + title + ", contents=" + contents + "]";
    }
}