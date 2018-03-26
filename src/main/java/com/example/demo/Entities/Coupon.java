package com.example.demo.Entities;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

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
/**
 * Coupon Entity
 * @author omri
 *
 */
@Component
	@Entity(name="COUPONS")
	public class Coupon {
		
		

		@Id @GeneratedValue
		private int id;
		
		@Column
		private String title;

		@Column
		private LocalDate startDate;
		
		@Column
		private LocalDate endDate;
		
		@Column
		private int amount;
		
		@Column
		private CouponType type;
		
		@Column
		private String message;
		
		@Column
		private double price;
		
		@Column
		private String image;
		
		@ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.DETACH ,  CascadeType.REFRESH})
		@JoinTable(name="Customer_Coupon",
					joinColumns=@JoinColumn(name="Coupon_id"),
					inverseJoinColumns = @JoinColumn(name = "Customer_id"))
		
		private Collection <Customer> customers ;
/**
 * get Coupon id
 * @return Int id
 */
		public int getId() {
			return id;
		}
/**
 * get Coupon title
 * @return String title
 */
		

		public String getTitle() {
			return title;
		}
/**
 * set Coupon title
 * @param title
 */
		public void setTitle(String title) {
			this.title = title;
		}
/**
 * get the startDate of the coupon
 * @return LocalDate startDate
 */
		public LocalDate getStartDate() {
			return startDate;
		}
/**
 * set startDate of the coupon
 * @param startDate
 */
		public void setStartDate(LocalDate startDate) {
			this.startDate = startDate;
		}
/**
 * get endDate of the coupon
 * @return LocalDate endDate
 */
		public LocalDate getEndDate() {
			return endDate;
		}
/**
 * set endDate of the coupon
 * @param endDate
 */
		public void setEndDate(LocalDate endDate) {
			this.endDate = endDate;
		}
/**
 * get the amount of the coupon
 * @return int amount
 */
		public int getAmount() {
			return amount;
		}
/**
 * set the amount of the coupon
 * @param amount
 */
		public void setAmount(int amount) {
			this.amount = amount;
		}
/**
 * get the CouponType of the coupon
 * @return CouponType type
 */
		public CouponType getType() {
			return type;
		}
/**
 * set the CouponType of the coupon
 * @param type
 */
		public void setType(CouponType type) {
			this.type = type;
		}
/**
 * get the message of the coupon
 * @return String message
 */
		public String getMessage() {
			return message;
		}
/**
 * set the message of the coupon
 * @param message
 */
		public void setMessage(String message) {
			this.message = message;
		}
/**
 * get the price of the coupon
 * @return double price
 */
		public double getPrice() {
			return price;
		}
/**
 * set the price of the coupon
 * @param price
 */
		public void setPrice(double price) {
			this.price = price;
		}
/**
 * get the image of the coupon by string
 * @return String image
 */
		public String getImage() {
			return image;
		}
/**
 * set the image of the coupon by string
 * @param image
 */
		public void setImage(String image) {
			this.image = image;
		}
/**
 * Constructor
 * @param title
 * @param startDate
 * @param endDate
 * @param amount
 * @param type
 * @param message
 * @param price
 * @param image
 */
		public Coupon( String title, LocalDate startDate, LocalDate endDate, int amount, CouponType type, String message,
				double price, String image) {
			super();
			
			this.title = title;
			this.startDate = startDate;
			this.endDate = endDate;
			this.amount = amount;
			this.type = type;
			this.message = message;
			this.price = price;
			this.image = image;
		}
/**
 * an empty constructor
 */
		public Coupon() {
			super();
		}
/**
 * toString method to print all fields of the entity
 */
		@Override
		public String toString() {
			return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
					+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
					+ image + "]";
		}
/**'
 * Boolean method that override  object method and ask if the input object istance of Coupon object		
 */
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Coupon)) {
				return false;
			}

			return id == ((Coupon) obj).getId()
					;
		}
	}
	