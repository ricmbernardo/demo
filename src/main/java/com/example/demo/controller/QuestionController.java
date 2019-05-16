package com.example.demo.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepository;

@RestController
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("/questions")
	public Page<Question> getQuestions(Pageable pageable) {
		return questionRepository.findAll(pageable);
	}
	
	@PostMapping("/questions")
	public Question createQuestion(@Valid @RequestBody Question question) {
		return questionRepository.save(question);
	}
	
//	@PutMapping("/questions/{questionId}")
//	public Optional<Question> updateQuestion(@PathVariable Long questionId, @Valid @RequestBody Question questionRequest) {
//		return questionRepository.findById(questionId)
//				.map(question -> {
//					question.setTitle(questionRequest.getTitle());
//					question.setDescription(questionRequest.getDescription());
//					return questionRepository.save(question);
//				});
				//.orElseThrow(() -> new Exception("Question not found with id " + questionId));		
//	}
	
//	HttpHeaders responseHeaders = new HttpHeaders();
//	return new ResponseEntity<String>(category, responseHeaders, HttpStatus.OK);
	
	@PutMapping("/questions/{questionId}")
	public ResponseEntity<?> updateQuestion(@PathVariable Long questionId, @Valid @RequestBody Question questionRequest) {
		
		Optional<Question> questionOptional = questionRepository.findById(questionId);
		
		Question question = questionOptional.get();
			
		if(questionOptional.isPresent()) {
			question.setTitle(questionRequest.getTitle());
			question.setDescription(questionRequest.getDescription());
			questionRepository.save(question);
			HttpHeaders responseHeaders = new HttpHeaders();
			return new ResponseEntity<Question>(question, responseHeaders, HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
//	@DeleteMapping("/questions/{questionId}")
//	public Optional<Question> deleteQuestion(@PathVariable Long questionId) {
//		return questionRepository.findById(questionId)
//				.map(question -> {
//					questionRepository.delete(question);
//					return ResponseEntity.ok().build();
//				});
//	}
	@DeleteMapping("/questions/{questionId}")
	public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
		
		Optional<Question> questionOptional = questionRepository.findById(questionId);
		Question question = questionOptional.get();
		
		if(questionOptional.isPresent()) {
			questionRepository.delete(question);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
		
	}

}
