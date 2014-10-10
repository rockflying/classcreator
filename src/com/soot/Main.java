package com.soot;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import soot.Body;
import soot.Local;
import soot.RefType;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.SourceLocator;
import soot.Unit;
import soot.jimple.JasminClass;
import soot.jimple.Jimple;
import soot.jimple.StringConstant;
import soot.options.Options;
import soot.util.Chain;
import soot.util.JasminOutputStream;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub		
		Scene.v().loadClassAndSupport("java.lang.Object");
		Scene.v().loadClassAndSupport("java.lang.System");
		Scene.v().loadClassAndSupport("com.soot.AndroLeaker");
		
		SootClass sClass = Scene.v().getSootClass("com.soot.AndroLeaker");
		System.out.println("Loading class: " + sClass);
		
		SootMethod method = sClass.getMethod("void print()");
		System.out.println("Get method: " + method);
		
		
		Body body = method.retrieveActiveBody();
		//System.out.println("Get method body: " + body);
		
		Chain<Unit> units = body.getUnits();
		Unit objunit = null;
		for(Unit unit : units) {
			if(unit.toString().equals("return")) {
				objunit = unit;
			}
		}
		units.remove(objunit);
		
		Local tmpRef = Jimple.v().newLocal("tmpRef", RefType.v("java.io.PrintStream"));
        body.getLocals().add(tmpRef);
        
  
        // add "tmpRef = java.lang.System.out"
        units.add(Jimple.v().newAssignStmt(tmpRef, Jimple.v().newStaticFieldRef(
            Scene.v().getField("<java.lang.System: java.io.PrintStream out>").makeRef())));

		// insert "tmpRef.println("Hello world!")"

		SootMethod toCall = Scene.v().getMethod(
				"<java.io.PrintStream: void println(java.lang.String)>");
		units.add(Jimple.v().newInvokeStmt(
				Jimple.v().newVirtualInvokeExpr(tmpRef, toCall.makeRef(),
						StringConstant.v("Hello world!"))));
		
		units.add(Jimple.v().newReturnVoidStmt());
		
/*		String fileName = SourceLocator.v().getFileNameFor(sClass,
				Options.output_format_class);
		OutputStream streamOut = new JasminOutputStream(new FileOutputStream(
				fileName));
		PrintWriter writerOut = new PrintWriter(new OutputStreamWriter(
				streamOut));

		JasminClass jasminClass = new soot.jimple.JasminClass(sClass);
		jasminClass.print(writerOut);
		writerOut.flush();
		streamOut.close();*/
		//System.out.println("Instrumented method body: " + body);
	}

}
