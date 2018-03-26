package com.example.demo.Exception;
/**
 * Company Is In The Db All ready Exception extends  RuntimeException
 * @author omri
 *
 */
public class CompanyIsInTheDbAllreadyException   extends  RuntimeException{
/**
 * Constructor
 * @param message
 */
	public CompanyIsInTheDbAllreadyException (String message){
		super(message);
	}
}
