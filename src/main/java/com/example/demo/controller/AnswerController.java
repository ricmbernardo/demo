package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Answer;
import com.example.demo.model.Question;
import com.example.demo.repository.AnswerRepository;
import com.example.demo.repository.QuestionRepository;

@RestController
public class AnswerController {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("/questions/{questionId}/answers")
	public List<Answer> getAnswersByQuestionId(@PathVariable Long questionId) {
		return answerRepository.findByQuestionId(questionId);
	}
	
	@PostMapping("/questions/{questionId}/answers")
	public ResponseEntity<?> addAnswer(@PathVariable Long questionId, @Valid @RequestBody Answer answerRequest) {
		
		Optional<Question> optionalQuestion = questionRepository.findById(questionId);
		Question question = optionalQuestion.get();
		
		if(optionalQuestion.isPresent()) {
			answerRequest.setQuestion(question);
			answerRepository.save(answerRequest);
			HttpHeaders responseHeader = new HttpHeaders();
			return new ResponseEntity<Answer>(answerRequest, responseHeader, HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@PutMapping("/questions/{questionId}/answers/{answerId}")
	public ResponseEntity<?> updateAnswer(@PathVariable Long questionId, @PathVariable Long answerId, @Valid @RequestBody Answer answerRequest) {
				
		if (questionRepository.existsById(questionId)) {			
			Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
			if (optionalAnswer.isPresent()) {
				Answer answer = optionalAnswer.get();
				answer.setText(answerRequest.getText());
				answerRepository.save(answer);
				HttpHeaders responseHeader = new HttpHeaders();
				return new ResponseEntity<Answer>(answerRequest, responseHeader, HttpStatus.OK);
			} else {
				return ResponseEntity.notFound().build();
			}
		} else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@DeleteMapping("/questions/{questionId}/answers/{answerId}")
	public ResponseEntity<?> deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId) {
		
		if (questionRepository.existsById(questionId)) {
			Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
			if (optionalAnswer.isPresent()) {
				Answer answer = optionalAnswer.get();
				answerRepository.delete(answer);
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		} else {
			return ResponseEntity.notFound().build();
		}
		
	}

}
