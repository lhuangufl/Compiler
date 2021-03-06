package edu.ufl.cise.plpfa21.assignment1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ExampleLexerTests implements PLPTokenKinds {

	IPLPLexer getLexer(String input) {
		return CompilerComponentFactory.getLexer(input);
	}
	//********************************
	boolean doPrint = true;
	private void show(Object input) {
        if(doPrint) {
            System.out.println(input.toString());
        }
    }
	//********************************
	 @Test
	 public void test0() throws LexicalException {
	 	String input = """

	 			""";
	 	IPLPLexer lexer = getLexer(input);
	 	{
	 		IPLPToken token = lexer.nextToken();
	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.EOF);
	 	}
	 }

	 @Test
	 public void test1() throws LexicalException {
	 	String input = """
	 			abc
	 			  def
	 			     ghi
	 			""";
	 	IPLPLexer lexer = getLexer(input);
	 	{
	 		IPLPToken token = lexer.nextToken();

	 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());

	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.IDENTIFIER);
	 		int line = token.getLine();
	 		assertEquals(line, 1);
	 		int charPositionInLine = token.getCharPositionInLine();
	 		assertEquals(charPositionInLine, 0);
	 		String text = token.getText();
	 		assertEquals(text, "abc");
	 	}
	 	{
	 		IPLPToken token = lexer.nextToken();

	 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());

	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.IDENTIFIER);
	 		int line = token.getLine();
	 		assertEquals(line, 2);
	 		int charPositionInLine = token.getCharPositionInLine();
	 		assertEquals(charPositionInLine, 2);
	 		String text = token.getText();
	 		assertEquals(text, "def");
	 	}
	 	{
	 		IPLPToken token = lexer.nextToken();

	 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());

	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.IDENTIFIER);
	 		int line = token.getLine();
	 		assertEquals(line, 3);
	 		int charPositionInLine = token.getCharPositionInLine();
	 		assertEquals(charPositionInLine, 5);
	 		String text = token.getText();
	 		assertEquals(text, "ghi");
	 	}
	 	{
	 		IPLPToken token = lexer.nextToken();
	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.EOF);
	 	}
	 }

	 @Test
	 public void test2() throws LexicalException {
	 	String input = """
	 			a123 123a
	 			""";
	 	IPLPLexer lexer = getLexer(input);
	 	{
	 		IPLPToken token = lexer.nextToken();

	 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());

	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.IDENTIFIER);
	 		int line = token.getLine();
	 		assertEquals(line, 1);
	 		int charPositionInLine = token.getCharPositionInLine();
	 		assertEquals(charPositionInLine, 0);
	 		String text = token.getText();
	 		assertEquals(text, "a123");
	 	}
	 	{
	 		IPLPToken token = lexer.nextToken();

	 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());

	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.INT_LITERAL);
	 		int line = token.getLine();
	 		assertEquals(line, 1);
	 		int charPositionInLine = token.getCharPositionInLine();
	 		assertEquals(charPositionInLine, 5);
	 		String text = token.getText();
	 		assertEquals(text, "123");
	 		int val = token.getIntValue();
	 		assertEquals(val, 123);
	 	}
	 	{
	 		IPLPToken token = lexer.nextToken();

	 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());

	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.IDENTIFIER);
	 		int line = token.getLine();
	 		assertEquals(line, 1);
	 		int charPositionInLine = token.getCharPositionInLine();
	 		assertEquals(charPositionInLine, 8);
	 		String text = token.getText();
	 		assertEquals(text, "a");
	 	}
	 	{
	 		IPLPToken token = lexer.nextToken();
	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.EOF);
	 	}
	 }

	@Test
	public void test3() throws LexicalException {
		String input = """
				= == ===
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();

			show("Kind:" + token.getKind() + " line:" + token.getLine() +
					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());

			Kind kind = token.getKind();
			assertEquals(kind, Kind.ASSIGN);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
			String text = token.getText();
			assertEquals(text, "=");

		}
		{
			IPLPToken token = lexer.nextToken();

			show("Kind:" + token.getKind() + " line:" + token.getLine() +
					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());

			Kind kind = token.getKind();
			assertEquals(kind, Kind.EQUALS);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 2);
			String text = token.getText();
			assertEquals(text, "==");

		}
		{
			IPLPToken token = lexer.nextToken();

			show("Kind:" + token.getKind() + " line:" + token.getLine() +
					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());

			Kind kind = token.getKind();
			assertEquals(kind, Kind.EQUALS);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 5);
			String text = token.getText();
			assertEquals(text, "==");

		}
		{
			IPLPToken token = lexer.nextToken();

			show("Kind:" + token.getKind() + " line:" + token.getLine() +
					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());

			Kind kind = token.getKind();
			assertEquals(kind, Kind.ASSIGN);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 7);
			String text = token.getText();
			assertEquals(text, "=");

		}
		{
			IPLPToken token = lexer.nextToken();

			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
			show("Kind:" + kind);
		}
	}

 	@Test
 	public void test4() throws LexicalException {
 		String input = """
 				a %
 				""";
 		IPLPLexer lexer = getLexer(input);
 		{
 			IPLPToken token = lexer.nextToken();

 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());

 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.IDENTIFIER);
 			int line = token.getLine();
 			assertEquals(line, 1);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 0);
 			String text = token.getText();
 			assertEquals(text, "a");
 		}
 		assertThrows(LexicalException.class, () -> {
 			@SuppressWarnings("unused")
 			IPLPToken token = lexer.nextToken();
 		});
 	}

 	@Test
 	public void test5() throws LexicalException {
 		String input = """
 				99999999999999999999999999999999999999999999999999999999999999999999999
 				""";
 		IPLPLexer lexer = getLexer(input);
 		assertThrows(LexicalException.class, () -> {
 			@SuppressWarnings("unused")
 			IPLPToken token = lexer.nextToken();
 		});
 	}

 	@Test
 	public void test6() throws LexicalException {
 		String input = """
 				(+]&&
 				 !=END
 				""";//)*==?:;
 		IPLPLexer lexer = getLexer(input);
 		{
 			IPLPToken token = lexer.nextToken();

 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());

 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.LPAREN);
 			int line = token.getLine();
 			assertEquals(line, 1);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 0);
 			String text = token.getText();
 			assertEquals(text, "(");
 		}
 		{
 			IPLPToken token = lexer.nextToken();

 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());

 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.PLUS);
 			int line = token.getLine();
 			assertEquals(line, 1);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 1);
 			String text = token.getText();
 			assertEquals(text, "+");

 		}
 		{
 			IPLPToken token = lexer.nextToken();

 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());

 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.RSQUARE);
 			int line = token.getLine();
 			assertEquals(line, 1);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 2);
 			String text = token.getText();
 			assertEquals(text, "]");

 		}
 		{
 			IPLPToken token = lexer.nextToken();

 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());

 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.AND);
 			int line = token.getLine();
 			assertEquals(line, 1);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 3);
 			String text = token.getText();
 			assertEquals(text, "&&");
 		}
 		{
 			IPLPToken token = lexer.nextToken();

 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());

 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.NOT_EQUALS);
 			int line = token.getLine();
 			assertEquals(line, 2);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 1);
 			String text = token.getText();
 			assertEquals(text, "!=");
 		}
 		{
 			IPLPToken token = lexer.nextToken();

 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());

 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.KW_END);
 			int line = token.getLine();
 			assertEquals(line, 2);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 3);
 		}
 	}

 	@Test
 	public void test7() throws LexicalException {
 		String input = """
 				"ddssdddd
 				""";
 		IPLPLexer lexer = getLexer(input);
 		assertThrows(LexicalException.class, () -> {
 			@SuppressWarnings("unused")
 			IPLPToken token = lexer.nextToken();
 		});
 	}

 	@Test
 	public void test8() throws LexicalException {
 		String input = """
 				"junk"
 				"das\\dfd"
 				"kje_dhiehihjkl"
 				"k;df\ni\t k\'"
 				""";
 		IPLPLexer lexer = getLexer(input);
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.STRING_LITERAL);
 			int line = token.getLine();
 			assertEquals(line, 1);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 0);
 		}
 		{
 			assertThrows(LexicalException.class, () -> {
 				@SuppressWarnings("unused")
 				IPLPToken token = lexer.nextToken();
 				show("Kind:" + token.getKind() + " line:" + token.getLine() +
 						" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 			});
 		}
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.STRING_LITERAL);
 			int line = token.getLine();
 			assertEquals(line, 3);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 0);
 		}
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.STRING_LITERAL);
 			int line = token.getLine();
 			assertEquals(line, 4);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 0);
 			String string = token.getStringValue();
 			show("String Value is ->" + string);
 			assertEquals(string, "k;df\ni\t k\'");
 			String text = token.getText();
 			show("Text Value is ->" + text + "*Text ends");
 			assertEquals(text, "\"k;df\\ni\\t k\\\'\"");
 		}
 	}
 	@Test
 	public void test9() throws LexicalException {
 		String input = """
 				$junk
 				  268423323435
 				 		INT i = 3
 				FUN(STRING strsrd):
 				""";
 		IPLPLexer lexer = getLexer(input);
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.IDENTIFIER);
 			int line = token.getLine();
 			assertEquals(line, 1);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 0);
 		}
 		{
 			assertThrows(LexicalException.class, () -> {
 				@SuppressWarnings("unused")
 				IPLPToken token = lexer.nextToken();
 				show("Kind:" + token.getKind() + " line:" + token.getLine() +
 						" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 			});
 		}
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.KW_INT);
 			int line = token.getLine();
 			assertEquals(line, 3);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 3);
 		}
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.IDENTIFIER);
 			int line = token.getLine();
 			assertEquals(line, 3);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 7);
 		}
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 		}
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 		}
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 		}
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 		}
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 		}
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 		}
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 		}
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 		}
 	}
 	@Test
 	public void test10() throws LexicalException {
 		String input = """
 				 IF N>3:
 				""";
 		IPLPLexer lexer = getLexer(input);
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.KW_IF);
 			int line = token.getLine();
 			assertEquals(line, 1);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 1);
 		}
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.IDENTIFIER);
 			int line = token.getLine();
 			assertEquals(line, 1);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 4);
 		}
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.GT);
 			int line = token.getLine();
 			assertEquals(line, 1);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 5);
 		}
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.INT_LITERAL);
 			int line = token.getLine();
 			assertEquals(line, 1);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 6);
 		}
 		{
 			IPLPToken token = lexer.nextToken();
 			show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 			Kind kind = token.getKind();
 			assertEquals(kind, Kind.COLON);
 			int line = token.getLine();
 			assertEquals(line, 1);
 			int charPositionInLine = token.getCharPositionInLine();
 			assertEquals(charPositionInLine, 7);
 		}
 	}
	@Test
	public void test11() throws LexicalException {
	 	String input = """
	 			" this is a string "
	 			""";
	 	IPLPLexer lexer = getLexer(input);
	 	{
	 		IPLPToken token = lexer.nextToken();
	 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.STRING_LITERAL);
	 	}
	 }
	@Test
	public void test12() throws LexicalException {
	 	String input = """
	 			abc def \ra\n 
	 			""";
	 	IPLPLexer lexer = getLexer(input);
	 	{
	 		IPLPToken token = lexer.nextToken();
	 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.IDENTIFIER);
	 	}
	 	{
	 		IPLPToken token = lexer.nextToken();
	 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.IDENTIFIER);
	 	}
	 	{
	 		IPLPToken token = lexer.nextToken();
	 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.IDENTIFIER);
	 	}
	 }
	@Test
	public void test36() throws LexicalException{
		String input = """
		"This is a multiline
		string."
		""";
		try{
		IPLPLexer lexer = getLexer(input);
		{
		IPLPToken token = lexer.nextToken();
 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
 		Kind kind = token.getKind();
		assertEquals(Kind.STRING_LITERAL,kind,"at "  + token.getLine() + ":" + token.getCharPositionInLine());
		int line = token.getLine();
		assertEquals(1,line,"line position error for token"+"This is a multiline\nstring.");
		int charPositionInLine = token.getCharPositionInLine();
		assertEquals(0,charPositionInLine,"char position error for token"+"This is a multiline\nstring.");
		String text = token.getText();
		assertEquals("\"This is a multiline\nstring.\"",text,"at "  + token.getLine() + ":" + token.getCharPositionInLine());
		String val = token.getStringValue();
		assertEquals("This is a multiline\nstring.",val,"at "  + token.getLine() + ":" + token.getCharPositionInLine());
		}
		{
		IPLPToken token = lexer.nextToken();
		Kind kind = token.getKind();
		assertEquals(Kind.EOF,kind);
		}
		}
			catch(LexicalException e) {
			    System.out.println("Unexpected LexicalException:  " + e.getMessage());
			    System.out.println("input=");
			    System.out.println(input);
			    System.out.println("-----------");
			    throw e;
		    }
			catch(Throwable e) {
			    System.out.println(e.getClass() + "  "  + e.getMessage());
			    System.out.println("input=");
			    System.out.println(input);
			    System.out.println("-----------");
			    throw e;
		    }
		}
	@Test
	public void test13() throws LexicalException {
	 	String input = """
	 			!= ==!= !=== !=3 
	 			""";
	 	IPLPLexer lexer = getLexer(input);
	 	{
	 		IPLPToken token = lexer.nextToken();
	 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.NOT_EQUALS);
	 	}
	 	{
	 		IPLPToken token = lexer.nextToken();
	 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.EQUALS);
	 	}
	 	{
	 		IPLPToken token = lexer.nextToken();
	 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.NOT_EQUALS);
	 	}
	 	{
	 		IPLPToken token = lexer.nextToken();
	 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.NOT_EQUALS);
	 	}
	 	{
	 		IPLPToken token = lexer.nextToken();
	 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.EQUALS);
	 	}
	 	{
	 		IPLPToken token = lexer.nextToken();
	 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.NOT_EQUALS);
	 	}
	 	{
	 		IPLPToken token = lexer.nextToken();
	 		show("Kind:" + token.getKind() + " line:" + token.getLine() +
 					" pos:" + token.getCharPositionInLine() + " Text:" + token.getText());
	 		Kind kind = token.getKind();
	 		assertEquals(kind, Kind.INT_LITERAL);
	 	}
	 }	
	
 }
