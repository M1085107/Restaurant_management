package com.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.boot.model.Cart;
import com.boot.model.Food;
import com.boot.model.Item;
import com.boot.repository.FoodRepository;
import com.boot.repository.OrderRepository;

@Service
public class UpdateServiceImp implements UpdateService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Override
	public List<Cart> updateDelivery(){
		List<Cart> cartList= orderRepository.searchForReleaseOrder();
		orderRepository.updateForReleaseOrder();
		return cartList;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean stuffProductStock(List<Item> listItem) {
		Food foodList=null;
		for (Item item : listItem) {
			foodList=foodRepository.selectStock(item.getName());
//			if(foodList.getStock()<item.getQuantity()|| foodList.getStock()==0)
//				return false;
			
			foodRepository.updateStock(foodList.getStock() - item.getQuantity() , foodList.getName());
			
		}
		return true;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean deStuffProductStock(List<Item> listItem) {
		Food foodList=null;
		
		for (Item item : listItem) {
			foodList=foodRepository.selectStock(item.getName());
			System.out.println(item.getName());
			foodRepository.updateStock(foodList.getStock() + item.getQuantity() , foodList.getName());
			
		}
		return true;
	}

}
