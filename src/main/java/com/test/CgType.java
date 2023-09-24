package com.test;

public enum CgType {

	TRANSFER(1), 
	
	ONLINE(2), 
	
	MANUAL(3);
	
	private int val = 0;
	
	CgType(int val) {
		this.val = val;
	}
	
	int getVal(){
		return this.val;
	}
}
