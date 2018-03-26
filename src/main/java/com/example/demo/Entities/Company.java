package com.example.demo.Entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.stereotype.Component;
/**
 * Entity named companies
 * @author omri
 *
 */
@Component
@Entity(name = "COMPANIES")
public class Company {

	@Id
	@GeneratedValue
	private int id;

	@Column
	private String companyName;

	@Column
	private String password;

	@Column
	private String email;

	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "Company_id")
	private Collection<Coupon> coupons;

/**
 * toString method to print all fields of the entity
 */

	@Override
	public String toString() {
		return "Company [id=" + id + ", companyName=" + companyName + ", password=" + password + ", email=" + email
				+ "]";
	}
	/**
	 * Boolean method that override  object method and ask if the input object instance of Company object
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Company)) {
			return false;
		}

		return id == ((Company) obj).getId()
				;
	}
/**
 * get company id
 * @return int id
 */
	public int getId() {
		return id;
	}
/**
 * get all the Company
 * @return
 */
	public Collection<Coupon> getCoupons() {
		return coupons;
	}
/**
 * set the collections of coupons
 * @param coupons
 */
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}
/**
 * get company name
 * @return String object
 */
	public String getCompanyName() {
		return companyName;
	}
/**
 * set the comapny name
 * @param companyName
 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
/**
 * get company password 
 * @return String object
 */

	public String getPassword() {
		return password;
	}
/**
 * set company password 
 * @param password
 */
	public void setPassword(String password) {
		this.password = password;
	}
/**
 * get company email.
 * @return String object
 */
	public String getEmail() {
		return email;
	}
/**
 * set company email
 * @param email
 */
	public void setEmail(String email) {
		this.email = email;
	}
/**
 * Costructor
 * @param companyName
 * @param password
 * @param email
 * @param coupons
 */
	public Company(String companyName, String password, String email, Collection<Coupon> coupons) {
		super();
		this.companyName = companyName;
		this.password = password;
		this.email = email;
		this.coupons = coupons;
	}
/**
 * empty constructor
 */
	public Company() {
		super();
	}

/**
 * Costructor
 * @param companyName
 * @param password
 * @param email
 */
	public Company(String companyName, String password, String email) {
		super();
		this.companyName = companyName;
		this.password = password;
		this.email = email;
	}

}
