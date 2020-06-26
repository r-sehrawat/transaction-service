package com.service.transaction.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.service.transaction.entity.Transaction;

@Service
public class TransactionService {
	
	private Map<Long, Transaction> transactionMap = new HashMap<>();
	
	
	
	public Boolean putTransaction(Long transactionId, Transaction t) {
		
		if(transactionMap.containsKey(transactionId)) {
			return false;
		}
		
		transactionMap.put(transactionId, t);
		return true;
		
	}
	
	public Transaction getTransactionById(Long id) {
	   return transactionMap.containsKey(id) ?  transactionMap.get(id): new Transaction() ;
	}
	

}
