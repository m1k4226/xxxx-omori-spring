package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
	
	//　index.htmlを表示
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	//　input.htmlを表示
	@GetMapping("/hello")
	public String input() {
		return "input";
	}
	
	//　input.htmlからのform内容を受信
	//　hello.htmlにform内容を反映、送信
	@PostMapping("/hello")
	public String show(
			@RequestParam String name,
			@RequestParam Integer age,
			@RequestParam String hobby,
			Model model
			){
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		model.addAttribute("hobby", hobby);
		
//		if(age >= 18) {
//			model.addAttribute("成人してから\"+(age-20)+\"年たちました", age);
//		}else {
//			String ageCheck = "未成年です";
//			model.addAttribute("ageCheck", age);
//		}
		
		return "hello";
	}
	
}
