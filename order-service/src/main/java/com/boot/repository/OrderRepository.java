package com.boot.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.boot.model.Cart;

@Repository
public interface OrderRepository extends JpaRepository<Cart, Long>{
	
	public Cart searchForOrderNumber(String ord_num);
	
	public List<Cart> searchForReleaseOrder();
	
	@Modifying
	@Transactional
	@Query("UPDATE Cart o SET o.deliverd_yn = 'Y' WHERE o.prepared_yn = 'Y' AND o.deliverd_yn = 'N' AND o.status = 'P'")
	public int updateForReleaseOrder();
	
	@Modifying
	@Transactional
	@Query("UPDATE Cart o SET o.status = 'C' WHERE o.ord_num = ?1")
	public void cancelOrder(String ord_name);

}
