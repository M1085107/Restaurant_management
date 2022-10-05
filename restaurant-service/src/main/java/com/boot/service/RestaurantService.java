package com.boot.service;

import java.util.HashMap;
import java.util.List;

import com.boot.model.Restaurant;

public interface RestaurantService {
	
	public List<Restaurant> getAllRestaurant();
	
	public Restaurant getSingleRestaurant(long res_id);
	
	public List<Restaurant> getRestaurantByLocation(String location);
	
	public List<Restaurant> getRestaurantByName(String name);
	
	public List<Restaurant> getRestaurantByDistance(HashMap<String, Double> distance);
	
	public List<Restaurant> getRestaurantByBudget(HashMap<String, Double> budget);

}
