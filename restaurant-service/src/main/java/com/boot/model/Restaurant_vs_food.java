package com.boot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Entity(name = "Restaurant_vs_food")
@Table(name = "RESTAURANT_VS_FOOD")
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries(value = {
		@NamedQuery(name="Restaurant_vs_food.findByResId", query = "SELECT u from Restaurant_vs_food u where u.res_id=?1")
})
public class Restaurant_vs_food{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "RES_ID")
	private long res_id;
	
	@Column(name = "FOOD_ID")
	private long food_id;
	
}
