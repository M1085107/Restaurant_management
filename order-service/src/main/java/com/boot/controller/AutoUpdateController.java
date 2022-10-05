package com.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boot.model.Cart;
import com.boot.service.UpdateService;

@RestController
@RequestMapping("auto")
public class AutoUpdateController {
	
	@Autowired
	public UpdateService updateService;
	
//	@Scheduled(fixedRate = 10000)
	@RequestMapping(value = "/release", method = RequestMethod.GET)
	public List<Cart> releaseOrder(){
		List<Cart> cartList=updateService.updateDelivery();
		cartList.stream().forEach((cart)->System.out.println(cart.getOrd_num()+" order number is ready for delivery to "+cart.getCus_name()));
		return cartList;
	}
	
}
