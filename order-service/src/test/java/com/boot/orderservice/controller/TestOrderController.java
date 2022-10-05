package com.boot.orderservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

import com.boot.controller.OrderController;
import com.boot.model.Cart;
import com.boot.model.Item;
import com.boot.service.OrderService;
import com.boot.util.UtilClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = OrderController.class)
public class TestOrderController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderService orderService;

	@Test
	public void getCreateOrder() throws Exception {
		String URI = "/order/new";

		List<Item> allItem = Arrays.asList(new Item(0, "Flatbread pizza", 200, 2, null),
				new Item(0, "Cheddar Biscuits", 150, 3, null));

		Cart cart = new Cart();
		cart.setCus_name("Akash");
		cart.setCus_number("5447253786");
		cart.setDeliverd_yn("N");
		// if payment is not done
		cart.setPaid_yn("N");
		cart.setPrepared_yn("N");
		cart.setRes_id(4);
		cart.setAllItem(allItem);
		String expectedResult = "Order is placed. your order number is " + this.createOrderNumber();
		String requestBody = this.objMapper(cart);
		Mockito.when(orderService.createOrderService(Mockito.any(Cart.class))).thenReturn(expectedResult);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(requestBody).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String actualOutput = response.getContentAsString();

		assertThat(actualOutput).isEqualTo(expectedResult);
		assertEquals(HttpStatus.OK.value(), response.getStatus());

		// if payment is done
		cart.setPaid_yn("Y");

		expectedResult = "Please make the payment before placing the order";

		requestBody = this.objMapper(cart);

		Mockito.when(orderService.createOrderService(Mockito.any(Cart.class))).thenReturn(expectedResult);

		requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON).content(requestBody)
				.contentType(MediaType.APPLICATION_JSON);
		result = mockMvc.perform(requestBuilder).andReturn();
		response = result.getResponse();

		actualOutput = response.getContentAsString();

		assertThat(actualOutput).isEqualTo(expectedResult);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void testCheckOrder() throws Exception {
		String URI="/order/orderCheck";
		double totalPrice=0;
		List<Item> allItem = Arrays.asList(new Item(0, "Flatbread pizza", 200, 2, null),
				new Item(0, "Cheddar Biscuits", 150, 3, null));
		for (Item item : allItem) {
			totalPrice+=item.getPrice()*item.getQuantity();
		}
		Cart cart = new Cart();
		cart.setCus_name("Akash");
		cart.setCus_number("5447253786");
		cart.setDeliverd_yn("N");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("N");
		cart.setRes_id(4);
		cart.setAllItem(allItem);
		
		String requestBody = this.objMapper(cart);
		String expectedResult="Total is "+String.valueOf(totalPrice)+". Kindly make payment before placing the order";
		Mockito.when(orderService.checkOrderService(Mockito.any(Cart.class))).thenReturn(expectedResult);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(requestBody)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String actualOutput = response.getContentAsString();
		assertThat(actualOutput).isEqualTo(expectedResult);
	}
	
	@Test
	public void testDisplayAllOrder() throws Exception {
		String URI="/order/get";
		List<Item> allItem = Arrays.asList(new Item(77, "Cannoli", 100, 4, null));
		Cart cart1 = new Cart();
		cart1.setId(74);
		cart1.setCus_name("Akash");
		cart1.setCus_number("7465372334");
		cart1.setDeliverd_yn("N");
		cart1.setOrd_num("163321908");
		cart1.setStatus("P");
		cart1.setPaid_yn("Y");
		cart1.setPrepared_yn("Y");
		cart1.setRes_id(5);
		cart1.setAllItem(allItem);
		
		List<Cart> request=Arrays.asList(cart1);
		
		Mockito.when(orderService.displayAllOrderFromCustomer()).thenReturn(request);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.objMapper(request);

		String outputJson = result.getResponse().getContentAsString();
		assertThat(outputJson).isEqualTo(expectedJson);
		
	}
	
	@Test
	public void testDisplayOrder() throws Exception {
		String URI="/order/get/163321908";
		List<Item> allItem = Arrays.asList(new Item(77, "Cannoli", 100, 4, null));
		Cart cart = new Cart();
		cart.setId(74);
		cart.setCus_name("Akash");
		cart.setCus_number("7465372334");
		cart.setDeliverd_yn("N");
		cart.setOrd_num("163321908");
		cart.setStatus("P");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("N");
		cart.setRes_id(5);
		cart.setAllItem(allItem);
		Mockito.when(orderService.displayOrderFromCustomer(Mockito.anyString())).thenReturn(cart);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.objMapper(cart);
		String outputJson = result.getResponse().getContentAsString();
		assertThat(outputJson).isEqualTo(expectedJson);
	}
	
	@Test
	public void testPrepareOrder() throws Exception {
		String URI="/order/prepare/163321908";
		List<Item> allItem = Arrays.asList(new Item(77, "Cannoli", 100, 4, null));
		Cart cart = new Cart();
		cart.setId(74);
		cart.setCus_name("Akash");
		cart.setCus_number("7465372334");
		cart.setDeliverd_yn("N");
		cart.setOrd_num("163321908");
		cart.setStatus("P");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("Y");
		cart.setRes_id(5);
		cart.setAllItem(allItem);
		Mockito.when(orderService.updateOrderForPreparation(Mockito.anyString())).thenReturn(cart);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.objMapper(cart);
		String outputJson = result.getResponse().getContentAsString();
		assertThat(outputJson).isEqualTo(expectedJson);
	}
	
	@Test
	public void testUpdateByCustomer() throws Exception {
		String URI="/order/update";
		double priceBeforeUpdate=0,priceAfterUpdate=0;
		String result="";
		List<Item> itemBeforeUpdate = Arrays.asList(new Item(77, "Cannoli", 100, 4, null));
		for (Item item : itemBeforeUpdate) {
			priceBeforeUpdate+=item.getPrice()*item.getQuantity();
		}
		List<Item> itemAfterUpdate = Arrays.asList(new Item(0, "Flatbread pizza", 200, 2, null),
				new Item(0, "Cheddar Biscuits", 150, 3, null));
		for (Item item : itemAfterUpdate) {
			priceAfterUpdate+=item.getPrice()*item.getQuantity();
		}
		if(priceBeforeUpdate>priceAfterUpdate)
			result= String.valueOf(priceBeforeUpdate-priceAfterUpdate)+" is refunded";
		else if(priceBeforeUpdate<priceAfterUpdate)
			result= String.valueOf(priceAfterUpdate-priceBeforeUpdate)+" will be repaid";

		Cart cart = new Cart();
		cart.setId(74);
		cart.setCus_name("Akash");
		cart.setCus_number("7465372334");
		cart.setDeliverd_yn("N");
		cart.setOrd_num("163321908");
		cart.setStatus("P");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("Y");
		cart.setRes_id(5);
		cart.setAllItem(itemAfterUpdate);
		Mockito.when(orderService.updateOrderFromCustomer(Mockito.any(Cart.class))).thenReturn(result);
		String requestStr=this.objMapper(cart);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(URI)
				.accept(MediaType.APPLICATION_JSON).content(requestStr)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		String actualOutput = response.getContentAsString();
		assertThat(actualOutput).isEqualTo(result);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void testCancelByCustomer() throws Exception {
		String URI="/order/cancel/163321908";
		double price=0;
		String result="";
		List<Item> allItem = Arrays.asList(new Item(77, "Cannoli", 100, 4, null));
		for (Item item : allItem) {
			price+=item.getPrice()*item.getQuantity();
		}
		result=String.valueOf(price)+" will be refunded";
		Cart cart = new Cart();
		cart.setId(74);
		cart.setCus_name("Akash");
		cart.setCus_number("7465372334");
		cart.setDeliverd_yn("N");
		cart.setOrd_num("163321908");
		cart.setStatus("P");
		cart.setPaid_yn("Y");
		cart.setPrepared_yn("N");
		cart.setRes_id(5);
		cart.setAllItem(allItem);
		Mockito.when(orderService.cancelOrder(Mockito.anyString())).thenReturn(result);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(URI)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		String actualOutput = response.getContentAsString();
		assertThat(actualOutput).isEqualTo(result);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	//utility methods 
	public String objMapper(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

	public String createOrderNumber() {
		Calendar cal = Calendar.getInstance();
		String ord_num = String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + String.valueOf(cal.get(Calendar.MINUTE))
				+ String.valueOf(cal.get(Calendar.SECOND)) + String.valueOf(cal.get(Calendar.MILLISECOND));
		return ord_num;
	}

}
