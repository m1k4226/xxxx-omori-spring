// Step1：商品一覧画面を作りなさい（復習）

package com.example.demo.entity;

import jakarta.persistence.Column; // JPAでカラム情報を設定するためのアノテーション
import jakarta.persistence.Entity; // このクラスがエンティティ（テーブルに対応するクラス）であることを示す
import jakarta.persistence.GeneratedValue; // 主キーの値を自動で生成する設定に使う。
import jakarta.persistence.GenerationType; // 主キーの生成方法（今回はIDENTITY＝DB任せ）を指定。
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity// このクラスはDBのテーブルに対応していることを意味する。
@Table(name = "items") // このエンティティは「items」テーブルと対応している。

public class Item {
	// フィールド
	
		//	この id は items テーブルの主キー に対応しており、「商品ごとに一意な識別子」となる。
		//	この id は Item クラスの id と連動しており、エンティティ（Java）とテーブル（DB）をつなぐカギになる。
		//	JPA（Java Persistence API）のルールとしても、エンティティには主キーが必須
		@Id // 主キー

		//	この指定は「このフィールド（id）の値は、INSERT時にDBが自動で採番してください」という明確な命令を意味する。
		//	DB（MySQLなど）が AUTO_INCREMENT によって自動でIDを付けてくれる。
		//	登録後、Spring Data JPAがそのIDを取得してJavaオブジェクトにセットしてくれる。
		@GeneratedValue(strategy = GenerationType.IDENTITY) // 主キーの値はDB側で自動採番（1,2,3…）
		private Integer id; // 主キーの値はDB側で自動採番（1,2,3…）

		@Column(name = "category_id") // カラム名を「category_id」として指定（DBの列名とJavaの変数名が異なる場合に使う）
		private Integer categoryId; // カテゴリーID
		private String name; // 商品名
		private Integer price; // 価格

		// ゲッター
		public Integer getId() {
			return id;
		}

		public Integer getCategoryId() {
			return categoryId;
		}

		public String getName() {
			return name;
		}

		public Integer getPrice() {
			return price;
		}

}
