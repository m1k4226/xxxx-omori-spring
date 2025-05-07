/**
 * Step1：商品一覧画面を作りなさい（復習）
 * Step2：新規登録の処理を追加しなさい
 * Step3：一覧画面に更新ボタンを追加し、更新画面の表示をしてください
 * Step4：更新画面にて、情報を変更し更新ボタンをクリックしたとき、データベースの内容を更新し、最新の情報で一覧画面を表示しなさい
 * Step5：削除機能を追加してください
 */

package com.example.demo.controller;

//リストインターフェースをインポート
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Step2：新規登録の処理を追加しなさい
import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;

// コントローラークラスであることを示すアノテーション
@Controller
public class ItemController {

	// Step2：新規登録の処理を追加しなさい
	// ItemRepositoryをインスタンス化してくれる（クラス内のNew演算子の記述不要、インスタンス化を一回で済ますことが可能）
	@Autowired
	ItemRepository itemRepository;

	// Step1：商品一覧画面を作りなさい（復習）
	// Getリクエストを処理する。引数はパスパラメータ。
	@GetMapping("/items")
	public String index(Model model) { // コントローラーからビューにデータを渡すため、引数にModel追加。

		// JpaRepositoryのfindAll()メソッドでitemsテーブルの全てのレコードを取得し、変数itemListに格納
		List<Item> itemList = itemRepository.findAll();

		// ModelオブジェクトのaddAttributeメソッドでthymeleafにデーターを渡す
		// itemsという名前で変数itemListの値をViewに渡す
		model.addAttribute("items", itemList);

		// itemsという名前のViewを表示する
		return "items";
	}

	// Step2：新規登録の処理を追加しなさい
	// method指定がない場合、デフォルト：GET　(HTTPリクエスト)
	// items.htmlの"新規登録(aタグ)"のHTTPリクエスト
	@GetMapping("/items/add")
	public String create() {

		//addItemというビューを返す
		return "addItem";
	}

	// Step2：新規登録の処理を追加しなさい
	// 新規登録画面からのHTTPリクエスト
	@PostMapping("/items/add")
	public String Store(
			//パラメーターで送られてきたフォームに入力された値を受け取る
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "price", defaultValue = "") Integer price) {

		// Itemオブジェクトの生成
		// Entity型（Itemクラスのコンストラクタより）
		Item items = new Item(categoryId, name, price);

		// itemsテーブルに追加
		// itemListは、itemRepositoryが入っているため、itemsテーブルに追加する必要がある。
		// 引数にitems(Item型オブジェクト)
		itemRepository.save(items);

		// View：itemsにGetでリクエストし直す　（リダイレクト）
		// Postで再表示させないよう、Getリクエストにするためのリダイレクト
		return "redirect:/items";
	}

	// Step3：一覧画面に更新ボタンを追加し、更新画面の表示をしてください
	@GetMapping("/items/{id}/edit")
	public String edit(
			// @PathVariableでURL のパスの一部からパスを取得(変更するレコードのidを取得)
			@PathVariable("id") Integer id,
			Model model) {

		// itemsテーブルから引数のidと一致する値があるか検索し、get()で値を取得
		Item item = itemRepository.findById(id).get();

		// itemsという名前で変数itemの値をビューに渡せるようにする
		model.addAttribute("items", item);

		// editItemとういビューを表示する
		return "editItem";
	}

	// Step4：更新画面にて、情報を変更し更新ボタンをクリックしたとき、データベースの内容を更新し、最新の情報で一覧画面を表示しなさい
	@PostMapping("/items/{id}/edit")
	public String update(
			//@PathVariableでURL のパスの一部からパスを取得(変更するレコードのidを取得)
			@PathVariable("id") Integer id,
			//@RequestParamURL のクエリパラメータからパスを取得(フォームから送信された値を取得)
			@RequestParam(name = "categoryId") Integer categoryId,
			@RequestParam(name = "name") String name,
			@RequestParam(name = "price") Integer price,
			Model model) {

		// Itemオブジェクトの生成し、引数の値でコンストラクタで初期化
		Item items = new Item(id, categoryId, name, price);

		// save()メソッドitemsテーブルに追加：登録ｓ
		itemRepository.save(items);

		// 商品一覧画面にリダイレクト
		return "redirect:/items";
	}

	// Step5：削除機能を追加してください
	@PostMapping("/items/{id}/delete")
	public String delete(
			// @PathVariableでURL のパスの一部からパスを取得(変更するレコードのidを取得)
			@PathVariable("id") Integer id) {

		// deleteById()メソッドで指定のidのレコードを削除
		itemRepository.deleteById(id);

		// 商品一覧画面にリダイレクト
		// Postで再表示させないよう、Getリクエストにするためのリダイレクト
		return "redirect:/items";
	}

}