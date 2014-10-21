package com.main;

public class Target {
	
	public int add(int a, int b) {
		System.out.println("----------------------------");
		return a + b;
	}
	
	public void foo() {
		//int x = add(1, 3);
		//System.out.println();
	}
	
	public static void main(String[] args) {
		System.out.println("++++++++++++++++++++");
		new Target().foo();
	}
}
