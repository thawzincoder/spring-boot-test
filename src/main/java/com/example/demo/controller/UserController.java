package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;

@Controller
public class UserController {

	@Autowired
	private UserRepo myUserRepo;

	@RequestMapping("/hello")
	public String helloPage() {
		return "hello";
	}

	// Register Page Get
	@RequestMapping("/register")
	public String RegisterPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("error", true);

		return "register";
	}

	// Register Page Post
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String RegisterPagePost(Model model, @ModelAttribute User user) {
		User db_user = myUserRepo.checkEmail(user.getEmail());
		if (db_user == null) {
			myUserRepo.save(user);

			model.addAttribute("user", new User());
			return "login";

		} else {
			model.addAttribute("error", false);
		}

		model.addAttribute("user", user);
		return "register";
	}

	@RequestMapping("/login")
	public String LoginPage(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("loginError", true);
		
		return "login";
	}

	//	Login Submit Button
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String SubmitLoginPage(Model model, @ModelAttribute User user) {

		User db_user = myUserRepo.checkLogin(user.getEmail(), user.getPassword());
		
		if (db_user == null) {
			model.addAttribute("loginError", false);
		} else {
			List<User> users = myUserRepo.findAll();
			model.addAttribute("users", users);
			
			return "home";
		}

		model.addAttribute("user", user);
		return "login";
	}

	@RequestMapping("/edit/{id}")
	public String EditPage(Model model, @PathVariable("id") long id) {
		User user = myUserRepo.findById(id).orElseThrow();
		model.addAttribute("user", user);
		return "edit";
	}
	
	@RequestMapping("/edit")
	public String EditSubmit(Model model, @ModelAttribute User user) {
		myUserRepo.save(user);
		List<User> users = myUserRepo.findAll();
		model.addAttribute("users", users);
		
		return "home";
	}
	
	// Delete User
	@RequestMapping("/delete/{id}")
	public String DeleteUser(Model model, @PathVariable("id") long id) {
		myUserRepo.deleteById(id);
		List<User> users = myUserRepo.findAll();
		model.addAttribute("users", users);
		
		return "home";
	}
}
