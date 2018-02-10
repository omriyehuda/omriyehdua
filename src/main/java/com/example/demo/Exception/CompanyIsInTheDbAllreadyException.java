package com.example.demo.Exception;

public class CompanyIsInTheDbAllreadyException   extends  RuntimeException{

	public CompanyIsInTheDbAllreadyException (String message){
		super(message);
	}
}
