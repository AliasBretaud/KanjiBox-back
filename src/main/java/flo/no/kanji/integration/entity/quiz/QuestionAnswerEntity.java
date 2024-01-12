package flo.no.kanji.integration.entity.quiz;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz_question_answer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionAnswerEntity {

    /** Database technical identifier **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "question_id")
    private QuizQuestionEntity quizQuestion;

    private String answered;

    private Boolean isCorrect;
}
