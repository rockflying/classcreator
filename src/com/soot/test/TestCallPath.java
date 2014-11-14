package com.soot.test;

import java.util.Iterator;
import java.util.Map;

import soot.Body;
import soot.BodyTransformer;
import soot.Local;
import soot.PackManager;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.Transform;
import soot.Unit;
import soot.jimple.AbstractStmtSwitch;
import soot.jimple.AssignStmt;
import soot.util.Chain;

public class TestCallPath {
	public static void main(String[] args) {
		String path = TestCallPath.class.getResource("/").getPath();
		
		String[] argv = {
			"-cp", path,
			"-pp",
			"com.soot.test.ObjClass",
		};
		
		PackManager.v().getPack("jtp").add(new Transform("jtp.transform", new BodyTransformer() {
			
			@Override
			protected void internalTransform(Body b, String phaseName,
					Map<String, String> options) {
				// TODO Auto-generated method stub
				if(!b.getMethod().getName().equals("compute")) {
					return;
				}
//				System.out.println(b.getMethod().getDeclaringClass().getFields());
				final Chain<SootField> fields = b.getMethod().getDeclaringClass().getFields();
				final Chain<Local> locals = b.getLocals();
				
				final SootMethod method = b.getMethod();
				final SootClass  clazz  = method.getDeclaringClass();
				System.out.println(clazz.getJavaStyleName());
				for(Iterator<Unit> iter = b.getUnits().snapshotIterator(); iter.hasNext();) {
					Unit unit = iter.next();
					
					unit.apply(new AbstractStmtSwitch() {

						@Override
						public void caseAssignStmt(AssignStmt stmt) {
							System.out.println("--------------");
							System.out.println(stmt);
							// System.out.println(stmt.getLeftOp()
							// +"  vs  "+ stmt.getRightOp());
							if (stmt.containsInvokeExpr()) {
								String name = stmt.getInvokeExpr()
										.getMethod().getName();
								System.out.println("s_"
										+ clazz.getJavaStyleName()
										+ "_" + name + "_4");
							}
							// System.out.println(stmt.getBoxesPointingToThis());
							if (stmt.containsFieldRef()
									&& stmt.getLeftOp().equivTo(
											stmt.getFieldRef())) {
								String field_name = stmt.getFieldRef()
										.getField().getName();
								// b.getMethod();
							}
						}
					});
				// System.out.println(unit);
				}
			}
		}));
		
		soot.Main.main(argv);
	}
}
