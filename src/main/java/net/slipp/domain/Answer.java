package net.slipp.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import support.domain.AbstractUserEntity;
import support.domain.UrlGeneratable;

@Entity
public class Answer extends AbstractUserEntity implements UrlGeneratable {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Size(min = 5)
    @Lob
    private String contents;

    private boolean deleted = false;

    public Answer() {
    }

    public Answer(User writer, String contents) {
        super.writeBy(writer);
        this.contents = contents;
    }
    
    public Answer(Long id, User writer, Question question, String contents) {
        super(id, writer);
        this.question = question;
        this.contents = contents;
        this.deleted = false;
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

    public boolean isDeleted() {
        return deleted;
    }

    public DeleteHistory delete(User loginUser) {
        verifyAuthorizedOwner(loginUser);
        
        this.deleted = true;

        return new DeleteHistory(ContentType.ANSWER, getId(), loginUser, LocalDateTime.now());
    }

    @Override
    public String generateUrl() {
        return String.format("%s/answers/%d", question.generateUrl(), getId());
    }

    @Override
    public String toString() {
        return super.toString() + " Answer [id=" + getId() + ", contents=" + contents + "]";
    }
}
