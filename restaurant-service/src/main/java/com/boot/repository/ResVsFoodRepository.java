package com.boot.repository;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.boot.model.Restaurant_vs_food;

@Repository
public interface ResVsFoodRepository extends CrudRepository<Restaurant_vs_food, Long>{
	
	public ArrayList<Restaurant_vs_food> findByResId(Long res_id);
}
