package net.slipp.domain;

import support.jpa.SlippRepository;

public interface QuestionRepository extends SlippRepository<Question, Long> {
    Iterable<Question> findByDeleted(boolean deleted);
}
