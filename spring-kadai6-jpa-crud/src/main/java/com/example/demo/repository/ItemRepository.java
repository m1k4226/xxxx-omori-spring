/**
 * Step1：商品一覧画面を作りなさい（復習）
 */

package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Item;

// public：どこからでもアクセスできる。
// interface ItemRepository：ItemRepositoryというインターフェース。
// extends JpaRepository<Item, Integer>：JpaRepositoryという別のインターフェースを継承している。引数は<テーブル名,主キーのデータ型>
public interface ItemRepository extends JpaRepository<Item, Integer> {

}