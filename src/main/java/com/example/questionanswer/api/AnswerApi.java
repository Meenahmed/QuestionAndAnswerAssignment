package com.example.questionanswer.api;

import com.example.questionanswer.model.Answer;
import com.example.questionanswer.service.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "answer")
@AllArgsConstructor
@RestController
public class AnswerApi {
    private final AnswerService answerService;


    @GetMapping("{questionId}/all")
    public List<Answer> getAnswers(@PathVariable Long questionId){
        return answerService.getAllAnswers(questionId);
    }

    @PostMapping("{questionId}/add")
    public Answer addAnswer(@PathVariable Long questionId, @RequestBody Answer answer){
        return answerService.addNewAnswer(questionId, answer);
    }

    @PutMapping("{questionId}/{answerId}/update")
    public Answer updateAnswer(@PathVariable Long questionId,
                               @PathVariable Long answerId,
                               @RequestBody Answer answer) {
        return answerService.updateAnswer(questionId, answerId, answer);
    }

    @DeleteMapping("{questionId}/{answerId}/delete")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId){
        return answerService.deleteAnswer(questionId, answerId);
    }
}
