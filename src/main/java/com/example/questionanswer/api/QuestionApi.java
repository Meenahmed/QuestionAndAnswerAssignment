package com.example.questionanswer.api;

import com.example.questionanswer.model.Question;
import com.example.questionanswer.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "question/api/v1/")
public class QuestionApi {

    private QuestionService questionService;

    // Add a new question
    @PostMapping("/add")
    public Question addQuestion(@RequestBody Question question){
        return questionService.createQuestion(question);
    }

    // Update question
    @PutMapping("/update")
    public Question updateQuestion(@RequestBody Question question){
        return questionService.updateQuestion(question);
    }

    // Get all questions
    @GetMapping(path = "/all")
    public List<Question> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    // Delete question
    @DeleteMapping("{questionId}/delete")
    public ResponseEntity<?> deleteQuestion(@PathVariable(value = "questionId") Long questionId){
        return questionService.deleteQuestion(questionId);
    }

}
