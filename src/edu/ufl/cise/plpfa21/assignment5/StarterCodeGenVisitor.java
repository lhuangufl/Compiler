package edu.ufl.cise.plpfa21.assignment5;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;import javax.swing.DefaultFocusManager;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;
import edu.ufl.cise.plpfa21.assignment3.ast.ASTVisitor;
import edu.ufl.cise.plpfa21.assignment3.ast.IAssignmentStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IBinaryExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IBlock;
import edu.ufl.cise.plpfa21.assignment3.ast.IBooleanLiteralExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IDeclaration;
import edu.ufl.cise.plpfa21.assignment3.ast.IExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IExpressionStatement;
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
import edu.ufl.cise.plpfa21.assignment3.ast.IUnaryExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IWhileStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IType.TypeKind;
import edu.ufl.cise.plpfa21.assignment3.astimpl.PrimitiveType__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.Type__;
import edu.ufl.cise.plpfa21.assignment4.TypeCheckVisitor.TypeCheckException;
import edu.ufl.cise.plpfa21.assignment5.ReferenceCodeGenVisitor.LocalVarInfo;
import edu.ufl.cise.plpfa21.assignment5.ReferenceCodeGenVisitor.MethodVisitorLocalVarTable;
import edu.ufl.cise.plpfa21.pLP.ListSelectorExpression;


public class StarterCodeGenVisitor implements ASTVisitor, Opcodes {
	
	public StarterCodeGenVisitor(String className, String packageName, String sourceFileName){
		this.className = className;
		this.packageName = packageName;	
		this.sourceFileName = sourceFileName;
	}
	

	ClassWriter cw;
	String className;
	String packageName;
	String classDesc;
	String sourceFileName; //



	public static final String stringClass = "java/lang/String";
	public static final String stringDesc = "Ljava/lang/String;";
	public static final String listClass = "java/util/ArrayList";
	public static final String listDesc = "Ljava/util/ArrayList;";
	public static final String runtimeClass = "edu/ufl/cise/plpfa21/assignment5/Runtime";
	
	private static final boolean VERBOSE = true;

	@SuppressWarnings("unused")
	private static void show(Object input) {
		if (VERBOSE) {
			System.out.println(input.toString());
		}
	}
	
	/* Records for information passed to children, namely the methodVisitor and information about current methods Local Variables */
	record LocalVarInfo(INameDef nameDef, String typeDesc, Label start, Label end) {}
	record MethodVisitorLocalVarTable(MethodVisitor mv, List<LocalVarInfo> localVars) {};
	
	
	public static HashMap<String, String> methodDesc = new HashMap<>();

	/*  Adds local variables to a method
	 *  The information about local variables to add has been collected in the localVars table.  
	 *  This method should be invoked after all instructions for the method have been generated, immediately before invoking mv.visitMaxs.
	 */
	private static void addLocals(MethodVisitorLocalVarTable arg, Label start, Label end) {
		MethodVisitor mv = arg.mv;
		List<LocalVarInfo> localVars = arg.localVars();
		show("localVars ->>" + localVars);
		for (int slot = 0; slot < localVars.size(); slot++) {
			LocalVarInfo varInfo = localVars.get(slot);
			String varName = varInfo.nameDef.getIdent().getName();
			String localVarDesc = varInfo.typeDesc;
			Label range0 = varInfo.start == null ? start : varInfo.start;
		    Label range1 = varInfo.end == null ? end : varInfo.end;
		    show(varName + " " + localVarDesc + " " + range0 + " " + range1 + " SLOT -> " + slot);
		    mv.visitLocalVariable(varName, localVarDesc, null, range0, range1, slot);
		}
	}

