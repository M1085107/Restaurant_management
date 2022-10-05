package com.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boot.model.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long>{
	
	public Food selectStock(String name);
	
	@Modifying
	@Transactional
	@Query("UPDATE FOOD u SET u.stock= ?1 where u.name like ?2 ")
	public int updateStock(int stock,String name);
	
	@Modifying
	@Transactional
	@Query("UPDATE FOOD u SET u.stock = (SELECT u.stock FROM FOOD u WHERE u.name= ?1 )+ ?2 WHERE u.name= ?1")
	public void testStock(String name,int nulmber);

}
