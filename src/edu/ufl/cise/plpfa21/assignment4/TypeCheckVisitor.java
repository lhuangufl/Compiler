package edu.ufl.cise.plpfa21.assignment4;

import java.util.Iterator;
import java.util.List;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;
import edu.ufl.cise.plpfa21.assignment3.ast.ASTVisitor;
import edu.ufl.cise.plpfa21.assignment3.ast.Expression;
import edu.ufl.cise.plpfa21.assignment3.ast.IASTNode;
import edu.ufl.cise.plpfa21.assignment3.ast.IAssignmentStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IBinaryExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IBlock;
import edu.ufl.cise.plpfa21.assignment3.ast.IBooleanLiteralExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IDeclaration;
import edu.ufl.cise.plpfa21.assignment3.ast.IExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IFunctionCallExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IFunctionDeclaration;
import edu.ufl.cise.plpfa21.assignment3.ast.IIdentExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IIdentifier;
import edu.ufl.cise.plpfa21.assignment3.ast.IIfStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IImmutableGlobal;
import edu.ufl.cise.plpfa21.assignment3.ast.IIntLiteralExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.ILetStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IListSelectorExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IListType;
import edu.ufl.cise.plpfa21.assignment3.ast.IMutableGlobal;
import edu.ufl.cise.plpfa21.assignment3.ast.INameDef;
import edu.ufl.cise.plpfa21.assignment3.ast.INilConstantExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IPrimitiveType;
import edu.ufl.cise.plpfa21.assignment3.ast.IProgram;
import edu.ufl.cise.plpfa21.assignment3.ast.IReturnStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IStringLiteralExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.ISwitchStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IType;
import edu.ufl.cise.plpfa21.assignment3.ast.IType.TypeKind;
import edu.ufl.cise.plpfa21.assignment3.ast.IUnaryExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IWhileStatement;
import edu.ufl.cise.plpfa21.assignment3.astimpl.ListType__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.PrimitiveType__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.Type__;
import edu.ufl.cise.plpfa21.assignment4.SymbolTable.SymbolTableEntry;

public class TypeCheckVisitor implements ASTVisitor {
	
	private static final boolean VERBOSE = true;

	@SuppressWarnings("unused")
	private static void show(Object input) {
		if (VERBOSE) {
			System.out.println(input.toString());
		}
	}

	@SuppressWarnings("serial")
	public static class TypeCheckException extends Exception {
		TypeCheckException(String m) {
			super(m);
		}
	}

	SymbolTable symtab = new SymbolTable();

	private void check(boolean b, IASTNode n, String message) throws TypeCheckException {
		if (!b) {
			if (n.getLine() > 0) {
				message = n.getLine() + ":" + n.getPosInLine() + " " + n.getText() + "\n" + message;
			}
			throw new TypeCheckException(message);
		}
	}

	@Override
	public Object visitIBinaryExpression(IBinaryExpression n, Object arg) throws Exception {
		//TODO
		show("-------visiting binary expression--------");
		show(symtab);
		IExpression leftExpression = n.getLeft();
		show(leftExpression);
		IExpression rightExpression = n.getRight();
		show(rightExpression);
		IType leftExpressionType = (IType) leftExpression.visit(this, arg);
		show(leftExpressionType + " ------ leftExpressionType " );
		IType rightExpressionType = (IType) rightExpression.visit(this, arg);
		show(rightExpressionType + " ---- rightExpressionType" );
		
		check(leftExpressionType.equals(rightExpressionType), 
				n, "Left Expression Type doesn't match with Right one");
		show("left equals right");
		
		Kind op = n.getOp();
		show(op);
		if (op.equals(Kind.GT) || 
				op.equals(Kind.LT) || 
				op.equals(Kind.EQUALS) || 
				op.equals(Kind.NOT_EQUALS))
			return PrimitiveType__.booleanType;
		else if (leftExpressionType.equals(PrimitiveType__.booleanType) && 
				op.equals(Kind.AND) || 
				op.equals(Kind.OR)) 
			return PrimitiveType__.booleanType;
		else if (leftExpressionType.equals(PrimitiveType__.intType) && 
				op.equals(Kind.MINUS) || 
				op.equals(Kind.PLUS) ||
				op.equals(Kind.DIV) || 
				op.equals(Kind.TIMES))
			return leftExpressionType;
		else if (leftExpressionType.equals(PrimitiveType__.stringType) &&
				op.equals(Kind.PLUS))
			return leftExpressionType;
		else if (leftExpressionType.equals(IType.TypeKind.LIST) &&
				op.equals(Kind.PLUS))
			return leftExpressionType;
		else {
			show("Going to throw an exception");
			throw new TypeCheckException(n.getLine() + ":" + n.getPosInLine() + " " + n.getText() + " Unkown OPERATOR");
		}
	}
	
	
	/**
	 * arg is enclosing function declaration
	 */
	@Override
	public Object visitIBlock(IBlock n, Object arg) throws Exception {
		show("--------- Visiting block -------------");
		show(arg.toString());
		List<IStatement> statements = n.getStatements();
		for (IStatement statement : statements) {
			statement.visit(this, arg);
		}
		show("block checked okay!");
		return null;
	}

