package edu.ufl.cise.plpfa21.assignment3.astimpl;

import edu.ufl.cise.plpfa21.assignment3.ast.IExpression;

public abstract class Expression__ extends ASTNode__ implements IExpression {

	public Expression__(int line, int posInLine, String text) {
		super(line, posInLine, text);
	}

}
