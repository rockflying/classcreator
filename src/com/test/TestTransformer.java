package com.test;

import java.util.Map;

import soot.Body;
import soot.BodyTransformer;
import soot.Scene;
import soot.SootMethod;

public class TestTransformer extends BodyTransformer{

	@Override
	protected void internalTransform(Body body, String phaseName, Map options) {
		// TODO Auto-generated method stub
		SootMethod method = body.getMethod();
		//System.out.println("instrumenting method : " + method.getSignature());
		System.out.println(Scene.v().getMainClass());
	}
}
