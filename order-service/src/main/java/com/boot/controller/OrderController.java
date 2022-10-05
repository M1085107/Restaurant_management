package com.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boot.model.Cart;
import com.boot.service.OrderService;

@RestController
@RequestMapping("order")
@CrossOrigin
public class OrderController {
	
	@Autowired
	private OrderService orderService; 
	
//	@RequestMapping(value = "/test",method = RequestMethod.POST)
//	public ResponseEntity<List<Item>> testOrder(@RequestBody Cart ordered_Item) {
//		System.out.println("test order");
//		return new ResponseEntity<List<Item>>(orderService.testOrderService(ordered_Item),HttpStatus.OK);
//	}
	
	@RequestMapping(value = "/new",method = RequestMethod.POST)
	public ResponseEntity<String> createOrder(@RequestBody Cart ordered_Item) {
		
		return new ResponseEntity<String>(orderService.createOrderService(ordered_Item),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/orderCheck",method = RequestMethod.POST)
	public ResponseEntity<String> checkOrder(@RequestBody Cart ordered_Item) throws Exception {
		return new ResponseEntity<String>(orderService.checkOrderService(ordered_Item),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get",method = RequestMethod.GET)
	public ResponseEntity<List<Cart>> displayAllOrder(){
		
		return new ResponseEntity<List<Cart>>(orderService.displayAllOrderFromCustomer(),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get/{ord_num}",method = RequestMethod.GET)
	public ResponseEntity<Cart> displayOrder(@PathVariable("ord_num") String ord_num){
		
		return new ResponseEntity<Cart>(orderService.displayOrderFromCustomer(ord_num),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<String> updateByCustomer(@RequestBody Cart ordered_Item){
		return new ResponseEntity<String>(orderService.updateOrderFromCustomer(ordered_Item),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/prepare/{ord_num}", method = RequestMethod.PUT)
	public ResponseEntity<Cart> prepareOrder(@PathVariable("ord_num") String ord_num){
		
		return new ResponseEntity<Cart>(orderService.updateOrderForPreparation(ord_num),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/cancel/{ord_num}", method = RequestMethod.PUT)
	public ResponseEntity<String> cancelByCustomer(@PathVariable("ord_num") String ord_num){
		
		return new ResponseEntity<String>(orderService.cancelOrder(ord_num),HttpStatus.OK);
	}

}
