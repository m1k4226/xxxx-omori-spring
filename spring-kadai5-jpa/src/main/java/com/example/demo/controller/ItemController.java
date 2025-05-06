/**
 * Step1：商品一覧画面を作りなさい（復習）
 * Step2：カテゴリーで絞り込みができるようにしてください（復習）
 * Step3：価格（○○円以下）で検索できるようにしてください
 * Step4：「安い順」リンクを追加し、クリックされたときに並び替えしなさい
 * Step5：テキストボックスの検索条件（○○円以下）が、検索後も保持されるようにしてください
 * Step6：商品名による部分一致検索ができるようにしてください
 * ※keywordパラメータを/itemsにGETで送信する（絞り込み、価格検索、並べ替えとの組み合わせは不要）
 * Step7：「商品名」と「価格」の組み合わせ検索ができるようにしなさい
 * Step8：「商品名」と「価格」の組み合わせ検索の結果を出した後に、安い順リンクを押すと順序が変化するようにしてください
 * Step9：SQLによるDB連携方法を試してみましょう（正答なし）
 */


package com.example.demo.controller;

import java.util.List; // List型を使うためのインポート（商品やカテゴリの一覧に使う

import org.springframework.beans.factory.annotation.Autowired; // 自動的にオブジェクトを注入するアノテーション
import org.springframework.stereotype.Controller; // このクラスがWebのコントローラーであることを示すアノテーション
import org.springframework.ui.Model; // HTMLにデータを渡すために使用する
import org.springframework.web.bind.annotation.GetMapping; // HTTP GETリクエストを受け取るアノテーション
import org.springframework.web.bind.annotation.RequestParam; // フォームやURLのパラメータを受け取るアノテーション

import com.example.demo.entity.Category;
import com.example.demo.entity.Item;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;

@Controller
public class ItemController {

	//	Step1：商品一覧画面を作りなさい（復習）
	@Autowired // Springが自動的にオブジェクトを注入する（DI：依存性注入）
	ItemRepository itemRepository; // 商品情報の取得や保存に使うリポジトリ
	
	//	Step2：カテゴリーで絞り込みができるようにしてください（復習）
	@Autowired
	CategoryRepository categoryRepository; // カテゴリ情報の取得に使うリポジトリ
	
