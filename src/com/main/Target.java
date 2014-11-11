package com.main;

public class Target {
	int v;
	
	public int add(int a, int b) {
		return a + b;
	}
	
	public void foo() {
		int v = add(1, 4);
		v = 100;
		int b = v;
		b = add(100, v);
		if(b < 1000) {
			b = 1024;
		}
		this.v = b;
	}
	
	public static void main(String[] args) {
		System.out.println("++++++++++++++++++++");
		new Target().foo();
	}
}
