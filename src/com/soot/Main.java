package com.soot;

import soot.Scene;
import soot.SootClass;
import soot.SootMethod;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		Scene.v().loadClassAndSupport("java.lang.Object");
		Scene.v().loadClassAndSupport("java.lang.System");
		Scene.v().loadClassAndSupport("com.soot.AndroLeaker");
		
		SootClass sClass = Scene.v().getSootClass("com.soot.AndroLeaker");
		System.out.println("Loading class: " + sClass);
		
		SootMethod method = sClass.getMethod("void print()");
		System.out.println("Get method: " + method);
	}

}
