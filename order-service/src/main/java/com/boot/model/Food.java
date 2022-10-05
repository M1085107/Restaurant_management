package com.boot.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity(name = "FOOD")
@Table(name = "Food")
@DynamicUpdate
@NamedQueries(value = {
		@NamedQuery(name="Food.selectStock", query = "Select u from FOOD u where u.name like ?1 ")
})
@Inheritance(strategy=InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
public class Food {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "PRICE")
	private double price;
	
	@Column(name = "STOCK")
	private int stock;
	
	@Column(name = "TYPE")
	private String type;

}
