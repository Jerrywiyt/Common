package com.lujunyu.design.visitor;

public class NodeB implements Node{

	@Override
	public void accept(Visitor visitor) {
		visitor.visitor(this);
	}

	public String toString(){
		return "NodeB";
	}
}
