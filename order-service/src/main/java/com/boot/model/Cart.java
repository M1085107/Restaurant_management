package com.boot.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity(name = "Cart")
@Table(name = "CART")
@DynamicUpdate
@NamedQueries(value = {
		@NamedQuery(name="Cart.searchForOrderNumber", query = "Select o from Cart o where o.ord_num like ?1"),
		@NamedQuery(name="Cart.searchForReleaseOrder", query = "Select o from Cart o where o.prepared_yn = 'Y' AND o.deliverd_yn = 'N'")
})
@Inheritance(strategy=InheritanceType.JOINED)
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "CUS_NAME")
	private String cus_name;
	
	@Column(name = "CUS_NUMBER")
	private String cus_number;
	
	@Column(name = "RES_ID")
	private long res_id;
	
	@Column(name = "PAID_YN")
	private String paid_yn;
	
	@Column(name = "PREPARED_YN")
	private String prepared_yn;
	
	@Column(name = "DELIVERD_YN")
	private String deliverd_yn;
	
	@Column(name = "ORD_NUM")
	private String ord_num;
	
	@Column(name = "STATUS")
	private String status="P";
	
	@JsonManagedReference
	@OneToMany(mappedBy = "ordered_Item", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Item> allItem=new ArrayList<Item>();
	
	public void addAllItem(List<Item> itemList) {
        for (Item item : itemList) {
        	allItem.add(item);
            item.setOrdered_Item(this);
		}
    }
// 
//    public void removeItem(Item item) {
//        allItem.remove(item);
//        item.setOrdered_Item(null);
//    }
//	
    public void removeAllItem() {
        allItem.clear();
    }

}
