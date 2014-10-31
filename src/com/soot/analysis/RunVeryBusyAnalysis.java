package com.soot.analysis;
import java.util.Iterator;
import java.util.List;

import soot.Body;
import soot.NormalUnitPrinter;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.UnitPrinter;
import soot.jimple.internal.AbstractBinopExpr;
import soot.options.Options;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;

public class RunVeryBusyAnalysis
{
	public static void main(String[] args) {
		args = new String[] {"com.soot.analysis.VeryBusyClass"};
		
		if (args.length == 0) {
			System.out.println("Usage: java RunVeryBusyAnalysis class_to_analyse");
			System.exit(0);
		}
		
		String path = RunVeryBusyAnalysis.class.getResource("/").getPath();
		Options.v().set_soot_classpath(path);
		Options.v().set_prepend_classpath(true);
		Scene.v().addBasicClass(args[0], SootClass.BODIES);
		Scene.v().loadNecessaryClasses();
		
		SootClass sClass = Scene.v().loadClass(args[0], SootClass.BODIES);		
		sClass.setApplicationClass();
		
		Iterator<SootMethod> methodIt = sClass.getMethods().iterator();
		while (methodIt.hasNext()) {
			SootMethod m = (SootMethod)methodIt.next();
			Body b = m.retrieveActiveBody();
			
			System.out.println("=======================================");			
			System.out.println(m.toString());
			
			UnitGraph graph = new ExceptionalUnitGraph(b);
			VeryBusyExpressions vbe = new SimpleVeryBusyExpressions(graph);
			
			Iterator<Unit> gIt = graph.iterator();
			while (gIt.hasNext()) {
				Unit u = (Unit)gIt.next();
				List<?> before = vbe.getBusyExpressionsBefore(u);
				List<?> after = vbe.getBusyExpressionsAfter(u);
				UnitPrinter up = new NormalUnitPrinter(b);
				up.setIndent("");
				
				System.out.println("---------------------------------------");			
				u.toString(up);			
				System.out.println(up.output());
				System.out.print("Busy in: {");
				String sep = "";
				Iterator<?> befIt = before.iterator();
				while (befIt.hasNext()) {
					AbstractBinopExpr e = (AbstractBinopExpr)befIt.next();
					System.out.print(sep);
					System.out.print(e.toString());
					sep = ", ";
				}
				System.out.println("}");
				System.out.print("Busy out: {");
				sep = "";
				Iterator<?> aftIt = after.iterator();
				while (aftIt.hasNext()) {
					AbstractBinopExpr e = (AbstractBinopExpr)aftIt.next();
					System.out.print(sep);
					System.out.print(e.toString());
					sep = ", ";
				}			
				System.out.println("}");			
				System.out.println("---------------------------------------");
			}
			
			System.out.println("=======================================");
		}
	}
}
