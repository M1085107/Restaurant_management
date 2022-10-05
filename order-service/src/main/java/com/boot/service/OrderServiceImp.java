package com.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.model.Item;
import com.boot.model.Cart;
import com.boot.model.Food;
import com.boot.repository.FoodRepository;
import com.boot.repository.OrderRepository;
import com.boot.util.UtilClass;

@Service
public class OrderServiceImp implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Autowired
	private UpdateService updateService;
	
//	@Autowired
//	private UtilClass utilClass;
	
//	@Override
//	public List<Item> testOrderService(Cart cart) {
//		for (Item item : cart.getAllItem()) {
//			System.out.println(item.getName());
//		}
//		foodRepository.testStock(cart.getAllItem().get(0).getName(),5);
//		return cart.getAllItem();
//	}

	@Override
	public String createOrderService(Cart ordered_Item) {
		if(ordered_Item.getPaid_yn().equals("N"))
			return "Please make the payment before placing the order";
		
		if(updateService.stuffProductStock(ordered_Item.getAllItem())) {
			ordered_Item.setOrd_num(UtilClass.createOrderNumber());
			orderRepository.save(ordered_Item);
				
		}
		return "Order is placed. your order number is "+ordered_Item.getOrd_num();
		
	}

	@Override
	public String checkOrderService(Cart ordered_Item) throws Exception {
		double totalPrice=0;
		Food foodList=null;
		for (Item item : ordered_Item.getAllItem()) {
			foodList=foodRepository.selectStock(item.getName());
			if(item.getQuantity()>foodList.getStock())
				return item.getName() +" item in not available";
			totalPrice+=item.getPrice()*item.getQuantity();
		}
		return "Total is "+String.valueOf(totalPrice)+". Kindly make payment before placing the order";
	}

	@Override
	public Cart displayOrderFromCustomer(String ord_num) {
		return orderRepository.searchForOrderNumber(ord_num);
	}

	@Override
	public String updateOrderFromCustomer(Cart newItem){
		Cart oldItem=orderRepository.searchForOrderNumber(newItem.getOrd_num());
		if(oldItem.getStatus().equals("C"))
			return oldItem.getOrd_num()+" order is already canceled.";
		List<Item> newItemList=newItem.getAllItem();
		List<Item> oldItemList=oldItem.getAllItem();
		double newPrice=0,oldPrice=0;
		for (Item item : oldItemList) {
			oldPrice+=item.getPrice()*item.getQuantity();
		}
		if(oldItem.getPrepared_yn().equals("Y"))
			return "Order is prepared. Can not place order right now";
		
		updateService.deStuffProductStock(oldItemList);
		
		if(updateService.stuffProductStock(newItemList)) {
			for (Item item : newItemList) {
				newPrice+=item.getPrice()*item.getQuantity();
			}
			oldItem.removeAllItem();
			oldItem.addAllItem(newItemList);
			orderRepository.save(oldItem);
		}else
			return "Food is not available";
		
		if(oldPrice>newPrice)
			return String.valueOf(oldPrice-newPrice)+" is refunded";
		else if(oldPrice<newPrice)
			return String.valueOf(newPrice-oldPrice)+" will be repaid";
		else
			return "Thank you.visit again";
		
	}

	@Override
	public Cart updateOrderForPreparation(String ord_num) {
		Cart cart=orderRepository.searchForOrderNumber(ord_num);
		cart.setPrepared_yn("Y");
		orderRepository.save(cart);
		return cart;
	}

	@Override
	public String cancelOrder(String ord_num) {
		Cart cart= orderRepository.searchForOrderNumber(ord_num);
		List<Item> oldItem=cart.getAllItem();
		double newPrice=0;
		if(cart.getPrepared_yn().equals("Y"))
			return ord_num+" Order can not be canceled";
		else {
			orderRepository.cancelOrder(ord_num);
			if(updateService.deStuffProductStock(oldItem)) {
				for (Item item : oldItem) {
					newPrice+=item.getPrice()*item.getQuantity();
				}
				cart.getAllItem().removeAll(oldItem);
				orderRepository.save(cart);
				
			}
			return newPrice+" will be refunded";
		}
	}

	@Override
	public List<Cart> displayAllOrderFromCustomer() {
		return orderRepository.findAll();
	}

}
