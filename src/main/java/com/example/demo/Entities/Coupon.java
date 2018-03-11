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

		public int getId() {
			return id;
		}

		

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public LocalDate getStartDate() {
			return startDate;
		}

		public void setStartDate(LocalDate startDate) {
			this.startDate = startDate;
		}

		public LocalDate getEndDate() {
			return endDate;
		}

		public void setEndDate(LocalDate endDate) {
			this.endDate = endDate;
		}

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

		public CouponType getType() {
			return type;
		}

		public void setType(CouponType type) {
			this.type = type;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

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

		public Coupon() {
			super();
		}

		@Override
		public String toString() {
			return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
					+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
					+ image + "]";
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Coupon)) {
				return false;
			}

			return id == ((Coupon) obj).getId()
					;
		}
	}
	