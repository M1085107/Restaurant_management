package com.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
	
	List<Restaurant> findByName(String res_name);
	List<Restaurant> findByLocation(String location);
	List<Restaurant> findByDistance(double min,double max);
	List<Restaurant> findByBudget(double min,double max);

}
