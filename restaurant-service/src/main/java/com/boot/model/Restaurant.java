package com.boot.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Restaurant")
@Table(name = "RESTAURANT")
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries(value = {
		@NamedQuery(name="Restaurant.findByName", query = "SELECT u from Restaurant u where u.res_name like ?1"),
		@NamedQuery(name="Restaurant.findByLocation", query = "SELECT u from Restaurant u where u.location like ?1"),
		@NamedQuery(name="Restaurant.findByDistance", query = "SELECT u from Restaurant u where u.distance>?1 and u.distance<?2"),
		@NamedQuery(name="Restaurant.findByBudget", query = "SELECT u from Restaurant u where u.budget>?1 and u.budget<?2")
		
})
public class Restaurant{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "RES_NAME")
	private String res_name;
	
	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "CUISINE")
	private String cuisine;
	
	@Column(name = "DISTANCE")
	private double distance;
	
	@Column(name = "BUDGET")
	private double budget;
	
	@Transient
	private List<Food> foodList;

}
