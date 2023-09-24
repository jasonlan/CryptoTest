package com.test.innerclass;

public class MclassTest {

	private int val = 1;
	
	public static class Miclz {
		
		private int iv2 = 0;
		
		Miclz(int v){
			this.iv2 = v;
		}
		
		private static int iv1 = 2;
		
		public static int getIval() {
			return iv1;
		}
		
		public int getIval2() {
			return iv2;
		}
	}
	
	public int getVal() {
		return val + new Miclz(3).getIval2();
	}
	
	public class Miclz1 {
		
		private int iv1 = 2;
		
		public int getIval() {
			return iv1;
		}
	}
	
}
