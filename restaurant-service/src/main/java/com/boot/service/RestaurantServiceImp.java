package com.boot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.boot.model.Food;
import com.boot.model.Restaurant;
import com.boot.model.Restaurant_vs_food;
import com.boot.repository.ResVsFoodRepository;
import com.boot.repository.RestaurantRepository;

@Service
public class RestaurantServiceImp implements RestaurantService {
	
	private final String order_url="http://ORDERSERVICE/food/";
//	private final String order_url="http://localhost:9002/food/";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private ResVsFoodRepository foodRepository;

	@Override
	public List<Restaurant> getAllRestaurant() {
		
		List<Restaurant> allRestaurants=restaurantRepository.findAll();
		allRestaurants.forEach((restaurant)->{
			List<Restaurant_vs_food> allFood=foodRepository.findByResId(restaurant.getId());
			List<Food> foodList=new ArrayList<Food>();
			for (Restaurant_vs_food food : allFood) {
				Food oldFood= restTemplate.getForObject(order_url+"get/"+food.getFood_id(), Food.class);
				foodList.add(oldFood);
			}
			restaurant.setFoodList(foodList);
			
		});
		return allRestaurants;
	}

	@Override
	public Restaurant getSingleRestaurant(long res_id) {
		Restaurant newRes=restaurantRepository.findById(res_id).orElse(null);
		List<Restaurant_vs_food> allFood=foodRepository.findByResId(newRes.getId());
		List<Food> foodList=new ArrayList<Food>();
		for (Restaurant_vs_food food : allFood) {
			Food oldFood= restTemplate.getForObject(order_url+"get/"+food.getFood_id(), Food.class);
			foodList.add(oldFood);
		}
		newRes.setFoodList(foodList);
		return newRes;
	}

	@Override
	public List<Restaurant> getRestaurantByLocation(String location) {
		List<Restaurant> allRestaurants=restaurantRepository.findByLocation("%"+location+"%");
		allRestaurants.forEach((restaurant)->{
			List<Restaurant_vs_food> allFood=foodRepository.findByResId(restaurant.getId());
			List<Food> foodList=new ArrayList<Food>();
			for (Restaurant_vs_food food : allFood) {
				Food oldFood= restTemplate.getForObject(order_url+"get/"+food.getFood_id(), Food.class);
				foodList.add(oldFood);
			}
			restaurant.setFoodList(foodList);
			
		});
		return allRestaurants;
	}

	@Override
	public List<Restaurant> getRestaurantByName(String name) {
		List<Restaurant> allRestaurants=restaurantRepository.findByName("%"+name+"%");
		allRestaurants.forEach((restaurant)->{
			List<Restaurant_vs_food> allFood=foodRepository.findByResId(restaurant.getId());
			List<Food> foodList=new ArrayList<Food>();
			for (Restaurant_vs_food food : allFood) {
				Food oldFood= restTemplate.getForObject(order_url+"get/"+food.getFood_id(), Food.class);
				foodList.add(oldFood);
			}
			restaurant.setFoodList(foodList);
			
		});
		return allRestaurants;
	}

	@Override
	public List<Restaurant> getRestaurantByDistance(HashMap<String, Double> distance) {
		List<Restaurant> allRestaurants=restaurantRepository.findByDistance(distance.get("min"), distance.get("max"));
		allRestaurants.forEach((restaurant)->{
			List<Restaurant_vs_food> allFood=foodRepository.findByResId(restaurant.getId());
			List<Food> foodList=new ArrayList<Food>();
			for (Restaurant_vs_food food : allFood) {
				Food oldFood= restTemplate.getForObject(order_url+"get/"+food.getFood_id(), Food.class);
				foodList.add(oldFood);
			}
			restaurant.setFoodList(foodList);
			
		});
		return allRestaurants;
	}

	@Override
	public List<Restaurant> getRestaurantByBudget(HashMap<String, Double> budget) {
		List<Restaurant> allRestaurants=restaurantRepository.findByBudget(budget.get("min"), budget.get("max"));
		allRestaurants.forEach((restaurant)->{
			List<Restaurant_vs_food> allFood=foodRepository.findByResId(restaurant.getId());
			List<Food> foodList=new ArrayList<Food>();
			for (Restaurant_vs_food food : allFood) {
				Food oldFood= restTemplate.getForObject(order_url+"get/"+food.getFood_id(), Food.class);
				foodList.add(oldFood);
			}
			restaurant.setFoodList(foodList);
			
		});
		return allRestaurants;
	}

}
