package com.service.transaction.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class TransactionTypeMapper {
	
	private Map<String, HashSet<Long>> typeMap= new HashMap<>();
	
	
	public Boolean createTypeMapping(String type, long id) {
		
		if(!typeMap.containsKey(type)) {
			typeMap.put(type, new HashSet<>());
			typeMap.get(type).add(id);
			return true;
		}else if(typeMap.containsKey(type) && !typeMap.get(type).contains(id)) {
			typeMap.get(type).add(id);
			return true;
		}else {
			return false;
		}
		
	}
	
	public HashSet<Long> getTransactionIdsOfType(String type){
		
		if(typeMap.containsKey(type)) {
			return typeMap.get(type);
		}else {
			return new HashSet<>();
		}
	}
	
}
