package com.boot.orderservice.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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

import com.boot.controller.AutoUpdateController;
import com.boot.controller.FoodController;
import com.boot.model.Cart;
import com.boot.service.FoodService;
import com.boot.service.UpdateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AutoUpdateController.class)
public class TestAutoUpdateController {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UpdateService updateService;
	
	@Test
	public void testReleaseOrderForDelivery() throws Exception {
		
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
		
		String URI = "/auto/release/";

		Mockito.when(updateService.updateDelivery()).thenReturn(cartList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.objMapper(cartList);

		String outputJson = result.getResponse().getContentAsString();
		assertThat(outputJson).isEqualTo(expectedJson);
	}
	
	//Util method
	public String objMapper(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	

}
