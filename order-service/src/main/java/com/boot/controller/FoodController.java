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

import com.boot.model.Food;
import com.boot.service.FoodService;

@RestController
@RequestMapping("food")
@CrossOrigin
public class FoodController {
	
	@Autowired
	private FoodService foodService; 
	
	@RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
	public ResponseEntity<Food> getInfo(@PathVariable("id") long id) {
		
		return new ResponseEntity<Food>(foodService.getInfo(id),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get",method = RequestMethod.GET)
	public ResponseEntity<List<Food>> getAllInfo() {
		
		return new ResponseEntity<List<Food>>(foodService.getAllInfo(),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<String> updateByKeeper(@RequestBody List<Food> foods){
		return new ResponseEntity<String>(foodService.updateFoodList(foods),HttpStatus.OK);
	}

}
