package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="answers")
public class Answer extends AuditModel {

	@Id
	@GeneratedValue(generator = "answer_generator")
	@SequenceGenerator(
		name="answer_generator",
		sequenceName="answer_sequence",
		initialValue=1000
	)
	private Long Id;
	
	@Column(columnDefinition="text")
	private String text;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="question_id", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Question question;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}
