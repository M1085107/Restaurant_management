package com.boot.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.boot.model.Food;
import com.boot.model.Restaurant;
import com.boot.service.RestaurantService;
import com.boot.service.RestaurantServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RestaurantController.class)
public class TestRestaurantController {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RestaurantServiceImp restaurantService;
	
	@Test
	public void testGetAllList() throws Exception {
		String URI="/restaurant/get";
		List<Food> foodList= Arrays.asList(
				new Food(36, "Dal Makhni", 80, 10, "Main"),
				new Food(38, "Chicken Biryani", 250, 10, "Main"),
				new Food(33, "Korma", 220, 10, "Main"),
				new Food(35, "Vindaloo", 150, 10, "Main"),
				new Food(14, "Flatbread pizza", 200, 10, "Kid menu"),
				new Food(13, "Tomato soup", 140, 10, "Kid menu"),
				new Food(13, "Tomato soup", 140, 10, "Kid menu"),
				new Food(7, "Chicken pot pie", 250, 10, "Comfort food"),
				new Food(11, "Lasagna", 100, 10, "Comfort food"),
				new Food(2, "Cheddar Biscuits", 150, 10, "Appetizers"));
		
		Restaurant restaurant=new Restaurant(3, "Arsalan Restaurant", "Taltala", "Casual", 10, 1200, foodList);
		List<Restaurant> resList=new ArrayList<Restaurant>();
		
		Mockito.when(restaurantService.getAllRestaurant()).thenReturn(resList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.objMapper(resList);

		String outputJson = result.getResponse().getContentAsString();
		assertThat(outputJson).isEqualTo(expectedJson);
	}
	
	@Test
	public void testGetSingleList() throws Exception {
		String URI="/restaurant/get/3";
		List<Food> foodList= Arrays.asList(
				new Food(36, "Dal Makhni", 80, 10, "Main"),
				new Food(38, "Chicken Biryani", 250, 10, "Main"),
				new Food(33, "Korma", 220, 10, "Main"),
				new Food(35, "Vindaloo", 150, 10, "Main"),
				new Food(14, "Flatbread pizza", 200, 10, "Kid menu"),
				new Food(13, "Tomato soup", 140, 10, "Kid menu"),
				new Food(13, "Tomato soup", 140, 10, "Kid menu"),
				new Food(7, "Chicken pot pie", 250, 10, "Comfort food"),
				new Food(11, "Lasagna", 100, 10, "Comfort food"),
				new Food(2, "Cheddar Biscuits", 150, 10, "Appetizers"));
		
		Restaurant restaurant=new Restaurant(3, "Arsalan Restaurant", "Taltala", "Casual", 10, 1200, foodList);
		
		Mockito.when(restaurantService.getSingleRestaurant(Mockito.anyLong())).thenReturn(restaurant);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.objMapper(restaurant);

		String outputJson = result.getResponse().getContentAsString();
		assertThat(outputJson).isEqualTo(expectedJson);
	}
	
	@Test
	public void testGetbyLocation() throws Exception {
		String URI="/restaurant/location/Taltala";
		List<Food> foodList= Arrays.asList(
				new Food(36, "Dal Makhni", 80, 10, "Main"),
				new Food(38, "Chicken Biryani", 250, 10, "Main"),
				new Food(33, "Korma", 220, 10, "Main"),
				new Food(35, "Vindaloo", 150, 10, "Main"),
				new Food(14, "Flatbread pizza", 200, 10, "Kid menu"),
				new Food(13, "Tomato soup", 140, 10, "Kid menu"),
				new Food(13, "Tomato soup", 140, 10, "Kid menu"),
				new Food(7, "Chicken pot pie", 250, 10, "Comfort food"),
				new Food(11, "Lasagna", 100, 10, "Comfort food"),
				new Food(2, "Cheddar Biscuits", 150, 10, "Appetizers"));
		
		Restaurant restaurant=new Restaurant(3, "Arsalan Restaurant", "Taltala", "Casual", 10, 1200, foodList);
		List<Restaurant> restaurants=Arrays.asList(restaurant);
		
		Mockito.when(restaurantService.getRestaurantByLocation(Mockito.anyString())).thenReturn(restaurants);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.objMapper(restaurants);
		String outputJson = result.getResponse().getContentAsString();
		assertThat(outputJson).isEqualTo(expectedJson);
	}
	
	@Test
	public void testGetbyName() throws Exception {
		String URI="/restaurant/name/Arsalan Restaurant";
		List<Food> foodList= Arrays.asList(
				new Food(36, "Dal Makhni", 80, 10, "Main"),
				new Food(38, "Chicken Biryani", 250, 10, "Main"),
				new Food(33, "Korma", 220, 10, "Main"),
				new Food(35, "Vindaloo", 150, 10, "Main"));
		
		Restaurant restaurant=new Restaurant(3, "Arsalan Restaurant", "Taltala", "Casual", 10, 1200, foodList);
		List<Restaurant> restaurants=Arrays.asList(restaurant);
		
		Mockito.when(restaurantService.getRestaurantByName(Mockito.anyString())).thenReturn(restaurants);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.objMapper(restaurants);

		String outputJson = result.getResponse().getContentAsString();
		assertThat(outputJson).isEqualTo(expectedJson);
	}
	
	@Test
	public void testGetbyDistance() throws Exception {
		String URI="/restaurant/distance";
		List<Food> foodList= Arrays.asList(
				new Food(36, "Dal Makhni", 80, 10, "Main"),
				new Food(38, "Chicken Biryani", 250, 10, "Main"),
				new Food(33, "Korma", 220, 10, "Main"),
				new Food(35, "Vindaloo", 150, 10, "Main"));
		
		Restaurant restaurant=new Restaurant(3, "Arsalan Restaurant", "Taltala", "Casual", 10, 1200, foodList);
		List<Restaurant> restaurants=Arrays.asList(restaurant);
		
		HashMap<String,Double> requestMap=new HashMap<String, Double>();
		requestMap.put("min", (double) 6);
		requestMap.put("max", (double) 12);
		
		String resquestBody=this.objMapper(requestMap);
		
		Mockito.when(restaurantService.getRestaurantByDistance(requestMap)).thenReturn(restaurants);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).
				accept(MediaType.APPLICATION_JSON).content(resquestBody)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.objMapper(restaurants);

		String outputJson = result.getResponse().getContentAsString();
		assertThat(outputJson).isEqualTo(expectedJson);
	}
	
