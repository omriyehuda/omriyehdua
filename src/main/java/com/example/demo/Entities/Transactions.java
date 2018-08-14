package com.example.demo.Entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.example.demo.Facade.CouponClientFacade;
/**
 * Transactions entity 
 * @author omri
 *
 */
@Component
@XmlRootElement
@Entity(name="TRANSACTIONS")
public class Transactions {
	
	@Id  @GeneratedValue
	private int id;
	
	@Column
	private Date time;
	
	@Column
	private String methodName;
	
	@Column
	private boolean success;
	
	@Column
	private  EnumFacade facade;

	@Column
	private  String details;
/**
 * constructor 
 * @param id
 * @param time
 * @param methodName
 * @param success
 * @param facade
 */

	public Transactions(int id, Date time, String methodName, boolean success, EnumFacade facade) {
		super();
		this.id = id;
		this.time = time;
		this.methodName = methodName;
		this.success = success;
		this.facade = facade;
	}
/**
 * an empty costructor
 */
	public Transactions() {
		super();
	}
/**
 *  toString method to print all fields of the entity
 */
	@Override
	public String toString() {
		return "Actions [id=" + id + ", time=" + time + ", methodName=" + methodName + ", success=" + success + "]";
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getId() {
		return id;
	}

	public EnumFacade getFacade() {
		return facade;
	}

	public void setFacade(EnumFacade facade) {
		this.facade = facade;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	
	
	

}
