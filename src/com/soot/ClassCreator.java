package com.soot;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

import soot.ArrayType;
import soot.Local;
import soot.Modifier;
import soot.RefType;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.SourceLocator;
import soot.Type;
import soot.Unit;
import soot.VoidType;
import soot.jimple.JasminClass;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.jimple.StringConstant;
import soot.options.Options;
import soot.util.Chain;
import soot.util.JasminOutputStream;

public class ClassCreator {
	public static void main(String[] args) throws Exception {
		Scene.v().loadClassAndSupport("java.lang.Object");
		Scene.v().loadClassAndSupport("java.lang.System");

		SootClass sClass = new SootClass("HelloWorld", Modifier.PUBLIC);
		sClass.setSuperclass(Scene.v().getSootClass("java.lang.Object"));
		Scene.v().addClass(sClass);

		// Create the method, public static void main(String[])
        SootMethod method = new SootMethod("main",
             Arrays.asList(new Type[] {ArrayType.v(RefType.v("java.lang.String"), 1)}),
             VoidType.v(), Modifier.PUBLIC | Modifier.STATIC);
     
        sClass.addMethod(method);

		JimpleBody body = Jimple.v().newBody(method);
		
		method.setActiveBody(body);
		Chain<Unit> units = body.getUnits();

        // Add locals, java.io.printStream tmpRef
        Local tmpRef = Jimple.v().newLocal("tmpRef", RefType.v("java.io.PrintStream"));
        body.getLocals().add(tmpRef);
        
  
        // add "tmpRef = java.lang.System.out"
        units.add(Jimple.v().newAssignStmt(tmpRef, Jimple.v().newStaticFieldRef(
            Scene.v().getField("<java.lang.System: java.io.PrintStream out>").makeRef())));

		// insert "tmpRef.println("Hello world!")"
		{
			SootMethod toCall = Scene.v().getMethod(
					"<java.io.PrintStream: void println(java.lang.String)>");
			units.add(Jimple.v().newInvokeStmt(
					Jimple.v().newVirtualInvokeExpr(tmpRef, toCall.makeRef(),
							StringConstant.v("Hello world!"))));
		}
		units.add(Jimple.v().newReturnVoidStmt());

		String fileName = SourceLocator.v().getFileNameFor(sClass,
				Options.output_format_class);
		OutputStream streamOut = new JasminOutputStream(new FileOutputStream(
				fileName));
		PrintWriter writerOut = new PrintWriter(new OutputStreamWriter(
				streamOut));

		JasminClass jasminClass = new soot.jimple.JasminClass(sClass);
		jasminClass.print(writerOut);
		writerOut.flush();
		streamOut.close();
	}
}
