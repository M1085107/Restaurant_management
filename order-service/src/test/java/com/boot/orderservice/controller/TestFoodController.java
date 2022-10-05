package com.boot.orderservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.boot.controller.FoodController;
import com.boot.model.Food;
import com.boot.service.FoodService;
import com.boot.util.UtilClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FoodController.class)
public class TestFoodController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FoodService foodService;

	@Test
	public void testGetInfo() throws Exception {

		Food food = new Food(3, "Onion rings", 120, 10, "Appetizers");

		String URI = "/food/get/3";

		Mockito.when(foodService.getInfo(Mockito.anyLong())).thenReturn(food);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.objMapper(food);

		String outputJson = result.getResponse().getContentAsString();
		assertThat(outputJson).isEqualTo(expectedJson);
	}
	
	@Test
	public void testGetAllInfo() throws Exception{

		Food food = new Food(3, "Onion rings", 120, 10, "Appetizers");
		List<Food> foodList=new ArrayList<Food>();
		foodList.add(food);

		String URI = "/food/get";

		Mockito.when(foodService.getAllInfo()).thenReturn(foodList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.objMapper(foodList);

		String outputJson = result.getResponse().getContentAsString();
		assertThat(outputJson).isEqualTo(expectedJson);
	}

	@Test
	public void testUpdateByKeeper() throws Exception {
		List<Food> mockFood = Arrays.asList(
				new Food(4, "French fries", 100, 2, "Appetizers"),
				new Food(13, "Tomato soup", 140, 3, "Kid menu"), 
				new Food(10, "Meatloaf", 180, 5, "Comfort food"));
		
		String inputInJson = this.objMapper(mockFood);
		
		String expectedOutput="List is updated";
		
		String URI = "/food/update";
		
		Mockito.when(foodService.updateFoodList(Mockito.anyList())).thenReturn(expectedOutput);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		String actualOutput = response.getContentAsString();
		
		assertThat(actualOutput).isEqualTo(expectedOutput);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	//util method
	public String objMapper(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
