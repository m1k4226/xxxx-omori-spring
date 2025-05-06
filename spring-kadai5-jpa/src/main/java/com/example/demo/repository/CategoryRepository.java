package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository; 
//Spring Data JPAの標準機能を使うためのインターフェース（DB操作が簡単になる）

import com.example.demo.entity.Category;



//--- Categoryエンティティのデータベース操作を行うリポジトリ ---
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	// JpaRepository<Category, Integer> 
	// → 第1引数：エンティティのクラス名
	// → 第2引数：主キーの型（CategoryエンティティのidはInteger型）

	// このインターフェースを定義するだけで、
	// findAll() や findById()、save()、delete() などの基本的なDB操作が自動で使えるようになる

	// 例：
	// categoryRepository.findAll(); → 全カテゴリ取得
	// categoryRepository.findById(1); → IDが1のカテゴリ取得
	// categoryRepository.save(category); → カテゴリの保存（INSERTまたはUPDATE）

	// 必要であれば、カスタムメソッドも追加可能（例：findByName(String name) など）

}