	@Test
	public void testGetbyBudget() throws Exception {
		String URI="/restaurant/budget";
		List<Food> foodList= Arrays.asList(
				new Food(36, "Dal Makhni", 80, 10, "Main"),
				new Food(38, "Chicken Biryani", 250, 10, "Main"),
				new Food(33, "Korma", 220, 10, "Main"),
				new Food(35, "Vindaloo", 150, 10, "Main"));
		
		Restaurant restaurant=new Restaurant(3, "Arsalan Restaurant", "Taltala", "Casual", 10, 1200, foodList);
		List<Restaurant> restaurants=Arrays.asList(restaurant);
		
		HashMap<String,Double> requestMap=new HashMap<String, Double>();
		requestMap.put("min", (double) 600);
		requestMap.put("max", (double) 1200);
		
		String resquestBody=this.objMapper(requestMap);
		
		Mockito.when(restaurantService.getRestaurantByBudget(requestMap)).thenReturn(restaurants);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).
				accept(MediaType.APPLICATION_JSON).content(resquestBody)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.objMapper(restaurants);

		String outputJson = result.getResponse().getContentAsString();
		assertThat(outputJson).isEqualTo(expectedJson);
	}

	//utility methods 
		public String objMapper(Object object) throws JsonProcessingException {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(object);
		}
}
