package com.soot.analysis;

import soot.Main;
import soot.PackManager;
import soot.Transform;

public class TagBusyExpressions {
	public static void main(String[] args) {
		String path = TagBusyExpressions.class.getResource("/").getPath();
		String[] argv = {
			"com.soot.analysis.LiveVarsClass",
			"-cp", path,
			"-pp"
		};
		
		PackManager.v().getPack("jtp").add(new
				Transform("jtp." + VeryBusyExpsTagger.PHASE_NAME,
						VeryBusyExpsTagger.v()));
		
		Main.main(argv);
	}
}
