package com.ssp.storage.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.gson.Gson;

@Entity
@Table(name = "questions")
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public.questions_seq")
	@SequenceGenerator(sequenceName = "public.questions_seq", allocationSize = 1, name = "public.questions_seq")
	private Long id;
	private String question;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Question(Long id, String question) {
		super();
		this.id = id;
		this.question = question;
	}

	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
