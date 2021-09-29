package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;
import edu.ufl.cise.plpfa21.assignment2.IPLPParser;
import edu.ufl.cise.plpfa21.assignment2.SyntaxException;



public class CompilerComponentFactory {

	static final char EOFchar = 0;

	//********************************
	static boolean doPrint = false;
	@SuppressWarnings("unused")
	private static void show(Object input) {
        if(doPrint) {
            System.out.println(input.toString());
        }
    }
	//********************************

	public static class Token implements IPLPToken {
		public final Kind kind;
		public final int pos;
		public final int line;
		public final String text;
		public final int posInLine;
		public final int length;

		public Token(String text, Kind kind, int pos, int length, int line, int posInLine) {
			this.text = text;
			this.kind = kind;
			this.pos = pos;
			this.length = length;
			this.line = line;
			this.posInLine = posInLine;
		}

		@Override
		public Kind getKind() {
			// TODO Auto-generated method stub
			return this.kind;
		}
		/**
		 * Returns the actual text of token.
		 * 
		 * For string literals, this includes the delimiters and unprocessed escape sequences.
		 * For example, the text corresponding to this token "abc\'"  would have 7 characters.
		 * 
		 * @return text of token
		 */
		@Override
		public String getText() {
			StringBuilder stringBuilder = new StringBuilder();
			// TODO Auto-generated method stub
			if (this.kind==Kind.STRING_LITERAL) {
				populate_escapeSeqs();
				char[] chars = Arrays.copyOf(this.text.toCharArray(), length+2);
				stringBuilder.append(chars[0]);
				for (char ch : Arrays.copyOfRange(chars, 1, length+1)) {
					if (escapeSeqs.containsKey(ch)) 
						stringBuilder.append(escapeSeqs.get(ch));
					else stringBuilder.append(ch);
				}
				stringBuilder.append(chars[length+1]);
				return stringBuilder.toString();
			}
			return this.text;
		}

		@Override
		public int getLine() {
			// TODO Auto-generated method stub
			return this.line;
		}

		@Override
		public int getCharPositionInLine() {
			// TODO Auto-generated method stub
			return this.posInLine;
		}
		/**
		 * This routine is only defined for string literals, i.e. kind = STRING_LITERAL
		 * It returns a String representing the token after stripping delimiters and handling escape sequences.
		 * For example, the string value of the token with text "abc\'" would have four characters, abc'
		 * 
		 * @return the string value of this token
		 */
		@Override
		public String getStringValue() {
			// TODO Auto-generated method stub
			return this.text.substring(1, length+1);
		}

		@Override
		public int getIntValue() {
			return Integer.parseInt(this.text);
		}
		


	}
	public static HashMap<String, Kind> reservedWords = new HashMap<>();
	public static void populate_reservedWords() {
		reservedWords.put("=", Kind.ASSIGN);
		reservedWords.put(",", Kind.COMMA);
		reservedWords.put(":", Kind.COLON);
		reservedWords.put("(", Kind.LPAREN);
		reservedWords.put(")", Kind.RPAREN);
		reservedWords.put("[", Kind.LSQUARE);
		reservedWords.put("]", Kind.RSQUARE);
		reservedWords.put("&&", Kind.AND);
		reservedWords.put("||", Kind.OR);
		reservedWords.put("<", Kind.LT);
		reservedWords.put(">", Kind.GT);
		reservedWords.put("==", Kind.EQUALS);
		reservedWords.put("!=", Kind.NOT_EQUALS);
		reservedWords.put("!", Kind.BANG);
		reservedWords.put("+", Kind.PLUS);
		reservedWords.put("-", Kind.MINUS);
		reservedWords.put("*", Kind.TIMES);
		reservedWords.put("/", Kind.DIV);
		reservedWords.put("VAR", Kind.KW_VAR);
		reservedWords.put("VAL", Kind.KW_VAL);
		reservedWords.put("FUN", Kind.KW_FUN);
		reservedWords.put("DO", Kind.KW_DO);
		reservedWords.put("END", Kind.KW_END);
		reservedWords.put("LET", Kind.KW_LET);
		reservedWords.put("SWITCH", Kind.KW_SWITCH);
		reservedWords.put("CASE", Kind.KW_CASE);
		reservedWords.put("DEFAULT", Kind.KW_DEFAULT);
		reservedWords.put("IF", Kind.KW_IF);
		reservedWords.put("WHILE", Kind.KW_WHILE);
		reservedWords.put("RETURN", Kind.KW_RETURN);
		reservedWords.put("NIL", Kind.KW_NIL);
		reservedWords.put("TRUE", Kind.KW_TRUE);
		reservedWords.put("FALSE", Kind.KW_FALSE);
		reservedWords.put("INT", Kind.KW_INT);
		reservedWords.put("STRING", Kind.KW_STRING);
		reservedWords.put("BOOLEAN", Kind.KW_BOOLEAN);
		reservedWords.put("LIST", Kind.KW_LIST);
	}