	@Override
	public Object visitIBooleanLiteralExpression(IBooleanLiteralExpression n, Object arg) throws Exception {
		//TODO
		IType type = PrimitiveType__.booleanType;
		n.setType(type);
		return type;
	}

	@Override
	public Object visitIFunctionDeclaration(IFunctionDeclaration n, Object arg) throws Exception {
		String name = n.getName().getName();
		IType resultType = n.getResultType();
		if (resultType != null) {
			resultType.visit(this, null);
			check(fullyTyped(resultType), n, "result type cannot be partially defined");
		} else {
			n.setType(Type__.voidType);
		}
		check(symtab.insert(name, n), n, name + " already declared in scope");
		symtab.enterScope();
		for (INameDef argument : n.getArgs()) {
			IType argType = (IType) argument.visit(this, argument);
			check(fullyTyped(argType), argument, "Type of argument must be defined");
		}
		show("Going to check block within IFUNCTION Declaration");

		n.getBlock().visit(this, n);
		show("checked OKAY block with IFUNCTION Declaration");
		symtab.leaveScope();
		return null;
	}

	@Override
	public Object visitIFunctionCallExpression(IFunctionCallExpression n, Object arg) throws Exception {
		IIdentifier name = n.getName();
		IDeclaration dec = (IDeclaration) name.visit(this, null);
		check(dec instanceof IFunctionDeclaration, n, name.getName() + " is not declared or is not a function");
		IFunctionDeclaration fdec = (IFunctionDeclaration) dec;
		List<INameDef> formalArgDecs = fdec.getArgs();
		List<IExpression> actualArgs = n.getArgs();
		check(formalArgDecs.size() == actualArgs.size(), n, "formal and actual parameter lists have different lengths");
		for (int i = 0; i < actualArgs.size(); i++) {
			IType actualType = (IType) actualArgs.get(i).visit(this, arg);
			IType formalType = formalArgDecs.get(i).getType();
			check(compatibleAssignmentTypes(formalType, actualType), actualArgs.get(i),
					"types of actual and formal parameter " + formalArgDecs.get(i).getIdent().getName()
							+ "are inconsistent");
		}
		IType resultType = fdec.getResultType();
		n.setType(resultType);
		return resultType;
	}

	@Override
	public Object visitIIdentExpression(IIdentExpression n, Object arg) throws Exception {
//		show("@@@@@ visitIIdentExpression");
//		show(symtab);
//		IIdentifier name = n.getName();
//		show("current scope @@" + symtab.currentScope + " |||  name @@ " + name.getName());
//		check(symtab.entries.containsKey(name.getName()), n, "Identifier " + name + " is not declared yet");
//		SymbolTableEntry entry = symtab.entries.get(name.getName());
//		show("entry.scope ---> " + entry.scope);
//		check(entry.scope == symtab.currentScope, n, name + " not defined in current scope");
//		IDeclaration dec = (IDeclaration) name.visit(this, null);
//		show("dec @@" + dec);
//		IType type = getType(dec);
//		show("type @@" + type);
//		check(type != Type__.undefinedType, n, "Identifier " + name + " does not have defined type");
//		n.setType(type);
//		return type;
		IIdentifier name = n.getName();
		IDeclaration dec = (IDeclaration) name.visit(this, null);
		IType type = getType(dec);
		check(type != Type__.undefinedType, n, "Identifier " + name + " does not have defined type");
		n.setType(type);
		return type;
	}

