package com.boot.service;

import java.util.List;

import com.boot.model.Food;

public interface FoodService {
	
	public Food getInfo(long id);
	
	public List<Food> getAllInfo();
	
	public String updateFoodList(List<Food> foods);

}