	public static HashMap<Character, String> escapeSeqs = new HashMap<Character, String>();
	public static void populate_escapeSeqs() {
		escapeSeqs.put('\b', "\\b");
		escapeSeqs.put('\t', "\\t");
		escapeSeqs.put('\n', "\\n");
		escapeSeqs.put('\r', "\\r");
		escapeSeqs.put('\f', "\\f");
		escapeSeqs.put('\"', "\\\"");
		escapeSeqs.put('\'', "\\\'");
		escapeSeqs.put('\\', "\\\\");
	}

//	public static Set<Character> escapeSeqSet = new HashSet<Character>();

	public static enum State {
		START,
		STRING_LITERAL,
		INT_LITERAL,
		IDENTIFIER,
	}



	public static class IPLPLexer1 implements IPLPLexer {
		private int outputSeq = 0;
		private ArrayList<Token> tokens = new ArrayList<Token>();
		@Override
		public Token nextToken() throws LexicalException {
			Token token = tokens.get(outputSeq++);
			if (token.kind == Kind.INT_LITERAL)
				try {
					Integer.parseInt(token.text);
				} catch (NumberFormatException e) {
					throw new LexicalException("Integer Outflow!", token.line, token.posInLine);
				}
			else if (token.kind == Kind.STRING_LITERAL)
				switch (token.pos) {
				case -404 -> {
					show("Error Code 404...");
					throw new LexicalException("Missing Another String Delimiter: " + token.getText(), token.line, token.posInLine); }
				case -505 -> {
					show("Error Code 505...");
					throw new LexicalException("Illegal Symbol \\: " + token.getText(), token.line, token.posInLine); }
				case -606 -> {
					show("Error Code 606...");
					throw new LexicalException("Missing */ " + token.getText(), token.line, token.posInLine); }
				case -1001 -> {
					throw new LexicalException("I don't know what's wrong!!! " + token.getText(), token.line, token.posInLine); }
				default -> {break;}
			}
			else if (token.kind  == null)
				throw new LexicalException("Unknown Symbol: " + token.getText(), token.line, token.posInLine);

			return token;
		}
	}
	
