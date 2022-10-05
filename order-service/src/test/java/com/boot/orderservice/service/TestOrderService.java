package com.boot.orderservice.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import com.boot.service.OrderService;
import com.boot.service.OrderServiceImp;
import com.boot.service.UpdateService;
import com.boot.service.UpdateServiceImp;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestOrderService {
	
	@InjectMocks
	private OrderServiceImp orderService;
	
	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private FoodRepository foodRepository;
	
	@Mock
	private UpdateServiceImp updateService;
	
	@Test
	public void testPositiveCreateOrderService() {
		
		Food food = new Food(2, "Cheddar Biscuits", 150, 20, "Appetizers");
		List<Item> allItem = Arrays.asList(new Item(0, "Cheddar Biscuits", 150, 3, null));

		Cart cart = new Cart();
		cart.setCus_name("Bikash");
		cart.setCus_number("5447253786");
		cart.setDeliverd_yn("N");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("N");
		cart.setRes_id(4);
		cart.setAllItem(allItem);
		
		Mockito.when(updateService.stuffProductStock(allItem)).thenReturn(true);
		Mockito.when(foodRepository.selectStock(allItem.get(0).getName())).thenReturn(food);
		Mockito.when(orderRepository.save(cart)).thenReturn(cart);
		
		assertThat(orderService.createOrderService(cart)).isNotNull();
	}
	
	@Test
	public void testNegetiveCreateOrderService() {
		
		Food food = new Food(2, "Cheddar Biscuits", 150, 20, "Appetizers");
		List<Item> allItem = Arrays.asList(new Item(0, "Cheddar Biscuits", 150, 3, null));

		Cart cart = new Cart();
		cart.setCus_name("Bikash");
		cart.setCus_number("5447253786");
		cart.setDeliverd_yn("N");
		cart.setPaid_yn("N");
		cart.setPrepared_yn("N");
		cart.setRes_id(4);
		cart.setAllItem(allItem);
		
		Mockito.when(updateService.stuffProductStock(allItem)).thenReturn(true);
		Mockito.when(foodRepository.selectStock(allItem.get(0).getName())).thenReturn(food);
		Mockito.when(orderRepository.save(cart)).thenReturn(cart);
		
		assertThat(orderService.createOrderService(cart)).isEqualTo("Please make the payment before placing the order");
	}
	
	@Test
	public void testPositiveCheckOrderService() throws Exception {
		Food food = new Food(2, "Cheddar Biscuits", 150, 20, "Appetizers");
		double totalPrice=0;
		List<Item> allItem = Arrays.asList(new Item(0, "Cheddar Biscuits", 3, 150, null));
		
		Cart cart = new Cart();
		cart.setCus_name("Akash");
		cart.setCus_number("5447253786");
		cart.setDeliverd_yn("N");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("N");
		cart.setRes_id(4);
		cart.setAllItem(allItem);
		
		for (Item item : allItem) {
			totalPrice+=item.getPrice()*item.getQuantity();
		}
		
		Mockito.when(foodRepository.selectStock(Mockito.anyString())).thenReturn(food);
		
		assertThat(orderService.checkOrderService(cart)).isEqualTo("Total is "+String.valueOf(totalPrice)+". Kindly make payment before placing the order");
	}
	
	@Test
	public void testNegetiveCheckOrderService() throws Exception {
		Food food = new Food(2, "Cheddar Biscuits", 150, 2, "Appetizers");
		double totalPrice=0;
		List<Item> allItem = Arrays.asList(new Item(0, "Cheddar Biscuits", 8, 150, null));
		
		Cart cart = new Cart();
		cart.setCus_name("Akash");
		cart.setCus_number("5447253786");
		cart.setDeliverd_yn("N");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("N");
		cart.setRes_id(4);
		cart.setAllItem(allItem);
		
		for (Item item : allItem) {
			totalPrice+=item.getPrice()*item.getQuantity();
		}
		
		Mockito.when(foodRepository.selectStock(Mockito.anyString())).thenReturn(food);
		
		assertThat(orderService.checkOrderService(cart)).isEqualTo("Cheddar Biscuits item in not available");
	}
	
	@Test
	public void testDisplayOrderFromCustomer() {
		List<Item> allItem = Arrays.asList(new Item(0, "Cheddar Biscuits", 3, 150, null));

		Cart cart = new Cart();
		cart.setCus_name("Akash");
		cart.setCus_number("5447253786");
		cart.setDeliverd_yn("N");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("N");
		cart.setRes_id(4);
		cart.setOrd_num("174523912");
		cart.setAllItem(allItem);
		
		Mockito.when(orderRepository.searchForOrderNumber(cart.getOrd_num())).thenReturn(cart);
		
		assertThat(orderService.displayOrderFromCustomer(cart.getOrd_num())).isEqualTo(cart);
		
	}
	
	@Test
	public void testUpdateOrderFromCustomer_failedforCanceledOrder() throws Exception {
		
		Item item=new Item(0, "Cheddar Biscuits", 3, 150, null);
		List<Item> allItem = new ArrayList<Item>();
		allItem.add(item);

		Cart cart = new Cart();
		cart.setCus_name("Akash");
		cart.setCus_number("5447253786");
		cart.setDeliverd_yn("N");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("N");
		cart.setRes_id(4);
		cart.setStatus("C");
		cart.setOrd_num("174523912");
		cart.setAllItem(allItem);
		
		Mockito.when(orderRepository.searchForOrderNumber(cart.getOrd_num())).thenReturn(cart);
		
		Mockito.when(updateService.deStuffProductStock(allItem)).thenReturn(true);
		Mockito.when(updateService.stuffProductStock(allItem)).thenReturn(true);
		Mockito.when(orderRepository.save(cart)).thenReturn(cart);
		
		assertThat(orderService.updateOrderFromCustomer(cart)).isEqualTo("174523912 order is already canceled.");
	}
	
	@Test
	public void testUpdateOrderFromCustomer_failedforPreparedOrder() throws Exception {
		
		Item item=new Item(0, "Cheddar Biscuits", 3, 150, null);
		List<Item> allItem = new ArrayList<Item>();
		allItem.add(item);

		Cart cart = new Cart();
		cart.setCus_name("Akash");
		cart.setCus_number("5447253786");
		cart.setDeliverd_yn("N");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("Y");
		cart.setRes_id(4);
		cart.setStatus("P");
		cart.setOrd_num("174523912");
		cart.setAllItem(allItem);
		
		Mockito.when(orderRepository.searchForOrderNumber(cart.getOrd_num())).thenReturn(cart);
		
		Mockito.when(updateService.deStuffProductStock(allItem)).thenReturn(true);
		Mockito.when(updateService.stuffProductStock(allItem)).thenReturn(true);
		Mockito.when(orderRepository.save(cart)).thenReturn(cart);
		
		assertThat(orderService.updateOrderFromCustomer(cart)).isEqualTo("Order is prepared. Can not place order right now");
	}
	
	@Test
	public void testEqualUpdateOrderFromCustomer_failedforNotinList() throws Exception {
		
		Item item=new Item(0, "Cheddar Biscuits", 3, 150, null);
		List<Item> allItem = new ArrayList<Item>();
		allItem.add(item);

		Cart cart = new Cart();
		cart.setCus_name("Akash");
		cart.setCus_number("5447253786");
		cart.setDeliverd_yn("N");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("N");
		cart.setRes_id(4);
		cart.setStatus("P");
		cart.setOrd_num("174523912");
		cart.setAllItem(allItem);
		
		Mockito.when(orderRepository.searchForOrderNumber(cart.getOrd_num())).thenReturn(cart);
		
		Mockito.when(updateService.deStuffProductStock(allItem)).thenReturn(true);
		Mockito.when(updateService.stuffProductStock(allItem)).thenReturn(false);
		Mockito.when(orderRepository.save(cart)).thenReturn(cart);
		
		assertThat(orderService.updateOrderFromCustomer(cart)).isEqualTo("Food is not available");
	}
	@Test
	public void testUpdateOrderFromCustomer_forEqualList() throws Exception {
		
		Item item=new Item(0, "Cheddar Biscuits", 3, 150, null);
		List<Item> allItem = new ArrayList<Item>();
		allItem.add(item);

		Cart cart = new Cart();
		cart.setCus_name("Akash");
		cart.setCus_number("5447253786");
		cart.setDeliverd_yn("N");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("N");
		cart.setRes_id(4);
		cart.setStatus("P");
		cart.setOrd_num("174523912");
		cart.setAllItem(allItem);
		
		Mockito.when(orderRepository.searchForOrderNumber(cart.getOrd_num())).thenReturn(cart);
		
		Mockito.when(updateService.deStuffProductStock(allItem)).thenReturn(true);
		Mockito.when(updateService.stuffProductStock(allItem)).thenReturn(true);
		Mockito.when(orderRepository.save(cart)).thenReturn(cart);
		
		assertThat(orderService.updateOrderFromCustomer(cart)).isEqualTo("Thank you.visit again");
	}
	
	@Test
	public void testGreaterUpdateOrderFromCustomer() throws Exception {
		
		Item item=new Item(0, "Cheddar Biscuits", 3, 150, null);
		List<Item> oldItem = new ArrayList<Item>();
		oldItem.add(item);
		
		Item item1=new Item(0, "Biscuits", 3, 100, null);
		Item item2=new Item(0, "Cheddar", 2, 150, null);
		List<Item> newItem = new ArrayList<Item>();
		newItem.add(item1);
		newItem.add(item2);

		Cart cart1 = new Cart();
		cart1.setCus_name("Akash");
		cart1.setCus_number("5447253786");
		cart1.setDeliverd_yn("N");
		cart1.setPaid_yn("Y");
		cart1.setPrepared_yn("N");
		cart1.setRes_id(4);
		cart1.setStatus("P");
		cart1.setOrd_num("174523912");
		cart1.setAllItem(oldItem);
		
		Cart cart2 = new Cart();
		cart2.setCus_name("Akash");
		cart2.setCus_number("5447253786");
		cart2.setDeliverd_yn("N");
		cart2.setPaid_yn("Y");
		cart2.setPrepared_yn("N");
		cart2.setRes_id(4);
		cart2.setStatus("P");
		cart2.setOrd_num("174523912");
		cart2.setAllItem(newItem);
		
		Mockito.when(orderRepository.searchForOrderNumber(cart1.getOrd_num())).thenReturn(cart1);
		
		Mockito.when(updateService.deStuffProductStock(oldItem)).thenReturn(true);
		Mockito.when(updateService.stuffProductStock(newItem)).thenReturn(true);
		Mockito.when(orderRepository.save(cart2)).thenReturn(cart2);
		
		assertThat(orderService.updateOrderFromCustomer(cart2)).isEqualTo("150.0 will be repaid");
	}
	
	@Test
	public void testLessUpdateOrderFromCustomer() throws Exception {
		
		Item item=new Item(0, "Cheddar Biscuits", 3, 150, null);
		List<Item> newItem = new ArrayList<Item>();
		newItem.add(item);
		
		Item item1=new Item(0, "Biscuits", 3, 100, null);
		Item item2=new Item(0, "Cheddar", 2, 150, null);
		List<Item> oldItem = new ArrayList<Item>();
		oldItem.add(item1);
		oldItem.add(item2);

		Cart cart1 = new Cart();
		cart1.setCus_name("Akash");
		cart1.setCus_number("5447253786");
		cart1.setDeliverd_yn("N");
		cart1.setPaid_yn("Y");
		cart1.setPrepared_yn("N");
		cart1.setRes_id(4);
		cart1.setStatus("P");
		cart1.setOrd_num("174523912");
		cart1.setAllItem(oldItem);
		
		Cart cart2 = new Cart();
		cart2.setCus_name("Akash");
		cart2.setCus_number("5447253786");
		cart2.setDeliverd_yn("N");
		cart2.setPaid_yn("Y");
		cart2.setPrepared_yn("N");
		cart2.setRes_id(4);
		cart2.setStatus("P");
		cart2.setOrd_num("174523912");
		cart2.setAllItem(newItem);
		
		Mockito.when(orderRepository.searchForOrderNumber(cart1.getOrd_num())).thenReturn(cart1);
		
		Mockito.when(updateService.deStuffProductStock(oldItem)).thenReturn(true);
		Mockito.when(updateService.stuffProductStock(newItem)).thenReturn(true);
		Mockito.when(orderRepository.save(cart2)).thenReturn(cart2);
		
		assertThat(orderService.updateOrderFromCustomer(cart2)).isEqualTo("150.0 is refunded");
	}
	
	@Test
	public void testUpdateOrderForPreparation() {
		
		Cart cart = new Cart();
		cart.setCus_name("Akash");
		cart.setCus_number("5447253786");
		cart.setDeliverd_yn("N");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("N");
		cart.setRes_id(4);
		cart.setStatus("P");
		cart.setOrd_num("174523912");
		
		Mockito.when(orderRepository.searchForOrderNumber("174523912")).thenReturn(cart);
		cart.setPrepared_yn("Y");
		Mockito.when(orderRepository.save(cart)).thenReturn(cart);
		
		assertThat(orderService.updateOrderForPreparation("174523912")).isEqualTo(cart);
	}
	
	@Test
	public void testPositiveCancelOrder() {
		double refund=0;
		
		Item item=new Item(0, "Cheddar Biscuits", 3, 150, null);
		List<Item> allItem = new ArrayList<Item>();
		allItem.add(item);
		
		for (Item item2 : allItem) {
			refund+=item2.getPrice()*item2.getQuantity();
		}
		
		Cart cart = new Cart();
		cart.setCus_name("Akash");
		cart.setCus_number("5447253786");
		cart.setDeliverd_yn("N");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("N");
		cart.setRes_id(4);
		cart.setStatus("P");
		cart.setOrd_num("174523912");
		cart.setAllItem(allItem);
		
		Mockito.when(orderRepository.searchForOrderNumber("174523912")).thenReturn(cart);
		Mockito.when(updateService.deStuffProductStock(allItem)).thenReturn(true);
		Mockito.when(orderRepository.save(cart)).thenReturn(cart);
		
		assertThat(orderService.cancelOrder("174523912")).isEqualTo(String.valueOf(refund)+" will be refunded");
	}

	@Test
	public void testNegetiveCancelOrder() {
		double refund=0;
		
		Item item=new Item(0, "Cheddar Biscuits", 3, 150, null);
		List<Item> allItem = new ArrayList<Item>();
		allItem.add(item);
		
		for (Item item2 : allItem) {
			refund+=item2.getPrice()*item2.getQuantity();
		}
		
		Cart cart = new Cart();
		cart.setCus_name("Akash");
		cart.setCus_number("5447253786");
		cart.setDeliverd_yn("N");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("Y");
		cart.setRes_id(4);
		cart.setStatus("P");
		cart.setOrd_num("174523912");
		cart.setAllItem(allItem);
		
		Mockito.when(orderRepository.searchForOrderNumber("174523912")).thenReturn(cart);
		Mockito.when(updateService.deStuffProductStock(allItem)).thenReturn(true);
		Mockito.when(orderRepository.save(cart)).thenReturn(cart);
		
		assertThat(orderService.cancelOrder("174523912")).isEqualTo("174523912 Order can not be canceled");
	}
	
	@Test
	public void testDisplayAllOrderFromCustomer() {
		
		List<Item> allItem = Arrays.asList(new Item(0, "Cheddar Biscuits", 150, 3, null));
		
		Cart cart = new Cart();
		cart.setCus_name("Akash");
		cart.setCus_number("5447253786");
		cart.setDeliverd_yn("N");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("N");
		cart.setRes_id(4);
		cart.setStatus("P");
		cart.setOrd_num("174523912");
		cart.setAllItem(allItem);
		
		List<Cart> listCart=new ArrayList<>();
		listCart.add(cart);
		
		Mockito.when(orderRepository.findAll()).thenReturn(listCart);
		assertThat(orderService.displayAllOrderFromCustomer()).isEqualTo(listCart);
	}
	
	
	
	//util method
	public String createOrderNumber() {
		Calendar cal = Calendar.getInstance();
		String ord_num = String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + String.valueOf(cal.get(Calendar.MINUTE))
				+ String.valueOf(cal.get(Calendar.SECOND)) + String.valueOf(cal.get(Calendar.MILLISECOND));
		return ord_num;
	}

}
