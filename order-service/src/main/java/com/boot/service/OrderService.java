package com.boot.service;

import java.util.List;

import com.boot.model.Cart;
import com.boot.model.Item;

public interface OrderService {
	
//	public List<Item> testOrderService(Cart cart);
	
	public String createOrderService(Cart ordered_Item);
	
	public String checkOrderService(Cart ordered_Item) throws Exception;
	
	public Cart displayOrderFromCustomer(String ord_num);
	
	public String updateOrderFromCustomer(Cart newItem);
	
	public Cart updateOrderForPreparation(String ord_num);
	
	public String cancelOrder(String ord_num) ;
	
	public List<Cart> displayAllOrderFromCustomer();

}
