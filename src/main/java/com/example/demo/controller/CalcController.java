package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalcController {

	@GetMapping("calc")
	public String index() {
		return "calc";
	}

	@GetMapping("add")
	public String add(
			@RequestParam("num1") int num1,
			@RequestParam("num2") int num2,
			Model model) {

		model.addAttribute("result", num1 + num2);

		return "calc";
	}
}
