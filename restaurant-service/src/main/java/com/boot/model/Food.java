package com.boot.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class Food {

	private long id;
	private String name;
	private double price;
	private int stock;
	private String type;
}
