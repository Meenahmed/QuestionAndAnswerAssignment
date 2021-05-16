package com.example.questionanswer.service;

import com.example.questionanswer.exception.ResourcesNotFoundException;
import com.example.questionanswer.model.Question;
import com.example.questionanswer.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    // To add a new question
    public Question createQuestion(Question question){
        question.setCreatedAt(Instant.now());
      return questionRepository.save(question);
    }

    // To update question
    public Question updateQuestion(Question questionRequest){
        return questionRepository.findById(questionRequest.getId()).map((question) ->{
            question.setUpdatedAt(Instant.now());
            question.setText(questionRequest.getText());
            question.setDescription(questionRequest.getDescription());
            return questionRepository.save(question);
        }).orElseThrow(()-> new ResourcesNotFoundException("question with the id " + questionRequest.getId() + " not updated"));
    }

    // To get all questions
    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }

    // Get one questionById
    public ResponseEntity<Question> getQuestion(Long questionId){
        Optional<Question> question = questionRepository.findById(questionId);
        if (!question.isPresent())
            return new ResponseEntity("question not found", HttpStatus.NOT_FOUND);

        return ResponseEntity.of(question);
    }

    // Delete a question
    public ResponseEntity<?> deleteQuestion(Long questionId){
        Optional<Question> question = questionRepository.findById(questionId);

        if (!question.isPresent()) {
            return new ResponseEntity("question not found", HttpStatus.NOT_FOUND);
        }

        questionRepository.delete(question.get());

        return ResponseEntity.ok().body("Question successfully deleted");
    }

}
