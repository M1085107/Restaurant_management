package com.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.model.Food;
import com.boot.repository.FoodRepository;

@Service
public class FoodServiceImp implements FoodService {
	
	@Autowired
	private FoodRepository foodRepository;

	@Override
	public Food getInfo(long id) {
		return foodRepository.findById(id).filter((food)->food.getStock()>0).orElse(null);
	}

	@Override
	public List<Food> getAllInfo() {
		return foodRepository.findAll();
	}

	@Override
	public String updateFoodList(List<Food> foods) {
		Food foodList=null;
		for (Food food : foods) {
			foodList=foodRepository.selectStock(food.getName());
			foodRepository.updateStock(foodList.getStock()+food.getStock(), food.getName());
		}
	return "List is updated";
	}
	
	

}
