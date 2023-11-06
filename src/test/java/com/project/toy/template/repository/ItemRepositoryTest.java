package com.project.toy.template.repository;

import com.project.toy.enums.ItemSellStatus;
import com.project.toy.template.model.entitiy.Item;
import com.project.toy.template.model.entitiy.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemRepositoryTest {

	@Autowired
	ItemRepository itemRepository;

	@PersistenceContext
	EntityManager em;

	@Test
	@DisplayName( "테스트 저장" )
	public void createItemList(){
		Item item = new Item();
		item.setItemName( "테스트 상품" );
		item.setPrice( 10000 );
		item.setItemDetail("테스트 상품 상세 설명");
		item.setItemSellStatus( ItemSellStatus.SELL );
		item.setStockNumber( 100 );
		item.setRegTime( LocalDateTime.now() );
		item.setUpdateTime( LocalDateTime.now() );
		Item saveItem = itemRepository.save( item );
		System.out.println(saveItem.toString());
	}

	@Test
	@DisplayName( "Querydsl 조회 테스트1" )
	public void queryDslTest() {
		this.createItemList();
		JPAQueryFactory queryFactory = new JPAQueryFactory( em );
		QItem qItem = QItem.item;
		JPAQuery<Item> query = queryFactory.selectFrom( qItem )
			.where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
			.where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
			.orderBy( qItem.price.desc() );

		List<Item> itemList = query.fetch();

		for (Item item : itemList) {
			System.out.println(item.toString());
		}


	}

	public void createItemList2() {
		for (int i=1; i<=5; i++){
			Item item = new Item();
			item.setItemName( "테스트 상품" + i );
			item.setPrice( 10000 + i );
			item.setItemDetail( "테스트 상품 상세 설명" + i );
			item.setItemSellStatus( ItemSellStatus.SELL );
			item.setStockNumber( 100 );
			item.setRegTime( LocalDateTime.now() );
			item.setUpdateTime( LocalDateTime.now() );
			itemRepository.save( item );
		}

		for ( int i=6; i<=10; i++){
			Item item = new Item();
			item.setItemName( "테스트 상품" + i );
			item.setPrice( 10000 + i );
			item.setItemDetail( "테스트 상품 상세 설명" + i );
			item.setItemSellStatus( ItemSellStatus.SOLD_OUT );
			item.setStockNumber( 0 );
			item.setRegTime( LocalDateTime.now() );
			item.setUpdateTime( LocalDateTime.now() );
			itemRepository.save( item );
		}
	}

	@Test
	@DisplayName( "상품 Querydsl 조회 테스트 2" )
	public void queryDslTest2(){

		this.createItemList2();

		BooleanBuilder booleanBuilder = new BooleanBuilder();
		QItem item = QItem.item;
		String itemDetail = "테스트 상품 상세 설명";
		int price = 10003;
		String itemSellStat = "SELL";

		booleanBuilder.and(item.itemDetail.like( "%" + itemDetail + "%" ));
		booleanBuilder.and(item.price.gt(price));

		if( itemSellStat.equals( ItemSellStatus.SELL ) ) {
			booleanBuilder.and(item.itemSellStatus.eq( ItemSellStatus.SELL ));
		}

		Pageable pageable = PageRequest.of( 0, 5 );
		Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
		System.out.println("total elements : " + itemPagingResult.getTotalElements());

		List<Item> resultItemList = itemPagingResult.getContent();
		for(Item resultItem : resultItemList){
			System.out.println(resultItem.toString());
		}

	}


}