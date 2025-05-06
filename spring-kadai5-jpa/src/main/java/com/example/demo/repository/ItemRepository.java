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

package com.example.demo.repository;

import java.util.List; // 複数件のデータを扱うためにListを使用

//Spring Data JPAの機能を使うためのインターフェース。データベース操作を簡単にしてくれる
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Item; //Itemクラスのインポート


//--- Itemエンティティのデータベース操作を行うためのリポジトリ ---
//JpaRepository<Item, Integer> → Itemエンティティの主キーがInteger型であることを指定
public interface ItemRepository extends JpaRepository<Item, Integer> {
	// 第1引数：エンティティの型（Item）
    // 第2引数：主キーの型（Integer）
	
	
	// --- 以下、カスタムメソッド（SQL文が自動生成される） ---

	// Step1：商品一覧画面を作りなさい（復習）
	// 指定したカテゴリーIDに一致する商品をすべて取得
	// SELECT * FROM items WHERE category_id = ?
	List<Item> findByCategoryId(Integer categoryId);

	// Step3：価格（○○円以下）で検索できるようにしてください
	// 指定された価格以下の商品を取得（例：100円以下の商品）
	// SELECT * FROM items WHERE price <= ?
	List<Item> findByPriceLessThanEqual(Integer maxPrice);

	// Step4：「安い順」リンクを追加し、クリックされたときに並び替えしなさい
	// 商品を価格の安い順に並べてすべて取得
	// SELECT * FROM items ORDER BY price
	List<Item> findAllByOrderByPriceAsc();
	
	// Step6：商品名による部分一致検索ができるようにしてください	
	// 商品名に特定のキーワードが含まれる商品を取得（部分一致検索）
	// SELECT * FROM items WHERE name LIKE %?%
	List<Item> findByNameContaining(String keyword);
	
	// 以下、Step5：テキストボックスの検索条件（○○円以下）が、検索後も保持されるようにしてください
	// 指定された価格以下の商品を、価格の安い順に取得
	// SELECT * FROM items WHERE price <= ? ORDER BY price ASC
	List<Item> findByPriceLessThanEqualOrderByPriceAsc(Integer maxPrice);
	
	// 商品名にキーワードが含まれ、価格の安い順に並べる
	// SELECT * FROM items WHERE name LIKE %?% ORDER BY price ASC
	List<Item> findByNameContainingOrderByPriceAsc(String keyword);

	// 商品名にキーワードが含まれ、かつ指定された価格以下の商品を取得
	// SELECT * FROM items WHERE name LIKE %?% AND price <= ?
	List<Item> findByNameContainingAndPriceLessThanEqual(String keyword, Integer maxPrice);

	// 商品名にキーワードが含まれ、かつ価格が指定値以下で、価格の安い順に並べて取得
	// SELECT * FROM items WHERE name LIKE %?% AND price <= ? ORDER BY price ASC
	List<Item> findByNameContainingAndPriceLessThanEqualOrderByPriceAsc(String keyword, Integer maxPrice);
	
	
}
