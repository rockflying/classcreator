package com.test;

import soot.Body;
import soot.Local;
import soot.LongType;
import soot.Pack;
import soot.PackManager;
import soot.Scene;
import soot.SootMethod;
import soot.Transform;
import soot.Unit;
import soot.jimple.Jimple;
import soot.jimple.Stmt;
import soot.util.Chain;

public class Main {
	
	public static Local addTmpLong(Body body) {
		Local tmpLong = Jimple.v().newLocal("tmpLong", LongType.v());
		body.getLocals().add(tmpLong);
		return tmpLong;
	}
	
	public static void insertStmt(Chain<Unit> units, Stmt s, Local tmpRef, Local tmpLong) {
		Stmt stmt = Jimple.v().newAssignStmt(tmpRef, Jimple.v().newStaticFieldRef(
			Scene.v().getField("<java.lang.System: java.io.PrintSystem out>").makeRef()));
		units.insertBefore(stmt, s);
		
		SootMethod method = Scene.v().getSootClass("java.io.PrintSystem").getMethod("void println(long)");
		stmt = Jimple.v().newInvokeStmt(Jimple.v().newVirtualInvokeExpr(tmpRef, method.makeRef(), tmpLong));
		units.insertBefore(stmt, s);		
	}
	
	public static void main(String[] args) {
		Pack jtp = PackManager.v().getPack("jtp");
		jtp.add(new Transform("jtp.trans", new TestTransformer()));
		
		
		
		soot.Main.main(args);
	}
}
