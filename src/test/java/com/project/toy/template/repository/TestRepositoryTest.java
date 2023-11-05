package com.project.toy.template.repository;

import com.project.toy.enums.ItemSellStatus;
import com.project.toy.template.model.entitiy.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class TestRepositoryTest {

	@Autowired
	TestRepository testRepository;

	@Test
	@DisplayName( "테스트 저장" )
	public void createItemTest(){
		Item item = new Item();
		item.setItemName( "테스트 상품" );
		item.setPrice( 10000 );
		item.setItemDetail("테스트 상품 상세 설명");
		item.setItemSellStatus( ItemSellStatus.SELL );
		item.setStockNumber( 100 );
		item.setRegTime( LocalDateTime.now() );
		item.setUpdateTime( LocalDateTime.now() );
		Item saveItem = testRepository.save( item );
		System.out.println(saveItem.toString());
	}

	@Test
	@DisplayName( "이건되나요" )
	public void test() {
		System.out.println("aaaa");
	}
}