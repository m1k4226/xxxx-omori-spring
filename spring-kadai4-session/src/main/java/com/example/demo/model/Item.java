package com.example.demo.model;


public class Item {
	
	//	フィールド
	private String name;
	private Integer price;
	
	//	デフォルトコンストラクタ
	public Item() {
	}

	//	引数ありコンストラクタ
	public Item(String name, Integer price) {
		this.name = name;
		this.price = price;
	}

	//	ゲッター/セッター
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
	
}
