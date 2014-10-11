package com.pro;

import soot.Scene;
import soot.SootClass;


public class InstrumenterTest {
	public static void main(String[] args) {
		SootClass sClass = Scene.v().loadClassAndSupport("com.soot.AndroLeaker");
		Scene.v().setMainClass(sClass);
		sClass = Scene.v().getMainClass();
		
		System.out.println(sClass);
		
	}
}
