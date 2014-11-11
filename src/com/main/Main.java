package com.main;

import java.util.Iterator;
import java.util.Map;

import com.jimple.JimpleInstrumenter;

import soot.Body;
import soot.BodyTransformer;
import soot.IntType;
import soot.Local;
import soot.PackManager;
import soot.SootClass;
import soot.SootMethod;
import soot.Transform;
import soot.Unit;
import soot.jimple.AbstractStmtSwitch;
import soot.jimple.AssignStmt;
import soot.jimple.IfStmt;
import soot.jimple.IntConstant;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.Jimple;
import soot.jimple.ReturnStmt;
import soot.jimple.ReturnVoidStmt;
import soot.jimple.Stmt;
import soot.util.Chain;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = JimpleInstrumenter.class.getResource("/").getPath();
		String[] argv = {
			"-cp", path,
			"-pp",
			"com.main.Target",
			"-print-tags",
			"-xml-attributes",
			"-annot-nullpointer",
		};
		
		//Options.v().set_output_format(Options.output_format_jimple);
		
		PackManager.v().getPack("jtp")
				.add(new Transform("jtp.trans", new BodyTransformer() {

					@Override
					protected void internalTransform(Body b, String phase,
							Map<String, String> options) {
						// TODO Auto-generated method stub						

//						if (b.getMethod().getName().equals("foo")) {
//							Local tmp = Jimple.v().newLocal("tmp", IntType.v());
//							b.getLocals().add(tmp);
//							
//							SootClass sootclass = b.getMethod().getDeclaringClass();
////							Local thisClass = Jimple.v().newLocal("thisclass", RefType.v(sootclass));
////							b.getLocals().add(thisClass);
//							SootMethod method = sootclass.getMethod("int add(int,int)");
//							Local r0 = b.getLocals().getFirst();
//							System.out.println(r0);
//							//System.out.println(Scene.v().getField("").makeRef());
//							System.out.println(b.getLocals());
//							//Jimple.v().newAssignStmt(thisClass, Jimple.v().newin)
//							
//							//Jimple.v().newAssignStmt(thisClass, );
//							System.out.println(b.getMethod().getDeclaringClass().getType());
//							InvokeExpr expr = Jimple.v().newVirtualInvokeExpr(r0, method.makeRef(), IntConstant.v(100), IntConstant.v(20));
//							Stmt stmt = Jimple.v().newAssignStmt(tmp, expr);
//							
//							Chain<Unit> units = b.getUnits();
//							for(Iterator<Unit> iter = units.snapshotIterator(); iter.hasNext();) {
//								Unit unit = iter.next();
//								System.out.println(unit);
//								
//								if (unit instanceof ReturnStmt || unit instanceof ReturnVoidStmt) {
//									units.insertBefore(stmt, unit);
//								}
//							}
//						}
						if(!b.getMethod().getName().equals("foo")) {
							return;
						}
						System.out.println("instrumenting :" + b.getMethod());
						Chain<Unit> units = b.getUnits();
						for(Iterator<Unit> iter = units.snapshotIterator(); iter.hasNext();) {
							final Unit unit = iter.next();
							System.out.println(unit);
							unit.apply(new AbstractStmtSwitch(){
								@Override
								public void caseInvokeStmt(InvokeStmt stmt) {
									System.out.println("-----------caseInvokeStmt++++++++++");
									System.out.println(stmt);
									InvokeExpr expr = stmt.getInvokeExpr();									
									System.out.println(expr);
									System.out.println(expr.getArgs());
								}
								
								@Override
								public void caseAssignStmt(AssignStmt stmt)
							    {
									System.out.println("-----------caseAssignStmt++++++++++");
							    }
								
								@Override
								public void caseIfStmt(IfStmt stmt)
							    {
									System.out.println("-----------caseIfStmt++++++++++");
							    }
							});
						}
					}
					
				}));
		
		soot.Main.main(argv);
	}

}
