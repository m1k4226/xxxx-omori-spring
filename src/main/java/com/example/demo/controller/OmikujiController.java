package com.example.demo.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OmikujiController {

	@GetMapping("omikuji")
	public String index() {

		return "omikuji";
	}

	@PostMapping("omikuji")
	public String omikuji(Model model) {

		// おみくじ
		Random rand = new Random();
		int dice = rand.nextInt(6) + 1;
		switch (dice) {
		case 1:
			model.addAttribute("result", "大吉");
			break;
		case 2:
			model.addAttribute("result", "小吉");
			break;
		case 3:
			model.addAttribute("result", "凶");
			break;
		default:
			model.addAttribute("result", "吉");
			break;
		}

		return "omikuji";
	}
}
