package com.test.springtest;

public class ATest {

	@AutoWireTest("btest")
	private BTest b;
	
	public void dod() {
		b.dot();
	}
	
}
