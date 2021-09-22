package edu.ufl.cise.plpfa21.assignment2;



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.ufl.cise.plpfa21.assignment1.CompilerComponentFactory;


class ExampleParserTests {
	
	static boolean VERBOSE = false;
	
	static boolean doPrint = true;
	@SuppressWarnings("unused")
	private static void show(Object input) {
        if(doPrint) {
            System.out.println(input.toString());
        }
    }
	
	void noErrorParse(String input)  {
		show("**********Let's scan an input***********");
		IPLPParser parser = CompilerComponentFactory.getParser(input); 
		show("finish scanning; got a parser");
		try {
			show("Let's parse");
			parser.parse();
		} catch (Throwable e) {
			throw new RuntimeException(e); 
		}
	}
	

	private void syntaxErrorParse(String input, int line, int column) {
		IPLPParser parser = CompilerComponentFactory.getParser(input);
		assertThrows(SyntaxException.class, () -> {
			try {
			parser.parse();
			}
			catch(SyntaxException e) {
				if (VERBOSE) System.out.println(input + '\n' + e.getMessage());
				Assertions.assertEquals(line, e.line);
				Assertions.assertEquals(column, e.column);
				throw e;
			}
		});
		
	}

	@Test 
	public void test0() {
		String input = """

		""";
		
		noErrorParse(input);
	}
	

	@Test 
	public void test1() {
		String input = """
		VAL a: INT = 0;
		""";
		noErrorParse(input);
	}


	@Test 
	public void test2() {
		String input = """
		VAL a: STRING = "hello";
		""";
		noErrorParse(input);
	}


	@Test 
	public void test3() {
		String input = """
		VAL b: BOOLEAN = TRUE;
		VAR x: LIST[];
		""";
		noErrorParse(input);
	}


	@Test 
	public void test4() {
		String input = """
		VAR b: LIST[];
		""";
		noErrorParse(input);
	}

   //This input has a syntax error at line 2, position 19.
	@Test public void test5()  {
	String input = """
		FUN func() DO
		WHILE x>0 DO x=x-1 END
		END
		""";
		syntaxErrorParse(input,2,19);
	}
	@Test 
	public void test7() {
		String input = """
		VAR a: LIST[BOOLEAN];
		FUN func() DO
		WHILE x>0 DO Y=x-1; END
		END
		VAL b: INT = 1099893-2;
		""";
		noErrorParse(input);
	}
}
