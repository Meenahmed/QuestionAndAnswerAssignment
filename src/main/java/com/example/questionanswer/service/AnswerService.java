package com.example.questionanswer.service;

import com.example.questionanswer.exception.ResourcesNotFoundException;
import com.example.questionanswer.model.Answer;
import com.example.questionanswer.model.Question;
import com.example.questionanswer.repository.AnswerRepository;
import com.example.questionanswer.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnswerService {

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    // To get all answers through the questionId
    public List<Answer> getAllAnswers(Long questionId){
        return answerRepository.findByQuestionId(questionId);
    }

    // To add new answer
    public Answer addNewAnswer(Long questionId, Answer answer){
        return questionRepository.findById(questionId).map((question)->{
            answer.setQuestion(question);
            answer.setCreatedAt(Instant.now());

            return answerRepository.save(answer);
        }).orElseThrow(()-> new ResourcesNotFoundException("Answer is not added"));
    }

    // To update an answer
    public Answer updateAnswer(Long questionId, Long answerId, Answer answerRequest){
        Optional<Question> findQuestionById = questionRepository.findById(questionId);
        if (!findQuestionById.isPresent()) {
            throw new ResourcesNotFoundException("question with id not found");
        }
            return answerRepository.findById(answerId).map((answer) ->{
                answer.setText(answerRequest.getText());
                answer.setUpdatedAt(Instant.now());

                return answerRepository.save(answer);
            }).orElseThrow(()-> new ResourcesNotFoundException("answer not updated"));
        }

        // To delete an delete
   public ResponseEntity<?> deleteAnswer(Long questionId, Long answerId){
       Optional<Question> findByQuestionId = questionRepository.findById(questionId);
       if (!findByQuestionId.isPresent()){
           throw new ResourcesNotFoundException("question with id " + questionId + " not found");
       }

       return answerRepository.findById(answerId).map((answer)->{
           answerRepository.delete(answer);

           return ResponseEntity.ok().build();
       }).orElseThrow(()-> new ResourcesNotFoundException("answer with id " + answerId + " not deleted"));
   }
}
