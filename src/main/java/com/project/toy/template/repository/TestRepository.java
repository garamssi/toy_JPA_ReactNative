package com.project.toy.template.repository;

import com.project.toy.template.model.entitiy.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository< Item, Long > {

}
