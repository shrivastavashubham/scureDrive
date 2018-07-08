package com.ssp.storage.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssp.storage.domain.Question;
import com.ssp.storage.repository.QuestionRepository;
import com.ssp.storage.service.IQuestionsService;

@Service
public class QuestionsService implements IQuestionsService {

	@Autowired
	QuestionRepository questionRepository;

	@Override
	public List<Question> getQuestions() {
		return (List<Question>) questionRepository.findAll();
	}

	@PostConstruct
	public void setUp() {
		Question question;
		if (questionRepository.count() == 0) {
			List<String> list = new ArrayList<>();
			List<Question> questionsList = new ArrayList<>();
			list.add("What time of the day were you born?");
			list.add("What is the middle name of your youngest cousin?");
			list.add("What was the last name of your first grade teacher");
			for (String ques : list) {
				question = new Question();
				question.setQuestion(ques);
				questionsList.add(question);
			}
			questionRepository.saveAll(questionsList);	
		}
		
	}
}
