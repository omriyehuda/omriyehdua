package com.example.demo.Entities;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
/**
 * TransactionsRepo extends CrudRepository
 * @author omri
 *
 */
public interface TransactionsRepo extends CrudRepository <Transactions,Integer> {

	
	
	
}
