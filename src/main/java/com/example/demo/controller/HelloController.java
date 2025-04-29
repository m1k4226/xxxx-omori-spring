package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("hello")
	public String input() {
		return "input";
	}

	@PostMapping("hello")
	public String show(
			@RequestParam("name") String name, // 名前 
			@RequestParam("age") int age, // 年齢
			@RequestParam("hobby") String hobby, // 趣味
			Model model) {

		// Thymeleafにデータを渡す
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		if (age >= 18) {
			model.addAttribute("ageMemo", "成人してから" + (age - 18) + "年たちました");
		} else {
			model.addAttribute("ageMemo", "未成年です");
		}
		model.addAttribute("hobby", hobby);

		// hello.htmlを出力する
		return "hello";
	}
}