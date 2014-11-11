package com.soot.test;

public class ObjClass {
	private int id;
	
	public int getDeviceId(int x) {		
		return 12345;
	}
	
	public int compute(int x, int y) {
		
		int userid = getDeviceId(0);
		if(userid != 0) {
			id = userid;
		} else {
			id = 0;
		}
		sink(id);
		return id;
	}
	
	public void sink(int a) {
		System.out.println(a);
	}
}
