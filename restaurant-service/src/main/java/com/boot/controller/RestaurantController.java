package com.boot.controller;

import java.util.HashMap;
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

import com.boot.model.Restaurant;
import com.boot.service.RestaurantService;

@RestController
@RequestMapping("restaurant")
@CrossOrigin
public class RestaurantController {
	
	@Autowired
	private RestaurantService restaurantService; 
	
	@RequestMapping(value = "/get",method = RequestMethod.GET)
	public ResponseEntity<List<Restaurant>> getAllList() {
		System.out.println("list of restaurants");
		return new ResponseEntity<List<Restaurant>>(restaurantService.getAllRestaurant(),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
	public ResponseEntity<Restaurant> getSingleList(@PathVariable("id") long res_id) {
		System.out.println("list of restaurants");
		return new ResponseEntity<Restaurant>(restaurantService.getSingleRestaurant(res_id),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/location/{location}",method = RequestMethod.GET)
	public ResponseEntity<List<Restaurant>> getbyLocation(@PathVariable("location") String location) {
		System.out.println("list of restaurants by location");
		return new ResponseEntity<List<Restaurant>>(restaurantService.getRestaurantByLocation(location),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/name/{name}",method = RequestMethod.GET)
	public ResponseEntity<List<Restaurant>> getbyName(@PathVariable("name") String name) {
		System.out.println("list of restaurants");
		return new ResponseEntity<List<Restaurant>>(restaurantService.getRestaurantByName(name),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/distance",method = RequestMethod.POST)
	public ResponseEntity<List<Restaurant>> getbyDistance(@RequestBody HashMap<String, Double> distance) {
		System.out.println("list of restaurants");
		return new ResponseEntity<List<Restaurant>>(restaurantService.getRestaurantByDistance(distance),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/budget",method = RequestMethod.POST)
	public ResponseEntity<List<Restaurant>> getbyBudget(@RequestBody HashMap<String, Double> budget) {
		System.out.println("list of restaurants");
		return new ResponseEntity<List<Restaurant>>(restaurantService.getRestaurantByBudget(budget),HttpStatus.OK);
	}

}
