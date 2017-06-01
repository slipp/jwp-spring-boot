package net.slipp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Where;

import net.slipp.CannotDeleteException;
import net.slipp.UnAuthorizedException;

@Embeddable
public class Answers {
    @OneToMany(mappedBy="question", cascade = CascadeType.ALL)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Answer> answers = new ArrayList<>();

    public void add(Answer answer) {
        answers.add(answer);      
    }
    
    public List<DeleteHistory> delete(User loginUser) throws CannotDeleteException {
        List<DeleteHistory> histories = new ArrayList<>();
        
        for (Answer answer : answers) {
            histories.add(deleteAnswer(loginUser, answer));
        }
        
        return histories;
    }

    private DeleteHistory deleteAnswer(User loginUser, Answer answer)
            throws CannotDeleteException {
        try {
            return answer.delete(loginUser);
        } catch (UnAuthorizedException e) {
            throw new CannotDeleteException("다른 사용자가 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }
}