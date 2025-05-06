// Step2：カテゴリーで絞り込みができるようにしてください（復習）

package com.example.demo.entity;

import jakarta.persistence.Entity; // エンティティ（データベースのテーブルと対応するクラス）を示すアノテーション
import jakarta.persistence.GeneratedValue; // 主キーの値を自動生成するために使用する
import jakarta.persistence.GenerationType; // 自動生成の方法を指定（IDENTITYはDBが自動で採番）
import jakarta.persistence.Id; // 主キーであることを示すアノテーション
import jakarta.persistence.Table; // 対応するテーブル名を指定するためのアノテーション


@Entity // このクラスがエンティティであることを指定
@Table(name = "categories") // 対応するテーブル名を「categories」と指定
public class Category { // カテゴリーを表すクラス



	// --- フィールド（テーブルのカラムに対応） ---

	//	この id は categories テーブルの主キー に対応しており、「種類ごとに一意な識別子」となる。
	//	この id は Category クラスの id と連動しており、エンティティ（Java）とテーブル（DB）をつなぐカギになる。
	//	JPA（Java Persistence API）のルールとしても、エンティティには主キーが必須
	@Id // 主キー（テーブルで一意の識別子）を指定
	
	//	この指定は「このフィールド（id）の値は、INSERT時にDBが自動で採番してください」という明確な命令を意味する。
	//	DB（MySQLなど）が AUTO_INCREMENT によって自動でIDを付けてくれる。
	//	登録後、Spring Data JPAがそのIDを取得してJavaオブジェクトにセットしてくれる。	
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主キーはDB側で自動採番される
	private Integer id;

	private String name;



	// --- ゲッター（値を取得するメソッド） ---

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}