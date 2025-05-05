package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

	@GetMapping("/contact")
	public String index() {
		return "contactForm";
	}

	@PostMapping("/contact")
	public String contact(
			@RequestParam(name = "genre", defaultValue = "") String genre,
			@RequestParam(name = "lang", defaultValue = "") String[] langList,
			@RequestParam(name = "detail", defaultValue = "") String detail,
			@RequestParam(name = "dueDate", defaultValue = "") LocalDate dueDate,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "email", defaultValue = "") String email,
			Model model) {

		// エラーチェック
		List<String> errorList = new ArrayList<>();
		if (langList.length == 0) {
			errorList.add("言語は必須です");
		}
		LocalDate today = LocalDate.now();
		if (dueDate != null && dueDate.compareTo(today) <= 0) {
			errorList.add("実施予定日は翌日以降を選択してください");
		}
		if (name.length() == 0) {
			errorList.add("名前は必須です");
		} else if (name.length() > 20) {
			errorList.add("名前は20字以内で入力してください");
		}
		if (email.length() == 0) {
			errorList.add("メールアドレスは必須です");
		}

		// エラー発生時はお問い合わせフォームに戻す
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			return "contactForm";
		}

		model.addAttribute("genre", genre);
		model.addAttribute("langList", langList);
		model.addAttribute("detail", detail);
		model.addAttribute("dueDate", dueDate);
		model.addAttribute("name", name);
		model.addAttribute("email", email);

		return "contactResult";
	}

	@GetMapping("/training/{lang}")
	public String detail(
			@PathVariable("lang") String lang,
			Model model) {

		model.addAttribute("lang", lang);
		switch (lang) {
		case "Java":
			model.addAttribute("memo", "大規模開発でオールラウンドに活躍できるエンジニアを育成します");
			break;

		case "PHP":
			model.addAttribute("memo", "Webアプリケーションに特化して活躍できるエンジニアを育成します");
			break;

		case "Python":
			model.addAttribute("memo", "AI開発やデータ分析の領域で活躍できるエンジニアを育成します");
			break;
		}

		return "training";
	}
}
