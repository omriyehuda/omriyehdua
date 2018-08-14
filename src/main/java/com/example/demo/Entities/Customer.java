package com.example.demo.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
/**
 * Customer Entity
 * @author omri
 *
 */
@Component
@XmlRootElement
@Entity(name = "CUSTOMERS")
public class Customer implements Serializable{

	@Id
	@GeneratedValue
	private int id;

	@Column
	private String customerName;

	@Column
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.REFRESH })
	@JoinTable(name = "Customer_Coupon", joinColumns = @JoinColumn(name = "Customer_id"), inverseJoinColumns = @JoinColumn(name = "Coupon_id"))

	private Collection<Coupon> coupons;

/**
 * an empty costructor 	
 */
	public Customer() {
		super();
	}
/**
 * get customer id
 * @return int id
 */
	public int getId() {
		return id;
	}
/**
 * get coupons collections of the customer
 * @return Collection coupons
 */
	public Collection<Coupon> getCoupons() {
		return coupons;
	}
/**
 * set  coupons collections of the customer
 * @param coupons
 */
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}
/**
 * get the customer name
 * @return String name
 */
	public String getCustomerName() {
		return customerName;
	}
/**
 * set the customer name
 * @param customerName
 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
/**
 * Get the password of the customer
 * @return String password
 */
	public String getPassword() {
		return password;
	}
/**
 * set the password of the customer
 * @param password
 */
	public void setPassword(String password) {
		this.password = password;
	}
/**
 * costructor 
 * @param customer
 * @param password
 */
	public Customer(String customer, String password) {
		super();

		this.customerName = customer;
		this.password = password;
	}
/**
 * toString method to print all fields of the entity
 */
	@Override
	public String toString() {
		return "Customer [id=" + id + ", customer=" + customerName + ", password=" + password + "]";
	}
/**
 * Boolean method that override  object method and ask if the input object istance of Coupon object
 */


	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Customer)) {
			return false;
		}

		return id == ((Customer) obj).getId();
	}

}
