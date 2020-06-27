package com.service.transaction.entity;

public class Transaction {

	private Long parentId;
	private String type;
	private double amount;
	
	
	public Transaction( Long parentId, String type, double amount) {
		
		this.amount = amount;
		this.parentId = parentId;
		this.type = type;
	}
	
	
	
	public Transaction() {
	
	}


	public Long getParentId() {
		return parentId;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	
	
	
	
}
