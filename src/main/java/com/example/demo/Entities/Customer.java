package com.example.demo.Entities;

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

import org.springframework.stereotype.Component;

@Component
@Entity(name = "CUSTOMERS")
public class Customer {

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

	public Customer() {
		super();
	}

	public int getId() {
		return id;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Customer(String customer, String password) {
		super();

		this.customerName = customer;
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", customer=" + customerName + ", password=" + password + "]";
	}

	// public Collection<Transactions> getTransactions() {
	// return transactions;
	// }
	//
	// public void setTransactions(Collection<Transactions> transactions) {
	// this.transactions = transactions;
	// }

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Customer)) {
			return false;
		}

		return id == ((Customer) obj).getId();
	}

}
