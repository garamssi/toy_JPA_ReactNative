package com.project.toy.template.repository;

import com.project.toy.template.model.entitiy.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ItemRepository extends JpaRepository< Item, Long >, QuerydslPredicateExecutor<Item> {

}
