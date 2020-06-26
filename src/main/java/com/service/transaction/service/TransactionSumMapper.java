package com.service.transaction.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.transaction.entity.Transaction;

// Service to keep a mapping in form of a graph of parent and child transaction
// abstraction is there so that it can be easily broken into a new microservice or db entity

@Service
public class TransactionSumMapper {
	
	private Map<Long, HashSet<Long>> parentMap= new HashMap<>();
	
	@Autowired
	private TransactionService transactionService;
	
	
	public boolean addParentTransaction(Long transactionId, Long parentId) {
		
		//create a new transaction if doesn't exist
		if(!parentMap.containsKey(transactionId)) {	
			parentMap.put(transactionId, new HashSet<>());
		}
		
		
		if(parentId == null) {
	
			return true;		
			
		}else {
			
			if	(parentMap.get(transactionId).contains(parentId)) {
				//condition for cycle do not do anything
				return false;
				
			} else if(parentMap.containsKey(parentId) && !parentMap.get(parentId).contains(transactionId)) {
				
				parentMap.get(parentId).add(transactionId);
				
				return true;
				
			}	else {
				
				return false;
				
			}
			
		}
	}
	
	
	
	public double getTotalSumOfTransaction(Long transactionId) {
		
		Set<Long> visited = new HashSet<>(); 

		return dfsSumCalculator(visited,transactionId);		
	}
	
	
	private double dfsSumCalculator(Set<Long> visited, Long transactionId) {
		
		if(visited.contains(transactionId)) {
			return 0;
		}
		
		//can also be another service call to get transaction data from id
		Transaction t = transactionService.getTransactionById(transactionId);
		
		double sum = t.getAmount();
		
		visited.add(transactionId);
		
		if(parentMap.get(transactionId)==null) {
			return sum;
		}
		
		for(Long l: parentMap.get(transactionId)) {
			sum = sum + dfsSumCalculator(visited,l);
		}
		
		return sum;
		
		
	}
	

}