	@Override
	public Object visitIBinaryExpression(IBinaryExpression n, Object arg) throws Exception {
		show("-------visiting binary expression--------");
		MethodVisitor mv = ((MethodVisitorLocalVarTable) arg).mv;
		n.getLeft().visit(this, arg);
		n.getRight().visit(this, arg);
		Label trueLabel = new Label();
		Label endLabel = new Label();
		Kind op = n.getOp();
		if (op == Kind.OR) {
			mv.visitInsn(IOR);
		}
		else if (op == Kind.AND) {
			mv.visitInsn(IAND);
		}
		else if (op == Kind.EQUALS) {
			if (n.getLeft().getType().isInt() || n.getLeft().getType().isBoolean()) {
				mv.visitJumpInsn(IF_ICMPEQ, trueLabel);
				mv.visitLdcInsn(false);
				mv.visitJumpInsn(GOTO, endLabel);
				mv.visitLabel(trueLabel);
				mv.visitLdcInsn(true);
				mv.visitLabel(endLabel);
			}
			else {
				mv.visitJumpInsn(IF_ACMPEQ, trueLabel);
				mv.visitLdcInsn(false);
				mv.visitJumpInsn(GOTO, endLabel);
				mv.visitLabel(trueLabel);
				mv.visitLdcInsn(true);
				mv.visitLabel(endLabel);
			}
		}
		else if (op == Kind.NOT_EQUALS) {
			if (n.getLeft().getType().isInt() || n.getLeft().getType().isBoolean()) {
				mv.visitJumpInsn(IF_ICMPNE, trueLabel);
				mv.visitLdcInsn(false);
				mv.visitJumpInsn(GOTO, endLabel);
				mv.visitLabel(trueLabel);
				mv.visitLdcInsn(true);
				mv.visitLabel(endLabel);
			}
			else {
				mv.visitJumpInsn(IF_ACMPNE, trueLabel);
				mv.visitLdcInsn(false);
				mv.visitJumpInsn(GOTO, endLabel);
				mv.visitLabel(trueLabel);
				mv.visitLdcInsn(true);
				mv.visitLabel(endLabel);
			}
		}
		else if (op == Kind.LT) {
			if (n.getLeft().getType().isInt() || n.getLeft().getType().isBoolean()) {
				mv.visitJumpInsn(IF_ICMPLT, trueLabel);
				mv.visitLdcInsn(false);
				mv.visitJumpInsn(GOTO, endLabel);
				mv.visitLabel(trueLabel);
				mv.visitLdcInsn(true);
				mv.visitLabel(endLabel);
			}
			else {
				mv.visitInsn(SWAP);
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "startsWith", "(Ljava/lang/String;)Z", false);	
			}
		}
		else if (op == Kind.GT) {
			if (n.getLeft().getType().isInt() || n.getLeft().getType().isBoolean()) {
				mv.visitJumpInsn(IF_ICMPGT, trueLabel);
				mv.visitLdcInsn(false);
				mv.visitJumpInsn(GOTO, endLabel);
				mv.visitLabel(trueLabel);
				mv.visitLdcInsn(true);
				mv.visitLabel(endLabel);
			}
			else {
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "startsWith", "(Ljava/lang/String;)Z", false);
			}
		}

		else if (op == Kind.PLUS) {
			n.setType(n.getLeft().getType());
			if (n.getLeft().getType().isInt() || n.getLeft().getType().isBoolean()) {
				show(n.getLeft().getType() + " --- " + n.getRight().getType());
				mv.visitInsn(IADD);
			}
			else {
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "concat", "(Ljava/lang/String;)Ljava/lang/String;", false);
			}
		}
		else if (op == Kind.MINUS) {
			n.setType(n.getLeft().getType());
			if (n.getLeft().getType().isInt() || n.getLeft().getType().isBoolean()) {
				mv.visitInsn(ISUB);
			}
			else {
				throw new UnsupportedOperationException("Minus Operation Not Supported for " + n.getLeft().getType() + " " + n.getRight().getType());
			}
		}
		else if (op == Kind.TIMES) {
			if (n.getLeft().getType().isInt() || n.getLeft().getType().isBoolean()) {
				mv.visitInsn(IMUL);
			}
			else {
				throw new UnsupportedOperationException("Multiply Operation Not Supported for " + n.getLeft().getType() + " " + n.getRight().getType());
			}
		}
		else if (op == Kind.DIV) {
			if (n.getLeft().getType().isInt() || n.getLeft().getType().isBoolean()) {
				mv.visitInsn(IDIV);
			}
			else {
				throw new UnsupportedOperationException("Div Operation Not Supported for " + n.getLeft().getType() + " " + n.getRight().getType());
			}
		}
		return null;
	}


	@Override
	public Object visitIImmutableGlobal(IImmutableGlobal n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;				
		INameDef nameDef = n.getVarDef();
		String varName = nameDef.getIdent().getName();
		String typeDesc = nameDef.getType().getDesc();
		FieldVisitor fieldVisitor = cw.visitField(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, varName, typeDesc, null, null);
		fieldVisitor.visitEnd();
		//generate code to initialize field.  
		IExpression e = n.getExpression();
		e.visit(this, arg);  //generate code to leave value of expression on top of stack
		mv.visitFieldInsn(PUTSTATIC, className, varName, typeDesc);	
		return null;
	}

	@Override
	public Object visitIMutableGlobal(IMutableGlobal n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;				
		INameDef nameDef = n.getVarDef();
		String varName = nameDef.getIdent().getName();
		String typeDesc = nameDef.getType().getDesc();
		FieldVisitor fieldVisitor = cw.visitField(ACC_PUBLIC | ACC_STATIC, varName, typeDesc, null, null);
		fieldVisitor.visitEnd();
		//generate code to initialize field.  
		IExpression e = n.getExpression();
		if (e!= null) {
			e.visit(this, arg);  //generate code to leave value of expression on top of stack
			mv.visitFieldInsn(PUTSTATIC, className, varName, typeDesc);	
		}
		return null;
	}


	@Override
	public Object visitINilConstantExpression(INilConstantExpression n, Object arg) throws Exception {
		throw new UnsupportedOperationException("SKIP THIS");
	}
	
	@Override
	public Object visitIIntLiteralExpression(IIntLiteralExpression n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;
		mv.visitLdcInsn(n.getValue());
		return null;
	}
	

	@Override
	public Object visitIBooleanLiteralExpression(IBooleanLiteralExpression n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable) arg).mv;
		mv.visitLdcInsn(n.getValue());
		return null;
	}
	
	@Override
	public Object visitIStringLiteralExpression(IStringLiteralExpression n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;
		mv.visitLdcInsn(n.getValue());
		return null;
	}


	@Override
	public Object visitIListSelectorExpression(IListSelectorExpression n, Object arg) throws Exception {
		throw new UnsupportedOperationException("SKIP THIS");
	}

	@Override
	public Object visitIListType(IListType n, Object arg) throws Exception {
		throw new UnsupportedOperationException("SKIP THIS!!");
	}

	@Override
	public Object visitINameDef(INameDef n, Object arg) throws Exception {
        String varName = n.getIdent().getName();
        IType type = n.getType();
        String desc;

        if (type.isString()) {desc = "Ljava/lang/String;";}
        else if (type.isInt()) {desc = "I";}
        else if (type.isBoolean()) {desc = "Z";}
        else {throw new UnsupportedOperationException("visitDecVar can only be type of Sting or Int. Received: " + type);}

        FieldVisitor fieldVisitor = cw.visitField(ACC_STATIC, varName, desc, null, null);
        fieldVisitor.visitEnd();


        return null;
	}


	@Override
	public Object visitIProgram(IProgram n, Object arg) throws Exception {
		
		show("In IProgram: " + n);
		
		cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		/*
		 * If the call to mv.visitMaxs(1, 1) crashes, it is sometime helpful to temporarily try it without COMPUTE_FRAMES. You won't get a runnable class file
		 * but you can at least see the bytecode that is being generated. 
		 */
//	    cw = new ClassWriter(0); 
		classDesc = "L" + className + ";";
		cw.visit(V16, ACC_PUBLIC + ACC_SUPER, className, null, "java/lang/Object", null);
		if (sourceFileName != null) cw.visitSource(sourceFileName, null);
		
		// create MethodVisitor for <clinit>  
		//  This method is the static initializer for the class and contains code to initialize global variables.
		// get a MethodVisitor
		MethodVisitor clmv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "<clinit>", "()V", null, null);
		// visit the code first
		clmv.visitCode();
		//mark the beginning of the code
		Label clinitStart = new Label();
		clmv.visitLabel(clinitStart);
		//create a list to hold local var info.  This will remain empty for <clinit> but is shown for completeness.  Methods with local variable need this.
		List<LocalVarInfo> initializerLocalVars = new ArrayList<LocalVarInfo>();
		//pair the local var infor and method visitor to pass into visit routines
		MethodVisitorLocalVarTable clinitArg = new MethodVisitorLocalVarTable(clmv,initializerLocalVars);
		//visit all the declarations. 
		List<IDeclaration> decs = n.getDeclarations();
		for (IDeclaration dec : decs) {
			dec.visit(this, clinitArg);  //argument contains local variable info and the method visitor.  
		}
		//add a return method
		clmv.visitInsn(RETURN);
		//mark the end of the bytecode for <clinit>
		Label clinitEnd = new Label();
		clmv.visitLabel(clinitEnd);
		//add the locals to the class
		//addLocals(clinitArg, clinitStart, clinitEnd);  //shown for completeness.  There shouldn't be any local variables in clinit.
		//required call of visitMaxs.  Since we created the ClassWriter with  COMPUTE_FRAMES, the parameter values don't matter. 
		clmv.visitMaxs(0, 0);
		//finish the method
		clmv.visitEnd();
	
		//finish the clas
		cw.visitEnd();

		//generate classfile as byte array and return
		return cw.toByteArray();
	}


	
	@Override
	public Object visitIFunctionDeclaration(IFunctionDeclaration n, Object arg) throws Exception {
		
		String name = n.getName().getName();
		show("In visitIFunctionDeclaration: " + name);

		//Local var table
		List<LocalVarInfo> localVars = new ArrayList<LocalVarInfo>();
		//Add args to local var table while constructing type desc.
		List<INameDef> args = n.getArgs();

		//Iterate over the parameter list and build the function descriptor
		//Also assign and store slot numbers for parameters
		StringBuilder sb = new StringBuilder();	
		sb.append("(");
		for( INameDef def: args) {
			String desc = def.getType().getDesc();
			sb.append(desc);
			def.getIdent().setSlot(localVars.size());
			localVars.add(new LocalVarInfo(def, desc, null, null));
		}
		sb.append(")");
		sb.append(n.getResultType().getDesc());
		String desc = sb.toString();
		methodDesc.put(name, desc);
		// get method visitor
		MethodVisitor mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, name, desc, null, null);
		// initialize
		mv.visitCode();
		// mark beginning of instructions for method
		Label funcStart = new Label();
		mv.visitLabel(funcStart);
		MethodVisitorLocalVarTable context = new MethodVisitorLocalVarTable(mv, localVars);
		show("Context -> " + localVars);
		//visit block to generate code for statements
		n.getBlock().visit(this, context);
		
		//add return instruction if Void return type
		if(n.getResultType().equals(Type__.voidType)) {
			mv.visitInsn(RETURN);
		}
		
		//add label after last instruction
		Label funcEnd = new Label();
		mv.visitLabel(funcEnd);
		
		addLocals(context, funcStart, funcEnd);

		mv.visitMaxs(0, 0);
		
		//terminate construction of method
		mv.visitEnd();
		return null;

	}

	@Override
	public Object visitIBlock(IBlock n, Object arg) throws Exception {
		List<IStatement> statements = n.getStatements();
		for(IStatement statement: statements) {
			statement.visit(this, arg);
		}
		return null;
	}



	@SuppressWarnings("deprecation")
	@Override
	public Object visitIFunctionCallExpression(IFunctionCallExpression n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;
		show("IFunctionCallExpression n " + n);
		List<LocalVarInfo> localVars = ((MethodVisitorLocalVarTable)arg).localVars;
//		for (LocalVarInfo e: localVars) {
//			mv.visitVarInsn(ILOAD, e.nameDef.getIdent().getSlot());
//		}
		
		for (IExpression e: n.getArgs()) {
			e.visit(this, arg);
		}
		show(methodDesc.get(n.getName().getName()));
		mv.visitMethodInsn(INVOKESTATIC, className, n.getName().getName(), methodDesc.get(n.getName().getName()));
		return null;
	}
	
	@Override
	public Object visitIIdentExpression(IIdentExpression n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;
		INameDef nameDef = (INameDef) visitIIdentifier(n.getName(), arg);
		// Check if it's local variable or global
		if (nameDef != null ) { 
			// For local variable
			if (nameDef.getType().isInt() || nameDef.getType().isBoolean())  
				{ mv.visitVarInsn(ILOAD, nameDef.getIdent().getSlot()); }
			else { mv.visitVarInsn(ALOAD, nameDef.getIdent().getSlot()); }
		}
		// For global variable
		else { mv.visitFieldInsn(GETSTATIC, className, n.getName().getName(), n.getType().getDesc()); }
		return null;
	}
	
	@Override
	public Object visitIIdentifier(IIdentifier n, Object arg) throws Exception {
		List<LocalVarInfo> localVars = ((MethodVisitorLocalVarTable)arg).localVars;
		for (LocalVarInfo i : localVars) {
			// if IIdentifier n is in LocalVars, then it will return its nameDef
			if (n.getName().equals(i.nameDef.getIdent().getName()) ) { return i.nameDef;}
		}
		return null;
	}
	
	@Override
	public Object visitILetStatement(ILetStatement n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;
		List<LocalVarInfo> localVars = ((MethodVisitorLocalVarTable)arg).localVars;
		n.getLocalDef().visit(this, arg);
		n.getLocalDef().getIdent().setSlot(localVars.size());
//		if (n.getLocalDef().getType().isInt()) {n.getLocalDef().getIdent().;
		localVars.add(new LocalVarInfo(n.getLocalDef(), n.getLocalDef().getType().getDesc(), null, null));
		if (n.getExpression() != null) { 
			n.getExpression().visit(this, arg); 
			if (n.getExpression().getType().isInt() || n.getExpression().getType().isBoolean())  
				{ mv.visitVarInsn(ISTORE, n.getLocalDef().getIdent().getSlot());}
			else { mv.visitVarInsn(ASTORE, n.getLocalDef().getIdent().getSlot());}
		}
		n.getBlock().visit(this, arg);
		return null;
	}
		

	
	@Override
	public Object visitIReturnStatement(IReturnStatement n, Object arg) throws Exception {
		//get the method visitor from the arg
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;
		IExpression e = n.getExpression();
		if (e != null) {  //the return statement has an expression
			e.visit(this, arg);  //generate code to leave value of expression on top of stack.
			//use type of expression to determine which return instruction to use
			IType type = e.getType();
			if (type.isInt() || type.isBoolean()) { mv.visitInsn(IRETURN); }
			else  {mv.visitInsn(ARETURN);}
		}
		else { //there is no argument, (and we have verified duirng type checking that function has void return type) so use this return statement.  
			mv.visitInsn(RETURN);
		}
		return null;
	}


	@Override
	public Object visitIWhileStatement(IWhileStatement n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable) arg).mv;
		Label GUARD = new Label();
		Label BREAK = new Label();
		mv.visitLabel(GUARD);
		n.getGuardExpression().visit(this, arg);
		mv.visitJumpInsn(IFEQ, BREAK);
		n.getBlock().visit(this, arg);
		mv.visitJumpInsn(GOTO, GUARD);
		mv.visitLabel(BREAK);
		return null;
	}

	@Override
	public Object visitIIfStatement(IIfStatement n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable) arg).mv;
		Label AFTER = new Label();
		n.getGuardExpression().visit(this, arg);
		mv.visitJumpInsn(IFEQ, AFTER);
		n.getBlock().visit(this, arg);
		mv.visitLabel(AFTER);
		return null;
	}

	@Override
	public Object visitISwitchStatement(ISwitchStatement n, Object arg) throws Exception {
		throw new UnsupportedOperationException("SKIP THIS");

	}

	@Override
	public Object visitIUnaryExpression(IUnaryExpression n, Object arg) throws Exception {
		//get method visitor from arg
		MethodVisitor mv = ((MethodVisitorLocalVarTable) arg).mv;
		//generate code to leave value of expression on top of stack
		n.getExpression().visit(this, arg);
		//get the operator and types of operand and result
		Kind op = n.getOp();
		IType resultType = n.getType();
		IType operandType = n.getExpression().getType();
		switch(op) {
		case MINUS -> {
			mv.visitInsn(INEG);
//			throw new UnsupportedOperationException("IMPLEMENT unary minus");
		}
		case BANG -> {
			if (operandType.isBoolean()) {
				//this is complicated.  Use a Java method instead
//				Label brLabel = new Label();
//				Label after = new Label();
//				mv.visitJumpInsn(IFEQ,brLabel);
//				mv.visitLdcInsn(0);
//				mv.visitJumpInsn(GOTO,after);
//				mv.visitLabel(brLabel);
//				mv.visitLdcInsn(1);
//				mv.visitLabel(after);
				mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "not", "(Z)Z",false);
			}
			else { //argument is List
				throw new UnsupportedOperationException("SKIP THIS");
		}
		}
		default -> throw new UnsupportedOperationException("compiler error");
		}
		return null;
	}


	@Override
	public Object visitIPrimitiveType(IPrimitiveType n, Object arg) throws Exception {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}


	@Override
	public Object visitIAssignmentStatement(IAssignmentStatement n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable) arg).mv;
		show("in visitIAssignmentStatement");
		if (n.getRight()==null) { n.getLeft().visit(this, arg); return null; }
		n.getRight().visit(this, arg);
		List<LocalVarInfo> localVars = ((MethodVisitorLocalVarTable)arg).localVars;
		for (LocalVarInfo i : localVars) {
			if (n.getLeft().getText().equals(i.nameDef.getIdent().getName())) {
				if (i.nameDef.getType().isInt() || i.nameDef.getType().isBoolean())
					{ mv.visitVarInsn(ISTORE, i.nameDef.getIdent().getSlot()); }
				else { mv.visitVarInsn(ASTORE, i.nameDef.getIdent().getSlot()); }
				return null;
			}
		}
		mv.visitFieldInsn(PUTSTATIC, className, n.getLeft().getText(), n.getLeft().getType().getDesc());
		return null;
	}

	@Override
	public Object visitIExpressionStatement(IExpressionStatement n, Object arg) throws Exception {
		throw new UnsupportedOperationException("TO IMPLEMENT");
	}
}