	public static class IPLPParser1 implements IPLPParser {
		private int outputSeq;
		private ArrayList<Token> tokens = new ArrayList<Token>();
		private Token token;
		public IPLPParser1(ArrayList<Token> tokens) {
			this.tokens = tokens;
			this.outputSeq = 0;
			this.token = tokens.get(outputSeq++);
		}
		@Override
		public void parse() throws Exception {
			while (token.getKind() != Kind.EOF) {
				switch (token.getKind()) {
				case KW_VAR -> {
					consume();
					nameDef();				show("In case of KW_VAR nameDef() OK!");
					switch (token.getKind()) {
					case SEMI -> {
						match(Kind.SEMI); }
					case ASSIGN -> {
						consume();
						expression();
						match(Kind.SEMI);
					}
					default -> throw new SyntaxException("Expected = or ;", token.line, token.posInLine);
					}
					parse();
				}
				case KW_VAL -> {
					consume();
					nameDef();
					match(Kind.ASSIGN);		show("In case of KW_VAL, match(Kind.ASSIGN); OK!");
					expression();
					match(Kind.SEMI);
					parse();
				}
				case KW_FUN -> {
					consume(); 
					match(Kind.IDENTIFIER); show("In case of KW_FUN, match(Kind.IDENTIFIER); OK");
					match(Kind.LPAREN);		show("In case of KW_FUN, match(Kind.LPAREN); OK!");
					nameDefList();			show("In case of KW_FUN, nameDefList(); OK!");
					match(Kind.RPAREN);		show("In case of KW_FUN, match(Kind.RPAREN);");
					if (token.getKind()==Kind.LPAREN) {
						consume(); 
						match(Kind.COLON); 
						type(); 
						match(Kind.RPAREN);
						}
					match(Kind.KW_DO);		show("In case of KW_FUN, match(Kind.KW_DO); OK!");
					block();				show("In case of KW_FUN, block() OK!");
					match(Kind.KW_END);		show("In case of KW_FUN, match(Kind.KW_END); OK!");
					parse();
				}
				default -> throw new SyntaxException("Expected Kind (VAR, VAL, FUN); But Kind is " + 
				token.getKind(), token.line, token.posInLine);
				}
			}
		}

		void block() throws SyntaxException {
			if (token.getKind()==Kind.KW_END || 
					token.getKind()==Kind.EOF) 
				return;
			statement();
			block();
		}
		
		void statement() throws SyntaxException {
			switch(token.getKind()) {
			case KW_RETURN -> {
				consume(); 
				expression(); 
				match(Kind.SEMI);}
			case KW_WHILE, 
				KW_IF -> {
				consume(); 
				expression(); 		show("expression() OK! ");
				match(Kind.KW_DO); 	show("match(Kind.KW_DO) OK!"); 
				block(); 			show("block() OK!");
				match(Kind.KW_END);}
			case KW_LET -> {
				consume(); 
				nameDef();			show("In case of KW_LET, nameDef() ok! Next Kind -> " + token.getKind());
				if (token.getKind()==Kind.ASSIGN) {
					consume(); 
					expression();
					}
				match(Kind.SEMI);
				}
			case KW_SWITCH -> {
				consume(); 	
				expression();
				while (token.getKind()==Kind.KW_CASE) {
					consume(); 
					expression(); 
					match(Kind.COLON);
					while (token.getKind()!= Kind.KW_CASE &&
							token.getKind()!=Kind.KW_DEFAULT &&
							token.getKind()!=Kind.KW_END && 
							token.getKind()!=Kind.EOF)
						{statement();			show("Case statement match OK!"); }
					}
				match(Kind.KW_DEFAULT); 
				block(); 
				match(Kind.KW_END);
				}
			case EOF, KW_END -> {return;}
			default -> { 
				expression(); 
				if (token.getKind()==Kind.ASSIGN) {
					consume(); 
					expression();
					} 
				match(Kind.SEMI); 
				return;
				}
			}
		}
		
		void expression() throws SyntaxException {
			logicalExpression();
			return;
		}
		
		void logicalExpression() throws SyntaxException {
			comparisonExpression();
			if (token.getKind()==Kind.OR || 
					token.getKind()==Kind.AND) {
				consume();
				logicalExpression();
			}
			return;
		}
		
		void comparisonExpression() throws SyntaxException {
			additiveExpression();
			if (token.getKind()==Kind.GT || 
					token.getKind()==Kind.LT ||
					token.getKind()==Kind.EQUALS ||
					token.getKind()==Kind.NOT_EQUALS) {
				consume();
				comparisonExpression();
			}
			return;
		}
		