	@Override
	public Object visitIIfStatement(IIfStatement n, Object arg) throws Exception {
		//TODO
		show("check if statement");
		IExpression guardExpression = n.getGuardExpression();
		show("guardExpression ---> " + guardExpression);
		guardExpression.visit(this, arg);
		n.getBlock().visit(this, n);
		show("if statement chekced okay");
		return null;
	}

	@Override
	public Object visitIImmutableGlobal(IImmutableGlobal n, Object arg) throws Exception {
		show("------- Visiting IImmutableGlobal -------");
		IExpression expression = n.getExpression(); // IIMutableGlobal must have initalizing expression
		IType expressionType = (IType) expression.visit(this, arg);
		INameDef nameDef = n.getVarDef();
		IType declaredType = (IType) nameDef.visit(this, n);
		IType inferredType = unifyAndCheck(declaredType, expressionType, n);
		nameDef.setType(inferredType);
		show("------- Leaving IImmutableGlobal -------");
		return null;
	}

	@Override
	public Object visitIIntLiteralExpression(IIntLiteralExpression n, Object arg) throws Exception {
		IType type = PrimitiveType__.intType;
		n.setType(type);
		return type;
	}

	/**
	 * arg is enclosing Function declaration
	 */
	@Override
	public Object visitILetStatement(ILetStatement n, Object arg) throws Exception {
		//TODO
		IExpression expression = n.getExpression();
		IType expressionType = expression != null ? (IType) expression.visit(this, arg) : Type__.undefinedType;
		show(" ---------------------- "  + n.getLocalDef().getIdent().getName());
		n.getLocalDef().visit(this, n);
		n.getBlock().visit(this, n);
		return null;
	}

	@Override
	public Object visitIListSelectorExpression(IListSelectorExpression n, Object arg) throws Exception {
		show("----------Are you visiting here?------------");
		show(n.getName());
		IDeclaration dec = (IDeclaration) n.getName().visit(this, arg);
		show("dec -----------> " + dec);
		IType type = getType(dec);
		show("type = getType(dec) -> " + type.isList() + " n.getIndex() -> " + n.getIndex());
		check(type.isList(), n, "invalid or missing type for list ");
		show("type is a list!!!");
		IListType ltype = (IListType) type;
		show(n.getIndex());
		IType indexType = (IType) n.getIndex().visit(this, null);
		show("indexType -> " + indexType.isInt());
		check(indexType.isInt(), n, "index selector must be int");
		show("After indexType -> " + indexType);
		IType elementType = ltype.getElementType();
		show("elementType -> " + elementType);
		n.setType(elementType);
		show("----------Are you leaving here?------------");
		return elementType;
	}

	IType getType(IDeclaration dec) {
		if (dec == null) {
			return null;
		}
		if (dec instanceof IMutableGlobal mg) {
			return mg.getVarDef().getType();
		}
		if (dec instanceof IImmutableGlobal mg) {
			return mg.getVarDef().getType();
		}
		if (dec instanceof INameDef nd) {
			return nd.getType();
		}
		if (dec instanceof IFunctionDeclaration fd) {
			return fd.getResultType();
		}
		return null;
	}

	@Override
	public Object visitIListType(IListType n, Object arg) throws Exception {
		IType elementType = n.getElementType();
		IType inferredElemType = elementType != null ? (IType) elementType.visit(this, null) : Type__.undefinedType;
		return new ListType__(n.getLine(), n.getPosInLine(), "", inferredElemType);
	}

	/**
	 * The enclosing declaration should be passed in as argument. If this is an
	 * IMutableGlobal or IImmutableGlobal, the declaration object is is inserted
	 * into the symbol table as the declaration. If the enclosing declaration is a
	 * Function declaration (and this is a formal parameter) or a Let statement (and
	 * this is a local variable declaration), then this NameDef object is the
	 * Declaration in the symbol table.
	 */
	@Override
	public Object visitINameDef(INameDef n, Object arg) throws Exception {
		String name = n.getIdent().getName();
		show("visitINameDef, n -----> " + n);
		IType type = (IType) n.getType();
		show("visitINameDef, type -----> " + type);
		IType varType = type != null ? (IType) type.visit(this, null) : Type__.undefinedType;
		if (arg instanceof IMutableGlobal || arg instanceof IImmutableGlobal) {
			check(symtab.insert(name, (IDeclaration) arg), n, "Variable " + name + "already declared in this scope");
		} else {
			check(symtab.insert(name, n), n, "Variable " + name + "already declared in this scope");
		}
		show("visitINameDef, ok return varType -----> " + varType);
		return varType;
	}