	// --- 商品一覧ページの表示処理 ---
		@GetMapping("/items")
		public String index(
				
				// Step2：カテゴリーで絞り込みができるようにしてください（復習）
				// カテゴリで絞り込むためのID
				// @RequestParam(name = "categoryId", required = false) Integer categoryId
				// required = false：URLパラメータが省略されたら、nullになる（安全）。
				// こうしておけば、categoryId に値がなければ自然に null になり、if (categoryId != null) の分岐が正しく動く。
				@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
				
				// Step3：価格（○○円以下）で検索できるようにしてください
				// 最大価格の指定（未指定ならnullになる）
				@RequestParam(name = "maxPrice", defaultValue = "") Integer maxPrice,
				
				// Step4：「安い順」リンクを追加し、クリックされたときに並び替えしなさい
				// ソート条件（例：priceAsc なら価格昇順）
				@RequestParam(name = "sort", defaultValue = "") String sort,
				
				// Step6：商品名による部分一致検索ができるようにしてください
				// ※keywordパラメータを/itemsにGETで送信する（絞り込み、価格検索、並べ替えとの組み合わせは不要）
				// キーワード検索用のパラメータ（入力がない場合は空文字）
				@RequestParam(name = "keyword", defaultValue = "") String keyword,
				
				Model model) {

			// Step2：カテゴリーで絞り込みができるようにしてください（復習）
			// --- カテゴリ一覧の取得（セレクトボックスなどに使う） ---
			List<Category> categoryList = categoryRepository.findAll(); // 全カテゴリを取得
			model.addAttribute("categories", categoryList); // HTMLに渡す（名前は"categories"）
			
			// Step2：カテゴリーで絞り込みができるようにしてください（復習）
			// --- 商品一覧情報の取得用リスト ---
			List<Item> itemList = null;
			
			if (categoryId != null) {
				// Step2：カテゴリーで絞り込みができるようにしてください（復習）
				// カテゴリが指定されている場合（例：果物だけを表示したいなど）
			    // → itemsテーブルの中から、category_id列が指定のIDと一致する商品だけを取得する
			    // SQLにすると：SELECT * FROM items WHERE category_id = ?
			    itemList = itemRepository.findByCategoryId(categoryId);
			    
			    // Step3：価格（○○円以下）で検索できるようにしてください
			} else if (maxPrice != null) {
			    // 最大価格（maxPrice）が指定されている場合（例：1000円以下の商品だけを表示したいとき）
			    // → itemsテーブルから価格が指定値以下の商品を取得する
			    // SQLにすると：SELECT * FROM items WHERE price <= ?
			    itemList = itemRepository.findByPriceLessThanEqual(maxPrice);
			    
			    // Step4：「安い順」リンクを追加し、クリックされたときに並び替えしなさい
			} else if ("priceAsc".equals(sort)) {
			    // sortパラメータに "priceAsc" が指定されている場合（価格の安い順で並べたいとき）
			    // → すべての商品を価格の昇順（安い順）で並べて取得する
			    // SQLにすると：SELECT * FROM items ORDER BY price ASC
			    itemList = itemRepository.findAllByOrderByPriceAsc();
			    
			    // Step6：商品名による部分一致検索ができるようにしてください
			    // ※keywordパラメータを/itemsにGETで送信する（絞り込み、価格検索、並べ替えとの組み合わせは不要）
			} else if (keyword.length() > 0) {
			    // 検索キーワード（商品名）が入力されている場合（例：「りんご」など）
			    // → name列にキーワードを含む商品を部分一致で検索する
			    // SQLにすると：SELECT * FROM items WHERE name LIKE '%りんご%'
			    itemList = itemRepository.findByNameContaining(keyword);

			} else {
			    // 上記のどの条件も当てはまらない（パラメータが一切指定されていない）場合
			    // → 全商品を取得して表示する
			    // SQLにすると：SELECT * FROM items
			    itemList = itemRepository.findAll();
			}
			
			// Step1：商品一覧画面を作りなさい（復習）
			// → 全商品を取得して表示する
			// SQLにすると：SELECT * FROM items
//			List<Item> itemList = itemRepository.findAll();
			
			// Step1：商品一覧画面を作りなさい（復習）
			// 商品一覧をHTMLに渡す
			model.addAttribute("items", itemList);
			
			// --- フォームに入力された値を保持（画面に再表示するため） ---
			
			// Step5：テキストボックスの検索条件（○○円以下）が、検索後も保持されるようにしてください
			model.addAttribute("maxPrice", maxPrice);

			// Step6：商品名による部分一致検索ができるようにしてください
			// ※keywordパラメータを/itemsにGETで送信する（絞り込み、価格検索、並べ替えとの組み合わせは不要）
			model.addAttribute("keyword", keyword);
		    
			return "items";
			
			
//			 --- 以下は実践課題 ---
			// 複数条件の組み合わせによる商品検索

//			@GetMapping("/items")
//			public String index(
			
//				@RequestParam(name = "keyword", defaultValue = "") String keyword,           // 商品名による検索用キーワード
//				@RequestParam(name = "maxPrice", defaultValue = "") Integer maxPrice,        // 最大価格（例：1000円以下）
//				@RequestParam(name = "sort", defaultValue = "") String sort,                 // 並び替え条件（例："priceAsc"）
//				@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,    // カテゴリID（未使用）
//				Model model                                                                  // HTMLへデータを渡すためのオブジェクト
//			) {
//				List<Item> itemList = null; // 商品一覧を格納する変数
			
				// --- 「キーワード」が入力されていた場合 ---
//				if (keyword.length() > 0) {
			
					// 「最大価格」が入力されていた場合
//					if (maxPrice != null) {
			
						// 「ソート」が価格昇順で指定されていた場合
//						if (sort.equals("priceAsc")) {
			
							// キーワード + 最大価格 + 価格昇順
//							itemList = itemRepository.findByNameContainingAndPriceLessThanEqualOrderByPriceAsc(keyword, maxPrice);
			
//						} else {
			
							// キーワード + 最大価格（ソートなし）
//							itemList = itemRepository.findByNameContainingAndPriceLessThanEqual(keyword, maxPrice);
//						}
			
//					} else {
			
						// 最大価格が指定されていない場合
			
//						if (sort.equals("priceAsc")) {
			
							// キーワード + 価格昇順
//							itemList = itemRepository.findByNameContainingOrderByPriceAsc(keyword);
			
//						} else {
			
							// キーワードのみ
//							itemList = itemRepository.findByNameContaining(keyword);
//						}
//					}

			
				// --- キーワードが入力されていなかった場合（else） ---
//				} else {

					// 最大価格が指定されていた場合
//					if (maxPrice != null) {
			
//						if (sort.equals("priceAsc")) {
			
							// 最大価格 + 価格昇順
//							itemList = itemRepository.findByPriceLessThanEqualOrderByPriceAsc(maxPrice);

//						} else {

							// 最大価格のみ
//							itemList = itemRepository.findByPriceLessThanEqual(maxPrice);
//						}

//					} else {

						// 最大価格も指定されていない場合

//						if (sort.equals("priceAsc")) {

							// 価格昇順のみ指定
//							itemList = itemRepository.findAllByOrderByPriceAsc();

//						} else {
			
							// 条件なし（全件取得）
//						 itemList = itemRepository.findAll();
//						}
//					}
//				}

			
				// 入力内容をHTMLに渡して保持する
//				model.addAttribute("maxPrice", maxPrice);
//				model.addAttribute("keyword", keyword);

				// 商品一覧を渡す
//				model.addAttribute("items", itemList);
			
				// 表示するHTMLファイル名（items.html）
//				return "items";
//			}
			
			
		}

}