		void additiveExpression() throws SyntaxException {
			multiplicativeExpression();
			if (token.getKind()==Kind.PLUS || 
					token.getKind()==Kind.MINUS) {
				consume();
				additiveExpression();
			}
			return;
		}
		
		void multiplicativeExpression() throws SyntaxException {
			unaryExpression();
			if (token.getKind()==Kind.TIMES ||
					token.getKind()==Kind.DIV) {
				consume();
				multiplicativeExpression();
			}
			return;
		}
		
		void unaryExpression() throws SyntaxException {
			if (token.getKind()==Kind.BANG || 
					token.getKind()==Kind.MINUS)
				consume();
			primaryExpression();
		}
		
		void primaryExpression() throws SyntaxException {
			switch (token.getKind()) {
			case INT_LITERAL, 
				STRING_LITERAL, 
				KW_TRUE, 
				KW_FALSE, 
				KW_BOOLEAN, 
				KW_NIL -> consume();
			case LPAREN -> {
				consume(); 
				expression(); 
				match(Kind.RPAREN);
				}
			case IDENTIFIER -> {
				consume();
				switch (token.getKind()) {
				case LPAREN ->	{
					consume(); 
					if (token.getKind()!=Kind.RPAREN) {
						expression();
						while (token.getKind()==Kind.COMMA) {
							consume(); 
							expression();
							}
					}	
					match(Kind.RPAREN);
					}
				case LSQUARE -> {consume(); expression(); match(Kind.RSQUARE);}
				default -> {return;}
				}
			}
			default -> throw new IllegalArgumentException("Unexpected value: " + token.getKind());
			}
			return;
		}
		
		void nameDefList() throws SyntaxException {
			switch (token.getKind()) {
			case RPAREN -> {return;}
			case IDENTIFIER -> {
				nameDef();
				if (token.getKind()==Kind.COMMA) {
					consume(); 
					nameDefList();
					}
				}
			default -> throw new SyntaxException("Unexpected Kind: " + token.getKind(), token.line, token.posInLine);
			}
		}
		
		void nameDef() throws SyntaxException {
			try {
				match(Kind.IDENTIFIER);
			} catch (SyntaxException e) {
				throw e;
			}
			if (token.getKind() == Kind.COLON) {
				consume();
				type(); 
			}
			return;
		}
		
		void type() throws SyntaxException {
			switch (token.getKind()) {
			case KW_INT, 
				KW_STRING,
				KW_BOOLEAN -> { 
					consume(); 
					return;
				}
			case KW_LIST -> {
				consume();
				match(Kind.LSQUARE);
				if (token.getKind() != Kind.RSQUARE) 
					{type();show("sdfs   " + token.getKind());}
				match(Kind.RSQUARE);
				}
			default -> throw new SyntaxException("Unexpected Kind: " + token.getKind(), token.line, token.posInLine);
			}
			return;
		}

		void match(Kind kind) throws SyntaxException {
			if (token.getKind() == kind) {
				token = tokens.get(outputSeq++);
				return;
			}
			throw new SyntaxException("saw " + token.kind + "expected " + kind, token.line, token.posInLine);
		}
		
		private void consume() throws SyntaxException {
	 		show("TO BE COMSUMED :: Kind:" + token.getKind() + " line:" + token.getLine() +
					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
			if(outputSeq < tokens.size())
				token = tokens.get(outputSeq++);
			else
				throw new SyntaxException("[ERROR] No more tokens found", token.line, token.posInLine);
		}
	}
	
