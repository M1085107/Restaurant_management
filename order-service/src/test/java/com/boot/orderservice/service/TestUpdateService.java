package com.boot.orderservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.boot.model.Cart;
import com.boot.model.Food;
import com.boot.model.Item;
import com.boot.repository.FoodRepository;
import com.boot.repository.OrderRepository;
import com.boot.service.FoodService;
import com.boot.service.UpdateService;
import com.boot.service.UpdateServiceImp;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUpdateService {
	
	@InjectMocks
	private UpdateServiceImp updateService;
	
	@Mock
	private FoodRepository foodRepository;
	
	@Mock
	private OrderRepository orderRepository;
	
	
	
	@Test
	public void testUpdateDelivery() {
		int row = 1;
		Cart cart = new Cart();
		cart.setId(74);
		cart.setCus_name("Akash");
		cart.setCus_number("7465372334");
		cart.setDeliverd_yn("N");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("Y");
		cart.setRes_id(5);
		cart.setOrd_num("163321908");
		
		List<Cart> cartList=new ArrayList<Cart>();
		cartList.add(cart);
		
		Mockito.when(orderRepository.searchForReleaseOrder()).thenReturn(cartList);
		Mockito.when(orderRepository.updateForReleaseOrder()).thenReturn(row);
		assertThat(updateService.updateDelivery()).isEqualTo(cartList);
	}
	
	@Test
	public void testStuffProductStock() {
		int row=1;
		Item item=new Item(0, "Cheddar Biscuits", 3, 150, null);
		List<Item> allItem = Arrays.asList(item);
		
		Food food = new Food(2, "Cheddar Biscuits", 150, 20, "Appetizers");
		Mockito.when(foodRepository.selectStock(Mockito.anyString())).thenReturn(food);
		Mockito.when(foodRepository.updateStock(Mockito.anyInt(),Mockito.anyString())).thenReturn(row);
		assertThat(updateService.stuffProductStock(allItem)).isTrue();
	}
	
	@Test
	public void testDestuffProductStock() {
		int row=1;
		Item item1=new Item(0, "Flatbread pizza", 2, 200, null);
		Item item2=new Item(0, "Cheddar Biscuits", 3, 150, null);
		List<Item> allItem = Arrays.asList(item1,item2);
		
		Food food = new Food(2, "Cheddar Biscuits", 150, 20, "Appetizers");
		Mockito.when(foodRepository.selectStock(Mockito.anyString())).thenReturn(food);
		Mockito.when(foodRepository.updateStock(Mockito.anyInt(), Mockito.anyString())).thenReturn(row);
		assertThat(updateService.deStuffProductStock(allItem)).isTrue();
	}
	
	

}
