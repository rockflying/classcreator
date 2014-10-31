package com.soot.analysis;
import java.util.Iterator;
import java.util.List;

import soot.Body;
import soot.Local;
import soot.NormalUnitPrinter;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.UnitPrinter;
import soot.options.Options;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.SimpleLiveLocals;

public class RunLiveAnalysis
{
	public static void main(String[] args) {
		args = new String[] {"com.soot.analysis.LiveVarsClass"};
		
		if (args.length == 0) {
			System.out.println("Usage: java RunLiveAnalysis class_to_analyse");
			System.exit(0);
		}
		
		Options.v().set_soot_classpath(".;F:\\workspace\\soot-tutorial\\bin");
		Options.v().set_prepend_classpath(true);
		Options.v().set_whole_program(true);
		Scene.v().addBasicClass(args[0], SootClass.SIGNATURES);
		Scene.v().loadNecessaryClasses();
		
		

		SootClass sClass = Scene.v().loadClass(args[0], SootClass.SIGNATURES);
		sClass.setApplicationClass();
		
		Iterator<SootMethod> methodIt = sClass.getMethods().iterator();
		while (methodIt.hasNext()) {
			SootMethod m = (SootMethod)methodIt.next();
			Body b = m.retrieveActiveBody();
			
			System.out.println("=======================================");			
			System.out.println(m.getName());
			
			UnitGraph graph = new ExceptionalUnitGraph(b);
			System.out.println(graph);
			SimpleLiveLocals sll = new SimpleLiveLocals(graph);
			
			Iterator<?> gIt = graph.iterator();
			while (gIt.hasNext()) {
				Unit u = (Unit)gIt.next();
				List<?> before = sll.getLiveLocalsBefore(u);
				List<?> after = sll.getLiveLocalsAfter(u);
				UnitPrinter up = new NormalUnitPrinter(b);
				up.setIndent("");
				
				System.out.println("---------------------------------------");			
				u.toString(up);			
				System.out.println(up.output());
				System.out.print("Live in: {");
				String sep = "";
				Iterator<?> befIt = before.iterator();
				while (befIt.hasNext()) {
					Local l = (Local)befIt.next();
					System.out.print(sep);
					System.out.print(l.getName() + ": " + l.getType());
					sep = ", ";
				}
				System.out.println("}");
				System.out.print("Live out: {");
				sep = "";
				Iterator<?> aftIt = after.iterator();
				while (aftIt.hasNext()) {
					Local l = (Local)aftIt.next();
					System.out.print(sep);
					System.out.print(l.getName() + ": " + l.getType());
					sep = ", ";
				}			
				System.out.println("}");			
				System.out.println("---------------------------------------");
			}
			System.out.println("=======================================");
		}
	}
}
