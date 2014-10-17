package com.test;

public class TargetClass {
	private int a;
	private int b;

	public TargetClass(int x, int y) {
		a = x;
		b = y;
	}

	public TargetClass() {
		a = 0;
		b = 0;
	}
	
	public void print() {
		System.out.println(a + " " + b);
	}
	
	public String toString() {
		return "Target class";
	}
	
	public void foo() {
		bar();
	}

	private void bar() {
		// TODO Auto-generated method stub
		a = 1;
		b = 1;
		print();
		System.out.println(toString());
	}
	
	protected void func() {
		foo();
	}
	
	public static void main(String[] args) {
		new TargetClass().func();
	}
}

