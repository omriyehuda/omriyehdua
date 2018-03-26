package com.example.demo.Exception;
/**
 * Coupon All Ready Exist Exception extends RuntimeException
 * @author omri
 *
 */
public class CouponAllReadyExistException extends  RuntimeException{
	
/**
 * Costructor
 * @param massage
 */
	public CouponAllReadyExistException ( String massage){
		super (massage);
	}

}
