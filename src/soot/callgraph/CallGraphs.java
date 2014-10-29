package soot.callgraph;

public class CallGraphs
{
	public static void main(String[] args) {
		doStuff();
	}
	
	public static void doStuff() {
		new A().foo();
	}
}

class A
{
	public void foo() {
		bar();
	}
	
	public void bar() {
	}
}