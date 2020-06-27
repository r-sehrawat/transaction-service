package com.service.transaction.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.transaction.entity.Transaction;
import com.service.transaction.service.TransactionService;
import com.service.transaction.service.TransactionSumMapper;
import com.service.transaction.service.TransactionTypeMapper;

@RestController
@RequestMapping("/transactionservice")
public class TransactionController {
	
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private TransactionSumMapper transactionSumMapper;
	
	@Autowired
	private TransactionTypeMapper transactionTypeMapper;
	
	
	//O(1) complexity to perform put operations in 3 different entities
	@PutMapping("/transaction/{transaction_id}")
	public Map<String,String> putTransaction( @PathVariable("transaction_id") Long transactionId, @RequestBody Transaction txn){
	
		
		Map<String,String> responseMap= new HashMap<>();	
		
		//write in transaction data
		transactionService.putTransaction(transactionId,txn);
		
		//map in type data
		transactionTypeMapper.createTypeMapping(txn.getType(), transactionId);
		
		//map in parent child transaction
		transactionSumMapper.addParentTransaction(transactionId, txn.getParentId());
		
		//can add async functionality to this so returned value doesn't matter
		
		responseMap.put("status","ok");
		return responseMap;
	}
	
	
	//API to return transaction info corresponding to the id
	//O(1) complexity is retrieving transactions
	@GetMapping("/transaction/{transaction_id}")
	public Transaction getTransactionType( @PathVariable("transaction_id") Long transactionId){
	       return transactionService.getTransactionById(transactionId);
	}
	
	
	//API to return transaction ids for a type
	//O(1) complexity is retrieving all the types attached
	@GetMapping("/type/{type}")
	public Set<Long> getTransactionType( @PathVariable("type") String type){
	       return transactionTypeMapper.getTransactionIdsOfType(type);	
	}
	
	
	
	//API to calculate the sum of transaction and all it's child
	//O(n) complexity for calculating the sum where n are total linked ids with transactionId
	@GetMapping("/sum/{transaction_id}")
	public Map<String,Double> getSum( @PathVariable("transaction_id") Long transactionId){
	       
		Map<String,Double> responseMap= new HashMap<>();
		double sum = transactionSumMapper.getTotalSumOfTransaction(transactionId);
		
		responseMap.put("sum", sum);
		return responseMap;
		
	}
	
	
	
	
	

}