	@Override
	public Object visitINilConstantExpression(INilConstantExpression n, Object arg) throws Exception {
		n.setType(Type__.nilType);
		return n.getType();
	}

	@Override
	public Object visitIProgram(IProgram n, Object arg) throws Exception {
		List<IDeclaration> decs = n.getDeclarations();
		for (IDeclaration dec : decs) {
			show("check declaration");
			dec.visit(this, symtab);
		}
		return n;
	}
	
//	@Override
//	public Object visitIASTNode(ASTVisitor n, Object arg) throws Exception {
//		return null;
//	}
	
	/**
	 * arg is enclosing function definition
	 */
	@Override
	public Object visitIReturnStatement(IReturnStatement n, Object arg) throws Exception {
		//TODO
		return n.getExpression().visit(this, arg);
	}

	@Override
	public Object visitIStringLiteralExpression(IStringLiteralExpression n, Object arg) throws Exception {
		IType type = PrimitiveType__.stringType;
		n.setType(type);
		return type;
	}

	boolean compatibleAssignmentTypes(IType declared, IType actual) {
		if (declared instanceof IListType && actual instanceof INilConstantExpression) {
			return true;
		}
		if (declared instanceof IListType && actual instanceof IListType) {
			return compatibleAssignmentTypes(((IListType) declared).getElementType(),
					((IListType) actual).getElementType());
		}
		return declared.equals(actual);
	}

	boolean isConstantExpression(IExpression expression) {
		if (expression instanceof IIdentExpression e) {
			String name = ((IIdentExpression) expression).getName().getName();
			IDeclaration dec = symtab.lookupDec(name);
			return ! isMutable(dec);
		} else
			return (expression instanceof IBooleanLiteralExpression) || (expression instanceof IIntLiteralExpression)
					|| (expression instanceof IStringLiteralExpression)
					|| (expression instanceof INilConstantExpression);
	}

	/**
	 * arg is enclosing function definition
	 */
	@Override
	public Object visitISwitchStatement(ISwitchStatement n, Object arg) throws Exception {
		//TODO
		IExpression switchExpression = n.getSwitchExpression();
		IType switchType = (IType) switchExpression.visit(this, arg);
		show("----------------switchType ===" + switchType);
		List<IExpression> branchExpressions = n.getBranchExpressions();
		for (IExpression e : branchExpressions) {
			show("----------------------Inside for loop ====" + e);
			check(e.visit(this, arg).equals(switchType), n, "Branch Expession inconsistant with Switch Expression.");
			
		}
		show("branch expression checked okay");
		n.getDefaultBlock().visit(this, arg);
		show("Default Block checked okay");
		return null;
	}

	@Override
	public Object visitIUnaryExpression(IUnaryExpression n, Object arg) throws Exception {
		IExpression e = n.getExpression();
		IType eType = (IType) e.visit(this, arg);
		Kind op = n.getOp();
		if (op == Kind.MINUS && eType.isInt()) {
			n.setType(PrimitiveType__.intType);
		} else if (op == Kind.MINUS && eType.isList()) {
			// tail of list, result is same type of list as argument
			n.setType(eType);
		} else if (op == Kind.BANG && eType.isBoolean()) {
			n.setType(PrimitiveType__.booleanType);
		} else if (op == Kind.BANG && eType.isList()) {
			IListType listType = (IListType) eType;
			IType elementType = listType.getElementType();
			n.setType(elementType);
		} else {
			// not a legal case
			check(false, n, "Illegal operator or expression type in unary expression");
		}
		return n.getType();

	}

	/**
	 * arg is enclosing function declaration
	 */
	@Override
	public Object visitIWhileStatement(IWhileStatement n, Object arg) throws Exception {
		IExpression guard = n.getGuardExpression();
		IType guardType = (IType) guard.visit(this, arg);
//		check(guardType.isBoolean(), n, "Guard expression type not boolean");
		IBlock block = n.getBlock();
		block.visit(this, arg);
		return arg;
	}

