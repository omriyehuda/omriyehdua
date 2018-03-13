package com.example.demo.Entities;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TransactionsRepo extends CrudRepository <Transactions,Integer> {

	//@Query (" ALTER TABLE [table_name] ALTER COLUMN [column_name] [data_type] " )
	
	
	
}