	@SuppressWarnings("unused")
	public static IPLPParser1 getParser(String input) {
		ArrayList<Token> tokens = new ArrayList<Token>();
		IPLPLexer1 lexer = getLexer(input);
		while (lexer.outputSeq < lexer.tokens.size())
			try {
				tokens.add(lexer.nextToken());
			} catch (LexicalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		IPLPParser1 parser = new IPLPParser1(tokens);
		return parser;
	}
	


	static IPLPLexer1 getLexer(String input) {
		//TODO  create and return a Lexer instance to parse the given input.
		int numChars = input.length();
		char[] chars = Arrays.copyOf(input.toCharArray(), numChars+1);
		chars[numChars] = EOFchar;
		populate_reservedWords();
		IPLPLexer1 lexer = new IPLPLexer1();
		int pos = 0;			//position in char[]
		int line = 1;
		int length = 0;
		int posInLine = 0;		//position in line
		char ch;
		String text;
		State state = State.START;

		while (pos < chars.length) {
			switch (state) {
			case START -> {
				ch = chars[pos];
				switch(ch) {
				case '\n' -> 
					{ line++; posInLine = 0; pos++; }
				case '\"', 
					'\'' -> 
					{ state = State.STRING_LITERAL; pos++; posInLine++; }
				case '\b',
					'\t',
					'\r',
					'\f',
					'\\', 
					' ' -> 
					{ pos++; posInLine++; }
				case '('->
					{ lexer.tokens.add(new Token(String.valueOf(ch), Kind.LPAREN, pos, 1, line, posInLine)); pos++; posInLine++;  }
				case ')'->
					{ lexer.tokens.add(new Token(String.valueOf(ch), Kind.RPAREN, pos, 1, line, posInLine)); pos++; posInLine++; }
				case '['->
					{ lexer.tokens.add(new Token(String.valueOf(ch), Kind.LSQUARE, pos, 1, line, posInLine)); pos++; posInLine++; }
				case ']'->
					{ lexer.tokens.add(new Token(String.valueOf(ch), Kind.RSQUARE, pos, 1, line, posInLine));pos++; posInLine++; }
				case '+'->
					{ lexer.tokens.add(new Token(String.valueOf(ch), Kind.PLUS, pos, 1, line, posInLine)); pos++; posInLine++; }
				case '-'->
					{ lexer.tokens.add(new Token(String.valueOf(ch), Kind.MINUS, pos, 1, line, posInLine));pos++; posInLine++; }
				case '*'->
					{ lexer.tokens.add(new Token(String.valueOf(ch), Kind.TIMES, pos, 1, line, posInLine)); pos++; posInLine++; }
				case '>'->
					{ lexer.tokens.add(new Token(String.valueOf(ch), Kind.GT, pos, 1, line, posInLine));pos++; posInLine++; }
				case '<'->
					{ lexer.tokens.add(new Token(String.valueOf(ch), Kind.LT, pos, 1, line, posInLine)); pos++; posInLine++; }
				case ';'->
					{ lexer.tokens.add(new Token(String.valueOf(ch), Kind.SEMI, pos, 1, line, posInLine));pos++; posInLine++; }
				case ','->
					{ lexer.tokens.add(new Token(String.valueOf(ch), Kind.COMMA, pos, 1, line, posInLine));pos++; posInLine++; }
				case ':'->
					{ lexer.tokens.add(new Token(String.valueOf(ch), Kind.COLON, pos, 1, line, posInLine)); pos++; posInLine++; }
				case '/'-> {
					if (chars[pos+1] == '*') {
						pos++;
						posInLine++;
						while (pos<chars.length && chars[pos]!= EOFchar && (chars[pos] != '*' || 
								chars[pos] == '*' && chars[pos+1] != '/')) 
							{ pos++; posInLine++; }
						if (chars[pos] == '*' && chars[pos+1] == '/') {pos = pos+2; posInLine= posInLine+2;}
					} 
					else { 
						lexer.tokens.add(new Token(String.valueOf(ch), Kind.DIV, pos, 1, line, posInLine)); pos++; posInLine++; }
				}
				case '='-> {
					if (chars[pos+1] == '=') {
						lexer.tokens.add(new Token("==", Kind.EQUALS, pos, 2, line, posInLine));
						pos = pos+2;
						posInLine = posInLine+2;
					} else { lexer.tokens.add(new Token(String.valueOf(ch), Kind.ASSIGN, pos, 1, line, posInLine)); pos++; posInLine++; }
				}
				case '&', 
					'|'-> {
					if (chars[pos+1] == '&') {
						lexer.tokens.add(new Token("&&", Kind.AND, pos, 2, line, posInLine));
						pos = pos+2;
						posInLine = posInLine+2;
					} else if (chars[pos+1] == '|') {
						lexer.tokens.add(new Token("||", Kind.OR, pos, 2, line, posInLine));
						pos = pos+2;
						posInLine = posInLine+2;
					} else {lexer.tokens.add(new Token(String.valueOf(ch), null, pos, 1, line, posInLine)); pos++; posInLine++;}
				}

				case '!'-> {
					if (chars[pos+1] == '=') {
						lexer.tokens.add(new Token("!=", Kind.NOT_EQUALS, pos, 2, line, posInLine));
						pos = pos+2;
						posInLine = posInLine+2;
					} else { lexer.tokens.add(new Token(String.valueOf(ch), Kind.BANG, pos, 1, line, posInLine)); pos++; posInLine++;}
				}
				case EOFchar->
					{ lexer.tokens.add(new Token("", Kind.EOF, 0, 0, 0, 0)); pos++; posInLine++; }
				default -> {
					pos++; posInLine++;
					if (Character.isJavaIdentifierStart(ch)) {
						state = State.IDENTIFIER;
						length++;
					} else if (Character.isDigit(ch)) {
						state = State.INT_LITERAL;
						length++;
					} else { lexer.tokens.add(new Token(String.valueOf(ch), null, pos, 1, line, posInLine)); pos++; posInLine++; } }
				}
			}
			case IDENTIFIER -> {
				state = State.START;
				while (pos<chars.length && Character.isJavaIdentifierPart(chars[pos]) && chars[pos]!= EOFchar) {
					length++; pos++; posInLine++; }
				text = new String(chars, pos-length, length);
				show("IDENTIFIER-> " + text + "  pos->" + pos);
				if (reservedWords.containsKey(new String(chars, pos-length, length)))
					lexer.tokens.add(new Token(text, reservedWords.get(text), pos, length, line, posInLine-length));
				else lexer.tokens.add(new Token(text, Kind.IDENTIFIER, pos-length, length, line, posInLine-length));
				length = 0;
			}
			case INT_LITERAL -> {
				state = State.START;
				while (pos<chars.length && Character.isDigit(chars[pos]) && chars[pos]!= EOFchar) {
					length++; pos++; posInLine++; }
				show("INT LITERAL-> " + new String(chars, pos-length, length) + "  pos->" + pos);
				lexer.tokens.add(new Token(new String(chars, pos-length, length), Kind.INT_LITERAL, pos-length, length, line, posInLine-length));
				length = 0;
			}
			case STRING_LITERAL -> {
				state = State.START;
				while (chars[pos] != chars[pos-length-1] && chars[pos] != EOFchar && chars[pos] !='\\') {
					pos++; posInLine++; length++;}
				if (chars[pos]==EOFchar)
					lexer.tokens.add(new Token(String.valueOf(chars[pos-length]), Kind.STRING_LITERAL, -404, length, line, posInLine-length-1));
				else if (chars[pos]=='\\') {
					while (chars[pos] != chars[pos-length-1] && chars[pos] != EOFchar) {pos++; posInLine++; length++;}
					lexer.tokens.add(new Token(new String(chars, pos-length-1, length+2), Kind.STRING_LITERAL, -505, length, line, posInLine-length-1));
				}
				else {
					lexer.tokens.add(new Token(new String(chars, pos-length-1, length+2), Kind.STRING_LITERAL, pos, length, line, posInLine-length-1));
				}
				pos++; 
				posInLine++;
				length = 0;
			}
			default ->
				{ assert false; }
			}
		}
		return lexer;
	}
}


