package com.example.demo.Entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;


@Component
@Entity(name="COMPANIES")
public class Company {

		
		@Id  @GeneratedValue
		private int id;
		
		@Column
		private String companyName;
		
		@Column
		private String password;
		
		@Column
		private String email;
		
		
		@OneToMany(fetch = FetchType.EAGER , cascade = CascadeType.MERGE)
		@JoinColumn(name = "Company_id")
		private Collection <Coupon> coupons;
		
		@Override
		public String toString() {
			return "Company [id=" + id + ", companyName=" + companyName + ", password=" + password + ", email=" + email
					+ "]";
		}


		public int getId() {
			return id;
		}

	

		public Collection <Coupon> getCoupons() {
			return coupons;
		}


		public void setCoupons(Collection <Coupon> coupons) {
			this.coupons = coupons;
		}


		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public Company(String companyName, String password, String email, Collection<Coupon> coupons) {
			super();
			this.companyName = companyName;
			this.password = password;
			this.email = email;
			this.coupons = coupons;
		}

		public Company() {
			super();
		}



		

}
