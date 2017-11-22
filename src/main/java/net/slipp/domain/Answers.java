package net.slipp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Embeddable
public class Answers {
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private List<Answer> answers = new ArrayList<>();

    public void add(Answer answer) {
        answers.add(answer);
    }
}
