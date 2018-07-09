package com.ssp.storage.service.impl;

import java.io.StringWriter;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ssp.storage.constant.ErrorCode;
import com.ssp.storage.domain.Question;
import com.ssp.storage.domain.User;
import com.ssp.storage.domain.UserSecurityQuestion;
import com.ssp.storage.exception.UserException;
import com.ssp.storage.repository.UserRepository;
import com.ssp.storage.repository.UserSecurityQuestionRepository;
import com.ssp.storage.service.IUserSecurityQuestionService;

@Service
public class UserSecurityQuestionService implements IUserSecurityQuestionService {

	@Autowired
	UserSecurityQuestionRepository userSecurityQuestionRepository;

	@Autowired
	UserRepository userRepository;

	@Value("${server.port}")
	private int port;

	@Value("${from}")
	private String from;
	@Value("${password}")
	private String password;

	@Autowired
	VelocityEngine velocityEngine;

	@Override
	public List<Question> getQuestions(String username, String password) throws UserException {
		if (!userRepository.existsByUsernameAndPassword(username, password))
			throw new UserException(port + ErrorCode.INVALID_CREDENTIALS.getKey(),
					ErrorCode.INVALID_CREDENTIALS.getValue());
		return userSecurityQuestionRepository.findAllByUserUsername(username).stream().map(a -> a.getQuestion())
				.collect(Collectors.toList());
	}

	@Transactional
	@Override
	public boolean addSecurityQuestions(User user, List<UserSecurityQuestion> userSecurityQuestionsRequest)
			throws UserException {
		/*
		 * if (!userRepository.existsByUsernameAndPassword(username, password))
		 * throw new UserException(port +
		 * ErrorCode.INVALID_CREDENTIALS.getKey(),
		 * ErrorCode.INVALID_CREDENTIALS.getValue()); User user =
		 * userRepository.findByUsername(username); for (UserSecurityQuestion
		 * userSecurityQuestion : userSecurityQuestionsRequest) {
		 * userSecurityQuestion.setUser(user); }
		 */
		try {
			userSecurityQuestionRepository.deleteByUser(user);
			userSecurityQuestionRepository.saveAll(userSecurityQuestionsRequest);
			return true;
		} catch (DataAccessException e) {
			throw new UserException(port, e.getMessage());
		}
	}

	public boolean validate(String username, String password, List<UserSecurityQuestion> requestList)
			throws UserException {
		User user = userRepository.findByUsernameAndPassword(username, password);
		if (user == null) {
			throw new UserException(port + ErrorCode.INVALID_CREDENTIALS.getKey(),
					ErrorCode.INVALID_CREDENTIALS.getValue());
		}
		List<UserSecurityQuestion> list = userSecurityQuestionRepository.findAllByUser(user);
		for (UserSecurityQuestion userSecurityQuestion : list) {
			for (UserSecurityQuestion requestSecurityQuestion : requestList) {
				if (requestSecurityQuestion.getQuestion().getId().equals(userSecurityQuestion.getQuestion().getId())) {
					if (!userSecurityQuestion.getAnswer().equals(requestSecurityQuestion.getAnswer())) {
						String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
						SecureRandom rnd = new SecureRandom();
						StringBuilder sb = new StringBuilder(10);
						for (int i = 0; i < 10; i++)
							sb.append(AB.charAt(rnd.nextInt(AB.length())));
						user.setPassword(sb.toString());
						userRepository.save(user);
						Template template = null;
						try {
							template = velocityEngine.getTemplate("securityAlert.html", "UTF-8");
						} catch (org.apache.velocity.exception.ResourceNotFoundException e) {
							e.printStackTrace();
						}
						VelocityContext velocityContext = new VelocityContext();
						velocityContext.put("password", sb.toString());
						StringWriter writer = new StringWriter();
						template.merge(velocityContext, writer);
						/*
						 * InputStream inputStream =
						 * TypeReference.class.getResourceAsStream(""); String
						 * result = new BufferedReader(new
						 * InputStreamReader(inputStream)).lines()
						 * .collect(Collectors.joining("\n"));
						 */
						Mail.send(from, user.getEmail(), password, writer.toString());
						return false;
					}
				}
			}
		}
		return true;
	}
}