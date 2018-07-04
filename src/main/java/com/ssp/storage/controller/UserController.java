package com.ssp.storage.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssp.storage.error.AcgError;
import com.ssp.storage.exception.UserException;
import com.ssp.storage.service.IUserService;
import com.ssp.storage.vo.Signup;
import com.ssp.storage.web.ResponseEntity;

@Controller
@RequestMapping("/")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Value("${server.port}")
	private int port;

	@Autowired
	IUserService userService;

	@GetMapping("/user")
	public ResponseEntity<?> authenticateUser(@RequestHeader(name = "username") String username,
			@RequestHeader(name = "password") String password) {
		try {
			return new ResponseEntity<>(userService.authenticate(username, password), HttpStatus.OK);
		} catch (UserException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(new AcgError(e.getErrorCode(), e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/signup" /*, consumes= {"application/x-www-form-urlencoded;charset=UTF-8"}*/)
	public void addCustomer(@ModelAttribute Signup signup/*@RequestBody User user*/) {
		System.out.println(signup);
		/*try {
			return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
		} catch (UserException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(new AcgError(e.getErrorCode(), e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}*/
	}

	@GetMapping
	public String e(Model model) {
		List<String> questions = new ArrayList<>();
		questions.add("What time of the day were you born?");
		questions.add("What is the middle name of your youngest cousin?");
		questions.add("What was the last name of your first grade teacher");
		model.addAttribute("securityQuestions", questions);
		return "signup";
	}
}
