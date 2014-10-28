package com.jimple;

public class TestClass {
	public int add(int a, int b) {
		return a + b;
	}
	
	public int sub(int a, int b) {
		return a - b;
	}
	
	public void foo() {
		int a = 1;
		int b = 2;
		
		System.out.println(add(a, b));
		
		int c = sub(a, b);
		
		if(c < 0) {
			System.out.println();
		}
	}
}
