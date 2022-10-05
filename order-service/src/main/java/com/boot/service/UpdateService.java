package com.boot.service;

import java.util.List;

import com.boot.model.Cart;
import com.boot.model.Item;

public interface UpdateService {
	
	public List<Cart> updateDelivery();
	
	public boolean stuffProductStock(List<Item> listItem);
	
	public boolean deStuffProductStock(List<Item> listItem);

}
