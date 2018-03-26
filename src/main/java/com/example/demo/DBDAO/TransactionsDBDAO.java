package com.example.demo.DBDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Entities.Company;
import com.example.demo.Entities.CompanyRepo;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.CustomerRepo;
import com.example.demo.Entities.EnumFacade;
import com.example.demo.Entities.Transactions;
import com.example.demo.Entities.TransactionsRepo;
/**
 * TransactionsDBDAO 
 * @author omri
 * all the method to control transactions entity. 
 */
@Component
public class TransactionsDBDAO {

	@Autowired
	private TransactionsRepo transactionsRepo;
	@Autowired
	private CompanyRepo companyRepo;
	@Autowired
	private CustomerRepo customerRepo;
/**
 * an empty constactor.
 */
	public TransactionsDBDAO() {

	}
	/**
	 * method that write to transaction table and Attached to facades methods.
	 */
	
	public void writeToTable(String methodName, Boolean success, EnumFacade facade) {

		Date actionTime = Calendar.getInstance().getTime();

		Transactions action = new Transactions();
		action.setMethodName(methodName);
		action.setSuccess(success);
		action.setTime(actionTime);
		action.setFacade(facade);

		transactionsRepo.save(action);

	}
	/**
	 * method that write to transaction table and Attached to facades methods.
	 */
	public void writeToTableCustomer(String methodName, Boolean success, EnumFacade facade, String details) {

		Date actionTime = Calendar.getInstance().getTime();

		Transactions action = new Transactions();
		action.setMethodName(methodName);
		action.setSuccess(success);
		action.setTime(actionTime);
		action.setFacade(facade);

		action.setDetails(details);
		transactionsRepo.save(action);

	}
	/**
	 * method that write to transaction table and Attached to facades methods.
	 */
	public void writeToTableCompany(String methodName, Boolean success, EnumFacade facade, String details) {

		Date actionTime = Calendar.getInstance().getTime();

		Transactions action = new Transactions();
		action.setMethodName(methodName);
		action.setSuccess(success);
		action.setTime(actionTime);
		action.setDetails(details);
		action.setFacade(facade);

		transactionsRepo.save(action);

	}

}
