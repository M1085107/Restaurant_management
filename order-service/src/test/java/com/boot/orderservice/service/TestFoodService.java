package com.boot.orderservice.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.boot.model.Food;
import com.boot.repository.FoodRepository;
import com.boot.service.FoodService;
import com.boot.service.FoodServiceImp;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFoodService {

	@InjectMocks
	private FoodServiceImp foodService;
	
	@Mock
	private FoodRepository foodRepository;
	
	@Test
	public void testGetInfo() {
		Food food=new Food(3, "Onion rings", 120, 10, "Appetizers");
		Mockito.when(foodRepository.findById((long) 3)).thenReturn(Optional.of(food));
		assertThat(foodRepository.findById((long) 3)).isNotEmpty();
		assertThat(foodService.getInfo(3)).isEqualTo(food);
	}
	
	@Test
	public void testGetAllInfo() {
		Mockito.when(foodRepository.findAll()).thenReturn(new ArrayList<Food>());
		assertThat(foodService.getAllInfo()).isNotNull();
	}
	
	@Test
	public void testUpdateFoodList() {
		int row=1;
		Food food=new Food(3, "Onion rings", 120, 3, "Appetizers");
		List<Food> foodList=new ArrayList<Food>();
		foodList.add(food);
		Mockito.when(foodRepository.selectStock(food.getName())).thenReturn(food);
		Mockito.when(foodRepository.updateStock(Mockito.anyInt(), Mockito.anyString())).thenReturn(row);
		assertThat(foodService.updateFoodList(foodList)).isEqualTo("List is updated");
	}
}
