package soot.callgraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import soot.MethodOrMethodContext;
import soot.PackManager;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootMethod;
import soot.Transform;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Targets;

public class CallGraphExample
{	
	public static void main(String[] args) {
	   List<String> argsList = new ArrayList<String>(Arrays.asList(args));
	   String path = CallGraphExample.class.getResource("/").getPath();
	   argsList.addAll(Arrays.asList(new String[]{
			   "-w", 
			   "-pp",
			   "-cp", path,
			   "-main-class",
			   "soot.callgraph.CallGraphs",//main-class
			   "soot.callgraph.CallGraphs",//argument classes
			   "soot.callgraph.A"		   //
	   }));
	

	   PackManager.v().getPack("wjtp").add(new Transform("wjtp.myTrans", new SceneTransformer() {

		@Override
		protected void internalTransform(String phaseName, Map<String, String> options) {
		       CHATransformer.v().transform();
               //Scene.v().getSootClass("soot.callgraph.A");

		       SootMethod src = Scene.v().getMainClass().getMethodByName("doStuff");
		       CallGraph cg = Scene.v().getCallGraph();

		       Iterator<MethodOrMethodContext> targets = new Targets(cg.edgesOutOf(src));
		       while (targets.hasNext()) {
		           SootMethod tgt = (SootMethod)targets.next();
		           System.out.println(src + " may call " + tgt);
		       }
		}
		   
	   }));

           args = argsList.toArray(new String[0]);
           
           soot.Main.main(args);
	}
}