	@Override
	public Object visitIMutableGlobal(IMutableGlobal n, Object arg) throws Exception {
		
		show("----------visiting mutable global------------");
		show(symtab);
		
		IExpression expression = n.getExpression();
		
		show("n.getExpression() -> " + expression);
//		show("visit expression ---> " + expression.visit(this, arg));
		show("n getVarDef --------> " + n.getVarDef());
		IType expressionType = expression != null ? (IType) expression.visit(this, arg) : Type__.undefinedType;
		
		show("expressionType ------> " + expressionType);
		INameDef def = n.getVarDef();
		
		show("def = n.getVarDef() -> " + def.getType());
		IType declaredType = (IType) def.visit(this, arg);
		
		check(declaredType != Type__.undefinedType, n, "Mutable Global Declared Type Undefined.");
		
		show("declaredType -> " + declaredType);
		IType inferredType = unifyAndCheck(declaredType, expressionType, n);
		
		show("inferredType -> " + inferredType);
		def.setType(inferredType);
		return null;
	}

	IType unifyAndCheck(IType declaredType, IType expressionType, IASTNode n) throws TypeCheckException {
		boolean fullyDefinedExpr = fullyTyped(expressionType);
		boolean fullyDefinedDec = fullyTyped(declaredType);
		show(fullyDefinedExpr + " +++ " + declaredType);
		IType inferredType;
		check(fullyDefinedDec || fullyDefinedExpr, n, "type must be inferrable from declaration or initializer");
		show("fullyDefinedDec || fullyDefinedExpr -> " + (fullyDefinedDec || fullyDefinedExpr));
		if (fullyDefinedDec && fullyDefinedExpr) {
			check(compatibleAssignmentTypes(declaredType, expressionType), n, "incompatible types in declaration");
			inferredType = declaredType;
		} else if (expressionType.equals(Type__.undefinedType)) {
			inferredType = declaredType;
		} else if (expressionType.equals(Type__.nilType)) {
			check(declaredType.isList(), n, "NIL is not compatible with declared type");
			inferredType = declaredType;
		} else if (declaredType.equals(Type__.undefinedType)) {
			inferredType = expressionType;
		} else {
			check(declaredType.isList(), n, "invalid type");
			check(expressionType.isList(), expressionType, "incompatible expression type");
			IListType d = (IListType) declaredType;
			IType e = expressionType;
			while (! d.getElementType().equals(Type__.undefinedType)) {
				d = (IListType) d.getElementType();
				check(e.isList(), expressionType, "incompatible expression type");
				e = ((IListType) e).getElementType();
			}
			inferredType = e;
		}
		show("inferredType --++ --> " + inferredType);
		return inferredType;
	}

	boolean fullyTyped(IType type) {
		if (type.equals(Type__.undefinedType) || type.equals(Type__.nilType)) {
			return false;
		}
		if (type.isList()) {
			IType t = type;
			while (t instanceof IListType) {
				t = ((IListType) t).getElementType();
				if (t.equals(Type__.undefinedType)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public Object visitIPrimitiveType(IPrimitiveType n, Object arg) throws Exception {
		return n;
	}

	@Override
	public Object visitIAssignmentStatement(IAssignmentStatement n, Object arg) throws Exception {
		IExpression leftExpression = n.getLeft();
		IType leftType = (IType) leftExpression.visit(this, arg);
		IExpression rightExpression = n.getRight();
		if (leftExpression instanceof IFunctionCallExpression) {
			check(rightExpression == null, n, "cannot assign to function");
		} else {
			check(isMutable(leftExpression), n, "attempting to assign to immutable variable");
		}
		if (rightExpression != null) {
			IType rightType = (IType) rightExpression.visit(this, arg);
			check(compatibleAssignmentTypes(leftType, rightType), n, "incompatible types in assignment statement");
		}
		return arg;
	}

	private boolean isMutable(IExpression expression) {
		if (expression instanceof IIdentExpression e) {
			String name = e.getName().getName();
			IDeclaration dec = symtab.lookupDec(name);
			return isMutable(dec);
		}
		if (expression instanceof IListSelectorExpression e) {
			String name = e.getName().getName();
			IDeclaration dec = symtab.lookupDec(name);
			return isMutable(dec);
		}
		return false;
	}

	boolean isMutable(IDeclaration dec) {
		return (dec instanceof IMutableGlobal || dec instanceof INameDef);
	}

	@Override
	public Object visitIIdentifier(IIdentifier n, Object arg) throws Exception {

		String name = n.getName();

		IDeclaration dec = symtab.lookupDec(name);

		check(dec != null, n, "identifier not declared");
		return dec;
	}

}
