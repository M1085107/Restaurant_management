package com.boot.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.boot.model.Food;
import com.boot.model.Restaurant;
import com.boot.model.Restaurant_vs_food;
import com.boot.repository.ResVsFoodRepository;
import com.boot.repository.RestaurantRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRestaurantService {
	
	private final String order_url="http://localhost:9002/food/";
	
	@InjectMocks
	private RestaurantServiceImp restaurantService;
	
	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private RestaurantRepository restaurantRepository;
	
	@Mock
	private ResVsFoodRepository foodRepository;
	
	@Test
	public void testGetAllRestaurant() {
		
		Food food=new Food(36, "Dal Makhni", 80, 10, "Main");
		List<Food> foodList= Arrays.asList(food);
		
		Restaurant restaurant=new Restaurant(3, "Arsalan Restaurant", "Taltala", "Casual", 10, 1200, foodList);
		List<Restaurant> resList=new ArrayList<Restaurant>();
		resList.add(restaurant);
		Restaurant_vs_food restaurant_vs_food=new Restaurant_vs_food(2, 3, 36);
		ArrayList<Restaurant_vs_food> restaurant_vs_foods=new ArrayList<Restaurant_vs_food>();
		restaurant_vs_foods.add(restaurant_vs_food);
		
		Mockito.when(restaurantRepository.findAll()).thenReturn(resList);
		Mockito.when(foodRepository.findByResId(Mockito.anyLong())).thenReturn(restaurant_vs_foods);
		Mockito.when(restTemplate.getForObject(order_url+"get/"+restaurant_vs_food.getFood_id(),Food.class)).thenReturn(food);
		
		assertThat(restaurantService.getAllRestaurant()).isEqualTo(resList);
		
	}

	@Test
	public void testGetSingleRestaurant() {
		Food food=new Food(36, "Dal Makhni", 80, 10, "Main");
		List<Food> foodList= Arrays.asList(food);
		
		Restaurant restaurant=new Restaurant(3, "Arsalan Restaurant", "Taltala", "Casual", 10, 1200, foodList);
		
		Restaurant_vs_food restaurant_vs_food=new Restaurant_vs_food(2, 3, 36);
		ArrayList<Restaurant_vs_food> restaurant_vs_foods=new ArrayList<Restaurant_vs_food>();
		restaurant_vs_foods.add(restaurant_vs_food);
		
		Mockito.when(restaurantRepository.findById((long) 3)).thenReturn(Optional.of(restaurant));
		Mockito.when(foodRepository.findByResId(Mockito.anyLong())).thenReturn(restaurant_vs_foods);
		Mockito.when(restTemplate.getForObject(order_url+"get/"+restaurant_vs_food.getFood_id(),Food.class)).thenReturn(food);
		
		assertThat(restaurantService.getSingleRestaurant(restaurant.getId())).isEqualTo(restaurant);
		
	}
	
	@Test
	public void testGetRestaurantByLocation() {
		
		Food food=new Food(36, "Dal Makhni", 80, 10, "Main");
		List<Food> foodList= Arrays.asList(food);
		
		Restaurant restaurant=new Restaurant(3, "Arsalan Restaurant", "Taltala", "Casual", 10, 1200, foodList);
		List<Restaurant> resList=new ArrayList<Restaurant>();
		resList.add(restaurant);
		Restaurant_vs_food restaurant_vs_food=new Restaurant_vs_food(2, 3, 36);
		ArrayList<Restaurant_vs_food> restaurant_vs_foods=new ArrayList<Restaurant_vs_food>();
		restaurant_vs_foods.add(restaurant_vs_food);
		
		Mockito.when(restaurantRepository.findByLocation("%"+restaurant.getLocation()+"%")).thenReturn(resList);
		Mockito.when(foodRepository.findByResId(Mockito.anyLong())).thenReturn(restaurant_vs_foods);
		Mockito.when(restTemplate.getForObject(order_url+"get/"+restaurant_vs_food.getFood_id(),Food.class)).thenReturn(food);
		
		assertThat(restaurantService.getRestaurantByLocation(restaurant.getLocation())).isEqualTo(resList);
		
	}
	
	@Test
	public void testGetRestaurantByName() {
		
		Food food=new Food(36, "Dal Makhni", 80, 10, "Main");
		List<Food> foodList= Arrays.asList(food);
		
		Restaurant restaurant=new Restaurant(3, "Arsalan Restaurant", "Taltala", "Casual", 10, 1200, foodList);
		List<Restaurant> resList=new ArrayList<Restaurant>();
		resList.add(restaurant);
		Restaurant_vs_food restaurant_vs_food=new Restaurant_vs_food(2, 3, 36);
		ArrayList<Restaurant_vs_food> restaurant_vs_foods=new ArrayList<Restaurant_vs_food>();
		restaurant_vs_foods.add(restaurant_vs_food);
		
		Mockito.when(restaurantRepository.findByName("%"+restaurant.getLocation()+"%")).thenReturn(resList);
		Mockito.when(foodRepository.findByResId(Mockito.anyLong())).thenReturn(restaurant_vs_foods);
		Mockito.when(restTemplate.getForObject(order_url+"get/"+restaurant_vs_food.getFood_id(),Food.class)).thenReturn(food);
		
		assertThat(restaurantService.getRestaurantByName(restaurant.getLocation())).isEqualTo(resList);
		
	}
	
	@Test
	public void testGetRestaurantByDistance() {
		
		Food food=new Food(36, "Dal Makhni", 80, 10, "Main");
		List<Food> foodList= Arrays.asList(food);
		
		Restaurant restaurant=new Restaurant(3, "Arsalan Restaurant", "Taltala", "Casual", 10, 1200, foodList);
		List<Restaurant> resList=new ArrayList<Restaurant>();
		resList.add(restaurant);
		Restaurant_vs_food restaurant_vs_food=new Restaurant_vs_food(2, 3, 36);
		ArrayList<Restaurant_vs_food> restaurant_vs_foods=new ArrayList<Restaurant_vs_food>();
		restaurant_vs_foods.add(restaurant_vs_food);
		
		HashMap<String,Double> requestMap=new HashMap<String, Double>();
		requestMap.put("min", (double) 6);
		requestMap.put("max", (double) 12);
		
		Mockito.when(restaurantRepository.findByDistance(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(resList);
		Mockito.when(foodRepository.findByResId(Mockito.anyLong())).thenReturn(restaurant_vs_foods);
		Mockito.when(restTemplate.getForObject(order_url+"get/"+restaurant_vs_food.getFood_id(),Food.class)).thenReturn(food);
		
		assertThat(restaurantService.getRestaurantByDistance(requestMap)).isEqualTo(resList);
		
	}
	
	@Test
	public void testGetRestaurantByBudget() {
		
		Food food=new Food(36, "Dal Makhni", 80, 10, "Main");
		List<Food> foodList= Arrays.asList(food);
		
		Restaurant restaurant=new Restaurant(3, "Arsalan Restaurant", "Taltala", "Casual", 10, 1200, foodList);
		List<Restaurant> resList=new ArrayList<Restaurant>();
		resList.add(restaurant);
		Restaurant_vs_food restaurant_vs_food=new Restaurant_vs_food(2, 3, 36);
		ArrayList<Restaurant_vs_food> restaurant_vs_foods=new ArrayList<Restaurant_vs_food>();
		restaurant_vs_foods.add(restaurant_vs_food);
		
		HashMap<String,Double> requestMap=new HashMap<String, Double>();
		requestMap.put("min", (double) 600);
		requestMap.put("max", (double) 1200);
		
		Mockito.when(restaurantRepository.findByBudget(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(resList);
		Mockito.when(foodRepository.findByResId(Mockito.anyLong())).thenReturn(restaurant_vs_foods);
		Mockito.when(restTemplate.getForObject(order_url+"get/"+restaurant_vs_food.getFood_id(),Food.class)).thenReturn(food);
		
		assertThat(restaurantService.getRestaurantByBudget(requestMap)).isEqualTo(resList);
		
	}
}
