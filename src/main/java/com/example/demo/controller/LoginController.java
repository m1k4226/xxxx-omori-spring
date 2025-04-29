package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@GetMapping("login")
	public String index() {

		return "login";
	}

	@PostMapping("login")
	public String login(
			@RequestParam("userId") String userId,
			@RequestParam("password") String password,
			Model model) {

		if (!userId.equals("student")) {
			model.addAttribute("error", "ユーザIDが一致しませんでした");
			return "login";
		}

		if (!password.equals("himitu")) {
			model.addAttribute("error", "パスワードが一致しませんでした");
			return "login";
		}

		return "index";
	}
}
