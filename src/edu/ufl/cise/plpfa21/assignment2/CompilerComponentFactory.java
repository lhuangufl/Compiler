package edu.ufl.cise.plpfa21.assignment2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


import edu.ufl.cise.plpfa21.assignment2.PLPTokenKinds.Kind;



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
		private StringBuilder stringBuilder = new StringBuilder();
		public int length;

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

		@Override
		public String getText() {
			// TODO Auto-generated method stub
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

		@Override
		public String getStringValue() {
			// TODO Auto-generated method stub
			int i = 0;
			length = this.text.length();
			while (i<length)
				switch (this.text.charAt(i)) {
					case '\b', '\n', '\t','\r','\f','\\', ' '-> {i++;}
					case ',', ';', ':'-> {i++;}
					default -> {stringBuilder.append(this.text.charAt(i++));}
				}
			return this.stringBuilder.toString();
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
		escapeSeqs.put('\b', null);
		escapeSeqs.put('\t', null);
		escapeSeqs.put('\n', null);
		escapeSeqs.put('\r', null);
		escapeSeqs.put('\f', null);
		escapeSeqs.put('\"', null);
		escapeSeqs.put('\'', null);
		escapeSeqs.put('\\', null);
	}

//	public static Set<Character> escapeSeqSet = new HashSet<Character>();

	public static enum State {
		START,
		STRING_LITERAL,
		INT_LITERAL,
		IDENTIFIER,
	}



	public static class IPLPLexer1 implements IPLPLexer {
		int outputSeq = 0;
		public ArrayList<Token> tokens = new ArrayList<Token>();

		@Override
		public IPLPToken nextToken() throws LexicalException {
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
					throw new LexicalException("Missing \": " + token.getText(), token.line, token.posInLine); }
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

	static IPLPLexer getLexer(String input) {
		//TODO  create and return a Lexer instance to parse the given input.
		int numChars = input.length();
		char[] chars = Arrays.copyOf(input.toCharArray(), numChars+1);
		//**************************
		show(Arrays.toString(chars));
		show("The array length:" + chars.length);
		//**************************
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
				case '\"', '\'' -> {
					state = State.STRING_LITERAL; pos++; posInLine++; }
				case '\b','\t','\r','\f','\\', ' ' -> {
					pos++; posInLine++; }
				case '\n' -> {
					line++;
					posInLine = 0;
					pos++; }
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
						while (pos<chars.length && chars[pos]!= EOFchar && (chars[pos] != '*' || chars[pos] == '*' && chars[pos+1] != '/')) {
							pos++; posInLine++; }
						if (chars[pos] == EOFchar)
							lexer.tokens.add(new Token("*/", Kind.STRING_LITERAL, -606, 1, line, posInLine)); //Missing a symbol of '*/'
						else if (chars[pos] == '*' && chars[pos+1] == '/') {pos = pos+2; posInLine= posInLine+2;}
					} else { lexer.tokens.add(new Token(String.valueOf(ch), Kind.DIV, pos, 1, line, posInLine)); pos++; posInLine++; }
				}
				case '='-> {
					if (chars[pos+1] == '=') {
						lexer.tokens.add(new Token("==", Kind.EQUALS, pos, 2, line, posInLine));
						pos = pos+2;
						posInLine = posInLine+2;
					} else { lexer.tokens.add(new Token(String.valueOf(ch), Kind.ASSIGN, pos, 1, line, posInLine)); pos++; posInLine++; }
				}
				case '&', '|'-> {
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
					lexer.tokens.add(new Token(String.valueOf(chars[pos-length]), Kind.STRING_LITERAL, -404, length, line, posInLine-length));
				else if (chars[pos]=='\\') {
					while (chars[pos] != chars[pos-length-1] && chars[pos] != EOFchar) {pos++; posInLine++; length++;}
					lexer.tokens.add(new Token(new String(chars, pos-length, length), Kind.STRING_LITERAL, -505, length, line, posInLine-length));
				}
				else {
					lexer.tokens.add(new Token(new String(chars, pos-length, length), Kind.STRING_LITERAL, pos, length, line, posInLine-length));
				}
				pos++; posInLine++;
				length = 0;
			}

			default ->
				{ assert false; }
			}
		}
		return lexer;
	}
}


