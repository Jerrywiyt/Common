package com.lujunyu.design.visitor;

public class Visitor2 implements Visitor{

	@Override
	public void visitor(Node node) {
		//处理不同的node，
		//这里
		System.out.println("visitor2 deal "+node);
	}

}
