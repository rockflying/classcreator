package com.jimple;

import java.util.Iterator;
import java.util.Map;

import soot.Body;
import soot.BodyTransformer;
import soot.PackManager;
import soot.Transform;
import soot.Unit;
import soot.jimple.Stmt;
import soot.tagkit.AttributeValueException;
import soot.tagkit.Tag;
import soot.util.Chain;

public class JimpleInstrumenter {
	public static void main(String[] args) {
		String[] argv = {
			"com.jimple.TestClass"	
		};
		
		PackManager.v().getPack("jtp").add(new Transform("jtp.instru", new BodyTransformer() {
			
			@Override
			protected void internalTransform(Body b, String phaseName,
					Map options) {
				// TODO Auto-generated method stub
				//System.out.println(b.getMethod().getName());
				if(b.getMethod().getName().equals("add")) {
					//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
					Chain<Unit> units = b.getUnits();
					for(Iterator<Unit> iter = units.snapshotIterator(); iter.hasNext();){
						Stmt stmt = (Stmt) iter.next();
						System.out.println(stmt);
						stmt.addTag(new Tag() {

							@Override
							public String getName() {
								// TODO Auto-generated method stub
								return "TAGGGGGG";
							}

							@Override
							public byte[] getValue()
									throws AttributeValueException {
								// TODO Auto-generated method stub
								return "TAGGGGGG".getBytes();
							}});
					}
				}
			}
		}));
		
		soot.Main.main(argv);
	}
}
