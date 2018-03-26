package com.example.demo.Exception;
/**
 * Company Doesn't Exist Exception extends RuntimeException
 * @author omri
 *
 */
public class CompanyDoesntExistExeption extends RuntimeException{
/**
 * constructor
 * @param message
 */
	public CompanyDoesntExistExeption(String message){
		super(message);
	}

}
