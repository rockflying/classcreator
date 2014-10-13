package com.test;

import soot.Pack;
import soot.PackManager;
import soot.Transform;

public class Main {
	public static void main(String[] args) {
		Pack jtp = PackManager.v().getPack("jtp");
		jtp.add(new Transform("jtp.trans", new TestTransformer()));

		soot.Main.main(args);
	}
}
