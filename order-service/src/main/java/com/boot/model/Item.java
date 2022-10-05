package com.boot.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity(name = "item")
@Table(name = "ITEM")
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "QUANTITY")
	private int quantity;
	
	@Column(name = "PRICE")
	private double price;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	private Cart ordered_Item;

}
