package com.soot;

import java.util.Iterator;
import java.util.Map;

import soot.Body;
import soot.BodyTransformer;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.IntConstant;
import soot.jimple.InvokeExpr;
import soot.jimple.Jimple;
import soot.jimple.ReturnStmt;
import soot.jimple.ReturnVoidStmt;
import soot.jimple.StaticInvokeExpr;
import soot.jimple.Stmt;
import soot.util.Chain;

public class InvokeStaticInstrumenter extends BodyTransformer {

	static SootClass counterClass;
	static SootMethod increaseCounter, reportCounter;
	static {
		counterClass = Scene.v().loadClassAndSupport("com.soot.Counter");
		increaseCounter = counterClass.getMethod("void increase(int)");
		reportCounter = counterClass.getMethod("void report()");
	}

	protected void internalTransform(Body body, String phase, Map options) {

		SootMethod method = body.getMethod();
		System.out.println("instrumenting method : " + method.getSignature());
		Chain<Unit> units = body.getUnits();
		Iterator<Unit> stmtIt = units.snapshotIterator();

		while (stmtIt.hasNext()) {
			Stmt stmt = (Stmt) stmtIt.next();
			if (!stmt.containsInvokeExpr()) {
				continue;
			}
			InvokeExpr expr = (InvokeExpr) stmt.getInvokeExpr();
			if (!(expr instanceof StaticInvokeExpr)) {
				continue;
			}
			InvokeExpr incExpr = Jimple.v().newStaticInvokeExpr(
					increaseCounter.makeRef(), IntConstant.v(1));
			Stmt incStmt = Jimple.v().newInvokeStmt(incExpr);
			units.insertBefore(incStmt, stmt);
		}

		String signature = method.getSubSignature();
		boolean isMain = signature.equals("void main(java.lang.String[])");
		if (isMain) {
			stmtIt = units.snapshotIterator();
			while (stmtIt.hasNext()) {
				Stmt stmt = (Stmt) stmtIt.next();
				if ((stmt instanceof ReturnStmt)
						|| (stmt instanceof ReturnVoidStmt)) {
					InvokeExpr reportExpr = Jimple.v().newStaticInvokeExpr(
							reportCounter.makeRef());
					Stmt reportStmt = Jimple.v().newInvokeStmt(reportExpr);
					units.insertBefore(reportStmt, stmt);
				}
			}
		}
	}
}