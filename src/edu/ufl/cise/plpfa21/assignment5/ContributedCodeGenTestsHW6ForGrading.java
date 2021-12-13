package edu.ufl.cise.plpfa21.assignment5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class ContributedCodeGenTestsHW6ForGrading extends CodeGenTestsBaseHW6ForGrading{

		
	@DisplayName("John_Bright_test0")
	@Test
	public void John_Bright_test0(TestInfo testInfo) throws Exception {
		String input = """
				FUN a(x:INT): INT
				DO
				   RETURN x+1;
				END

				FUN main(y: INT): INT
				DO
				   RETURN a(a(a(a(y))))+2;
				END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { 7 };
		int result = (int) loadClassAndRunMethod(bytecode, className, "main", params);
		assertEquals(13, result);
		});
	}
	@DisplayName("John_Bright_test1")
	@Test
	public void John_Bright_test1(TestInfo testInfo) throws Exception {
		String input = """
				FUN isEven(x:INT): BOOLEAN
				DO
				   LET even:BOOLEAN = TRUE DO
				   LET i=0 DO
				   WHILE i < x DO
				   		even= !even;
				   		i=i+1;
				   END
				   END
				   RETURN even;
				   END				   
				END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { 8 };
		boolean result = (boolean) loadClassAndRunMethod(bytecode, className, "isEven", params);
		assertEquals(true, result);
		});
	}

	@DisplayName("John_Bright_test2")
	@Test
	public void John_Bright_test2(TestInfo testInfo) throws Exception {
		String input = """
				FUN isEven(x:INT): BOOLEAN
				DO
				    LET even:BOOLEAN = TRUE DO
				   LET i=0 DO
				   WHILE i < x DO
				   		even= !even;
				   		i=i+1;
				   END
				   END
				   RETURN even;
				   END
				END
				
				FUN collatz(y:INT): INT
				DO
					WHILE y>1 DO
						IF isEven(y)==TRUE DO
							y=y/2;
						END
						IF !isEven(y)==TRUE DO
							IF y==1 DO
								RETURN 1;
							END
							y=(3*y+1)/2;
						END
					END
				RETURN x;
				END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { 300 };
		int result = (int) loadClassAndRunMethod(bytecode, className, "collatz", params);
		assertEquals(1, result);
	    });
	    }
	@DisplayName("John_Bright_test3")
	@Test
	public void John_Bright_test3(TestInfo testInfo) throws Exception {
		String input = """
				FUN factorial(x:INT): INT
				DO
					LET y = x DO
					x=x-1;
				   WHILE x>0 DO
				   		y=y*x;
				   		x=x-1;
				   END
				   RETURN y;
				   END				   
				END
				FUN combination(n:INT, c:INT): INT
				DO
					RETURN factorial(n)/(factorial(n-c)*factorial(c));
				END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = {5, 2};
		int result = (int) loadClassAndRunMethod(bytecode, className, "combination", params);
		assertEquals(10, result);
	    });
	    }

	
	/*
	 * Calculates the nth fibonacci number
	 *  input: 0 1 2 3 4 5 ...
	 * output: 0 1 2 3 5 8 ...
	 */
	@DisplayName("michael_hoffman_test0")
	@Test
	public void michael_hoffman_test0(TestInfo testInfo) throws Exception {
		String input = """
				FUN fib(x:INT): INT
				DO
					IF x < 0 DO RETURN 0; END
					IF x == 0 DO RETURN 1; END
					LET a:INT = 1 DO 
						LET b:INT = 0 DO 
							WHILE x > 0 DO
								a = a + b;
								b = a - b;
								x = x - 1;
							END
							RETURN a;
						END
					END
				END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { 11 };
		int result = (int) loadClassAndRunMethod(bytecode, className, "fib", params);
		assertEquals(144, result);
	    });
	    }
	
	/*
	 * Calculates the square root of x with i iterations
	 */
	@DisplayName("michael_hoffman_test1")
	@Test
	public void michael_hoffman_test1(TestInfo testInfo) throws Exception {
		String input = """
				FUN sqrt(x:INT, i:INT): INT
				DO
					IF x < 1 DO RETURN 0; END
					IF x == 1 DO RETURN 1; END
					
					LET y:INT = 2 DO 
						WHILE i > 0 DO
							y = (x / y + y) / 2;
							i = i - 1;
						END
						RETURN y;
					END
				END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { 169, 10 };
		int result = (int) loadClassAndRunMethod(bytecode, className, "sqrt", params);
		assertEquals(13, result);
	    });
	    }
	
	
	/*
	 * Approximates the first n digits of PI
	 * PI = 4 * (1/1 - 1/3 + 1/5 - 1/7 + 1/9 ...)
	 */
	@DisplayName("michael_hoffman_test2")
	@Test
	public void michael_hoffman_test2(TestInfo testInfo) throws Exception {
		String input = """
				FUN pi(n:INT): INT
				DO	
					LET s:INT = 4 DO
						WHILE n > 1 DO
							s = s * 10;
							n = n - 1;
						END
						LET i:INT = 1 DO
							LET x:INT = 0 DO 
								WHILE i < s DO
									x = x + s / i;
									i = i + 2;
									x = x - s / i;
									i = i + 2;
								END
								RETURN x;
							END
						END
					END
				END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { 6 };
		int result = (int) loadClassAndRunMethod(bytecode, className, "pi", params);
		assertEquals(314167, result);
	    });
	    }
	
	/*
	 * Calculates the greatest common divisor of a and b
	 */
	@DisplayName("michael_hoffman_test3")
	@Test
	public void michael_hoffman_test3(TestInfo testInfo) throws Exception {
		String input = """
				FUN mod(n:INT, d:INT): INT
				DO
					LET q:INT = n / d
					DO
						RETURN n - q * d;
					END
				END
				
				FUN gcd(a:INT, b:INT): INT
				DO	
					LET t:INT
					DO
						WHILE b != 0 DO
							t = b;
							b = mod(a, b);
							a = t; 
						END
					END
					RETURN a;
				END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { 54, 24 };
		int result = (int) loadClassAndRunMethod(bytecode, className, "gcd", params);
		assertEquals(6, result);
	    });
	    }



    // Tests if local is still present after function call
    @DisplayName("Michael_Ivanov_test0")
    @Test
    public void Michael_Ivanov_test0(TestInfo testInfo) throws Exception {
        String input = """
               FUN a(y:BOOLEAN):BOOLEAN DO
                  RETURN y;
               END
			   FUN f():BOOLEAN DO
			      LET x:BOOLEAN = FALSE DO
				     a(TRUE);
				     RETURN x;
				  END
                END
			   """;
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
        show(CodeGenUtils.bytecodeToString(bytecode));
        boolean result = (boolean) loadClassAndRunMethod(bytecode, className, "f", null);
        assertEquals(false, result);
	    });
	    }

    // While loop with function call
    @DisplayName("Michael_Ivanov_test1")
    @Test
    public void Michael_Ivanov_test1(TestInfo testInfo) throws Exception {
        String input = """
                VAR sum = 0;
               	FUN inc():INT DO
               	    sum = sum + 1;
               	    RETURN sum;
               	END
				FUN a(x:INT):INT
				DO
				  WHILE inc() < 10 DO
                     x = x + 5;
				     END
				  RETURN x;
			    END
                """;
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
        show(CodeGenUtils.bytecodeToString(bytecode));
        Object[] params = { 0 };
        int result = (int) loadClassAndRunMethod(bytecode, className, "a", params);
        assertEquals(45, result);
	    });
	    }

    // Tests less than operator for booleans
    @DisplayName("Michael_Ivanov_test2")
    @Test
    public void Michael_Ivanov_test2(TestInfo testInfo) throws Exception {
        String input = """
				VAR x = TRUE;
				VAR y = (FALSE < x);
				VAR z = (TRUE < x);
				VAR a = FALSE;
				VAR b = (FALSE < a);
				VAR c = (TRUE < a);
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
        show(CodeGenUtils.bytecodeToString(bytecode));
        Class<?> testClass = getClass(bytecode, className);
        assertEquals(true, getBoolean(testClass, "y"));
        assertEquals(false, getBoolean(testClass, "z"));
        assertEquals(false, getBoolean(testClass, "b"));
        assertEquals(false, getBoolean(testClass, "c"));
	    });
	    }
    @DisplayName("Michael_Ivanov_test3")
    @Test
    public void Michael_Ivanov_test3(TestInfo testInfo) throws Exception {
        String input = """
				VAR x = TRUE;
				VAR y = (FALSE > x);
				VAR z = (TRUE > x);
				VAR a = FALSE;
				VAR b = (FALSE > a);
				VAR c = (TRUE > a);
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
        show(CodeGenUtils.bytecodeToString(bytecode));
        Class<?> testClass = getClass(bytecode, className);
        assertEquals(false, getBoolean(testClass, "y"));
        assertEquals(false, getBoolean(testClass, "z"));
        assertEquals(false, getBoolean(testClass, "b"));
        assertEquals(true, getBoolean(testClass, "c"));
	    });
	    }

	@DisplayName("danielJohnsontest0")
	@Test
	public void danielJohnsontest0(TestInfo testInfo) throws Exception {
		String input = """
				FUN a(x:INT): INT
				DO
				   RETURN x+1;
				END
				
				FUN b(z:INT): INT
				DO
				   RETURN z-1;
				END
				
				FUN c(w:INT): INT
				DO
				   RETURN w*w;
				END

				FUN main(y: INT): INT
				DO
				   RETURN a(y)+b(y)+c(y);
				END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { 2 };
		int result = (int) loadClassAndRunMethod(bytecode, className, "main", params);
		assertEquals(8, result);
	    });
	    }
	
	@DisplayName("danielJohnsontest1")
	@Test
	public void danielJohnsontest1(TestInfo testInfo) throws Exception {
		String input = """
				VAR sum = 0;
				FUN a(i:INT, j:INT, end:INT, by:INT):INT
				DO
				  WHILE i < end DO
				  j = 1;
				  	 WHILE j < end DO
				     	sum = sum + j;
				     	j = j + by;
				     END
				     i = i + by;
				  END
				  RETURN sum;
				END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { 1, 1, 5, 1 };
		int result = (int) loadClassAndRunMethod(bytecode, className, "a", params);
		assertEquals(40, result);
	    });
	    }
	
	@DisplayName("danielJohnsontest2")
	@Test
	public void danielJohnsontest2(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = 0;
				VAR y = 1;
				FUN g():STRING
				DO
				IF x==0 DO IF y==0 DO RETURN "both are zero"; END
				RETURN "y not zero"; END
				RETURN "x not zero";
				END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		String result = (String) loadClassAndRunMethod(bytecode, className, "g", null);
		assertEquals("y not zero", result);
	    });
	    }
	
	@DisplayName("danielJohnsontest3")
	@Test
	public void danielJohnsontest3(TestInfo testInfo) throws Exception {
		String input = """
				VAR result = 0;
				FUN pow(i:INT,base:INT, exp:INT):INT
				DO
				  WHILE i < exp DO
				  result = result + base*base;
				  i = i + 1;
				  END
				  RETURN result;
				END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { 1, 3, 2 };
		int result = (int) loadClassAndRunMethod(bytecode, className, "pow", params);
		assertEquals(9, result);
	    });
	    }



        @DisplayName("jayavidhi_kumar_test0")
        @Test
        public void jayavidhi_kumar_test0(TestInfo testInfo) throws Exception {
                String input = """
                                VAR x = 0;
                                VAL y = 1;
                                FUN g():STRING
                                DO
                                IF x!=y DO RETURN "zero"; END
                                RETURN "not zero";
                                END
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                String result = (String) loadClassAndRunMethod(bytecode, className, "g", null);
                assertEquals("zero", result);
        	    });
        }

        @DisplayName("jayavidhi_kumar_test1")
        @Test
        public void jayavidhi_kumar_test1(TestInfo testInfo) throws Exception {
                String input = """
                                VAR sum = "HELLO";
                                FUN a(i:INT, end:INT, by:BOOLEAN):STRING
                                DO
                                  WHILE by == TRUE DO
                                     IF i < end DO
                                                by = FALSE;
                                        END
                                  END
                                  RETURN sum+"WORLD";
                                END
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { 1, 5, true };
                String result = (String) loadClassAndRunMethod(bytecode, className, "a", params);
                assertEquals("HELLOWORLD", result);
        	    });
        }


        @DisplayName("jayavidhi_kumar_test2")
        @Test
        public void jayavidhi_kumar_test2(TestInfo testInfo) throws Exception {
                String input = """
                                VAR sum = 0;
                                FUN a(i:INT, end:INT, by:INT, bool:BOOLEAN, str: STRING):STRING
                                DO
                                  WHILE i < end DO
                                     sum = sum + i;
                                     i = i + by;
                                     str = str+"world";
                                     END
                                  RETURN str;
                                END
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { 1, 5, 1, true, "hello" };
                String result = (String) loadClassAndRunMethod(bytecode, className, "a", params);
                assertEquals("helloworldworldworldworld", result);
        	    });
        }


        @DisplayName("jayavidhi_kumar_test3")
        @Test
        public void jayavidhi_kumar_test3(TestInfo testInfo) throws Exception {
                String input = """
                                VAL  a: INT = 2+4;
                                VAL  b: STRING = "hello";
                                VAR  z: STRING = "hellooo";
                                VAL  k: BOOLEAN = 2<4;
                                VAL  c: BOOLEAN = a<10;
                                VAL  d: BOOLEAN = b>z;
                                VAL  e:  BOOLEAN= c == d;
                                VAL  f:  BOOLEAN= e != d;
                                VAL  g:  BOOLEAN = !f != d;
                                VAL  h:  BOOLEAN  = !(f == d);
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Class<?> testClass = getClass(bytecode, className);
                assertEquals(true, getBoolean(testClass, "k"));
                assertEquals(true, getBoolean(testClass, "c"));
                assertEquals(false, getBoolean(testClass, "d"));
                assertEquals(false, getBoolean(testClass, "e"));
                assertEquals(false, getBoolean(testClass, "f"));
                assertEquals(true, getBoolean(testClass, "g"));
                assertEquals(false, getBoolean(testClass, "h"));
                assertEquals(6, getInt(testClass, "a"));
        	    });
        }



	@DisplayName("marks_test0")
	@Test
	public void marks_test0(TestInfo testInfo) throws Exception {
		String input = """
				VAR w = FALSE;
				VAR x = TRUE;
				VAR A = (TRUE && x);
				VAR B = A || w;
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		boolean A = getBoolean(testClass, "A");
		assertEquals(true, A);
		boolean B = getBoolean(testClass, "B");
		assertEquals(true, B);
	    });
	    }
	@DisplayName("marks_test1")
	@Test
	public void marks_test1(TestInfo testInfo) throws Exception {
		String input = """
				VAR w = 2;
				VAR x = 100;
				VAR y = 100;
				VAR z = w*(x+y);
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		int z = getInt(testClass, "z");
		assertEquals(400, z);
	    });
	    }
	@DisplayName("marks_test2")
	@Test
	public void marks_test2(TestInfo testInfo) throws Exception {
		String input = """
				FUN f():BOOLEAN
				DO
				   RETURN !(!TRUE);
				END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		boolean result = (boolean) runMethod(testClass, "f", null);
		assertEquals(true, result);
	    });
	    }
	@DisplayName("marks_test3")
	@Test
	public void marks_test3(TestInfo testInfo) throws Exception {
		String input = """
				VAR w = 2;
				VAR x = 100;
				FUN f():INT
				DO
				   RETURN w*x;
				END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		int result = (int) runMethod(testClass, "f", null);
		assertEquals(200, result);
	    });
	    }


	@DisplayName("nestedIfStatements_DanielJose_ParedesPardo_09831133")
	@Test
	public void nestedIfStatements_DanielJose_ParedesPardo_09831133(TestInfo testInfo) throws Exception {
		String input =  """
				VAR nested = TRUE;
				FUN nests() DO
					nested = FALSE;
					IF nested != TRUE DO
						nested = TRUE;
						IF nested == TRUE DO
							IF nested == TRUE DO
								nested = FALSE;
								IF nested != TRUE DO
									IF nested != TRUE DO
										nested = !nested;
										IF nested != TRUE DO
											nested = TRUE;
											IF nested == TRUE DO
												IF nested == TRUE DO
													nested = FALSE;
												END
											END
										END
									END
								END
							END
						END
					END
				END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(true, getBoolean(testClass, "nested"));
	    });
	    }
	
	@DisplayName("binaryGEQ_int_DanielJose_ParedesPardo_09831133")
	@Test
	public void binaryGEQ_int_DanielJose_ParedesPardo_09831133(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = 3;
				VAR y = (3 > x || 3 == x);
				VAR z = (4 > x || 4 == x);
				VAR w = (2 > x || 2 == x);
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(true, getBoolean(testClass, "y"));
		assertEquals(true, getBoolean(testClass, "z"));
		assertEquals(false, getBoolean(testClass, "w"));
	    });
	    }
	


        @DisplayName("sagarpiyushparikhtest0")
        @Test
        public void sagarpiyushparikhtest0(TestInfo testInfo) throws Exception {
                String input = """
                                FUN b(x:INT, y:INT):BOOLEAN
                                DO
                                   IF x - y == 0
                                   DO
                                   RETURN TRUE;
                                   END
                                   RETURN FALSE;
                                END
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { 9, 9 };
                boolean result = (boolean) loadClassAndRunMethod(bytecode, className, "b", params);
                assertEquals(true, result);
        	    });
        }

        @DisplayName("sagarpiyushparikhtest1")
        @Test
        public void sagarpiyushparikhtest1(TestInfo testInfo) throws Exception {
                String input = """
                                VAR a:INT =19;
                                FUN b():STRING
                                DO
                                   IF a/2 == 0
                                   DO
                                   RETURN "EVEN";
                                   END
                                   RETURN "ODD";
                                END
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { };
                String result = (String) loadClassAndRunMethod(bytecode, className, "b", params);
                assertEquals("ODD", result);
        	    });
        }


        @DisplayName("sagarpiyushparikhtest2")
        @Test
        public void sagarpiyushparikhtest2(TestInfo testInfo) throws Exception {
                String input = """
                                VAR fact = 1;
                                FUN factorial(i:INT):INT
                                DO
                                  WHILE i > 0 DO
                                    fact = i * fact;
                                    i = i -1;
                                     END
                                  RETURN fact;
                                END
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { 5 };
                int result = (int) loadClassAndRunMethod(bytecode, className, "factorial", params);
                assertEquals(120, result);
        	    });
        }

        @DisplayName("sagarpiyushparikhtest3")
        @Test
        public void sagarpiyushparikhtest3(TestInfo testInfo) throws Exception {
                String input = """
                                FUN grades(mark:INT):STRING
                                DO
                                  IF mark > 90 && mark< 100
                                   DO
                                   RETURN "A grade";
                                   END
                                  IF mark > 85 && mark< 90
                                   DO
                                   RETURN "A- grade";
                                   END
                                   IF mark > 80 && mark< 70
                                   DO
                                   RETURN "B grade";
                                   END
                                   RETURN "C grade";
                                END
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { 88};
                String result = (String) loadClassAndRunMethod(bytecode, className, "grades", params);
                assertEquals("A- grade", result);
        	    });
        }

        @DisplayName("erik_rosa_test0")
        @Test
        public void erik_rosa_test0(TestInfo testInfo) throws Exception {
                String input = """
                                VAR x = 0;
                                FUN g():STRING
                                DO
                                IF x==0 DO RETURN "COP"; END
                                RETURN "CNT";
                                END

                                FUN main(): STRING
                                DO
                                   RETURN g()+ " 5556-2021";
                                END
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = {};
                String result = (String) loadClassAndRunMethod(bytecode, className, "main", params);
                assertEquals("COP 5556-2021", result);
        	    });
        }
        @DisplayName("erik_rosa_test1")
        @Test
        public void erik_rosa_test1(TestInfo testInfo) throws Exception {
                String input = """
                                VAR y:INT;
                                FUN a(x:BOOLEAN)
                                DO
                                   y = 10 + 1000;
                                   IF x DO
                                   y = -1 + 1;
                                   END
                                END

                                FUN main()
                                DO
                                   a(FALSE);
                                END
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = {};
                Class<?> testClass = getClass(bytecode, className);
                runMethod(testClass, "main", params);
                assertEquals(1010, getInt(testClass, "y"));
        	    });
        }

        @DisplayName("erik_rosa_test2")
        @Test
        public void erik_rosa_test2(TestInfo testInfo) throws Exception {
                String input = """
                                FUN g(x:BOOLEAN, y: INT):STRING
                                DO
                                   IF (x && y > 15) DO RETURN "Binary Expression is true"; END
                                   RETURN "Binary Expression is false";
                                END
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
                byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { true, 16 };
                String result = (String) loadClassAndRunMethod(bytecode, className, "g", params);
                assertEquals("Binary Expression is true", result);
        });
        }

        @DisplayName("erik_rosa_test3")
        @Test
        public void erik_rosa_test3(TestInfo testInfo) throws Exception {
                String input = """
                                FUN a(hw:INT, midterm:INT):STRING
                                DO
                                  IF hw+midterm > 300
                                  DO
                                        RETURN "YOU GOT AN A!";
                                  END
                                  RETURN "YOU FAILED,BETTER LUCK NEXT TIME!";
                                END
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { 200, 150};
                String result = (String) loadClassAndRunMethod(bytecode, className, "a", params);
                assertEquals("YOU GOT AN A!", result);
        	    });
        }
                @DisplayName("sumeet_saini_test1")
                @Test
                public void sumeet_saini_test1(TestInfo testInfo) throws Exception {
                        String input = """
                                        VAR x = 10;
                                        FUN g():STRING
                                        DO
                                        IF x/2==5
                                                DO
                                                        IF x/5==2
                                                        DO
                                                                RETURN "ten";
                                                        END
                                                END
                                                RETURN "not ten";
                                        END
                                        """;
                		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
                			byte[] bytecode = compile(input, className, packageName);
                        show(CodeGenUtils.bytecodeToString(bytecode));
                        String result = (String) loadClassAndRunMethod(bytecode, className, "g", null);
                        assertEquals("ten", result);
                	    });
                }

                @DisplayName("sumeet_saini_test2")
                @Test
                public void sumeet_saini_test2(TestInfo testInfo) throws Exception {
                        String input = """
                                        FUN a(i:INT, end:INT, by:INT, in:INT):STRING
                                        DO
                                                end = end + in;
                                                WHILE i < end
                                                DO
                                                i = i + by;
                                        END
                                                IF i==end
                                                DO
                                                        RETURN "EVEN";
                                                END
                                                RETURN "ODD";
                                        END
                                        """;
                		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
                			byte[] bytecode = compile(input, className, packageName);
                        show(CodeGenUtils.bytecodeToString(bytecode));
                        Object[] params = { 0, 5, 2 ,8 };
                        String result = (String) loadClassAndRunMethod(bytecode, className, "a", params);
                        assertEquals("ODD", result);
                	    });
                }

                @DisplayName("sumeet_saini_test03")
                @Test
                public void sumeet_saini_test03(TestInfo testInfo) throws Exception {
                        String input = """
                                        FUN a(name:STRING,message0:STRING,message1:STRING,i:INT):STRING
                                        DO
                                                IF i>0
                                                DO
                                                        RETURN message0 + name;
                                                END
                                                IF i<0
                                                DO
                                                        RETURN  message1 + name;
                                                END
                                                RETURN "no message for value 0";
                                        END
                                        """;
                		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
                			byte[] bytecode = compile(input, className, packageName);
                        show(CodeGenUtils.bytecodeToString(bytecode));
                        Object[] params = { "sumeet", "Good Morning, ","Good Night, ", 2 };
                        String result = (String) loadClassAndRunMethod(bytecode, className, "a", params);
                        assertEquals("Good Morning, sumeet", result);
                	    });
                }

                @DisplayName("sumeet_saini_test4")
                @Test
                public void sumeet_saini_test4(TestInfo testInfo) throws Exception {
                        String input = """
                                        VAR end = 64;
                                        VAR i = 1;
                                        FUN a(): STRING
                                        DO
                                                WHILE i < end
                                                DO
                                                IF i*i*i == end
                                                DO
                                                        RETURN "CUBE";
                                                END
                                                i=i+1;
                                        END
                                                RETURN "NOT CUBE";
                                        END
                                        """;
                		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
                			byte[] bytecode = compile(input, className, packageName);
                        show(CodeGenUtils.bytecodeToString(bytecode));
                        Object[] params = { };
                        String result = (String) loadClassAndRunMethod(bytecode, className, "a", params);
                        assertEquals("CUBE", result);
                	    });
                }

                @DisplayName("sumeet_saini_test0")
                @Test
                public void sumeet_saini_test0(TestInfo testInfo) throws Exception {
                        String input = """
                                        FUN g(x:INT): INT
                                        DO
                                                RETURN x+1;
                                        END

                                        FUN main(a:INT,b:INT,c:INT):INT
                                        DO
                                                WHILE c<b
                                                DO
                                                        a = a * c;
                                                        c = g(c);
                                                END
                                           RETURN a;
                                        END
                                        """;
                		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
                			byte[] bytecode = compile(input, className, packageName);
                        show(CodeGenUtils.bytecodeToString(bytecode));
                        Object[] params = { 1,5,2};
                        int result = (int) loadClassAndRunMethod(bytecode, className, "main", params);
                        assertEquals(24, result);
                	    });
                }

        @DisplayName("ganesan_santhanam_test1")
        @Test
        public void ganesan_santhanam_test1(TestInfo testInfo) throws Exception {
                String input = """
                                        VAR i = 0;
                                        VAR end = 10;
                                        VAR by = 2;
                                        FUN a():STRING
                                        DO
                                                WHILE i < end
                                                DO
                                                i = i + by;
                                        END
                                                IF i==end
                                                DO
                                                        RETURN "EVEN";
                                                END
                                                RETURN "ODD";
                                        END
                                        """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { };
                String result = (String) loadClassAndRunMethod(bytecode, className, "a", params);
                assertEquals("EVEN", result);
        	    });
        }

        @DisplayName("ganesan_santhanam_test2")
        @Test
        public void ganesan_santhanam_test2(TestInfo testInfo) throws Exception {
                String input = """
                                        FUN a(name:STRING,i:INT):STRING
                                        DO
                                                IF i==0
                                                DO
                                                        RETURN "HELLO " + name;
                                                END
                                                RETURN  "BYE" + name;
                                        END
                                        """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { "Ganesh", 0 };
                String result = (String) loadClassAndRunMethod(bytecode, className, "a", params);
                assertEquals("HELLO Ganesh", result);
        	    });
        }

        @DisplayName("ganesan_santhanam_test3")
        @Test
        public void ganesan_santhanam_test3(TestInfo testInfo) throws Exception {
                String input = """
                                        VAR neg = -1;
                                        FUN a(b:INT): INT
                                        DO
                                                IF b<0
                                                DO
                                                        RETURN neg*b;
                                                END
                                                RETURN b;
                                        END
                                        """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { -3 };
                int result = (int) loadClassAndRunMethod(bytecode, className, "a", params);
                assertEquals(3, result);
        	    });
        }

        @DisplayName("ganesan_santhanam_test4")
        @Test
        public void ganesan_santhanam_test4(TestInfo testInfo) throws Exception {
                String input = """
                                        VAR end = 25;
                                        VAR i = 1;
                                        FUN a(): STRING
                                        DO
                                                WHILE i < end
                                                DO
                                                IF i*i == end
                                                DO
                                                        RETURN "SQUARE";
                                                END
                                                i=i+1;
                                        END
                                                RETURN "NOT SQUARE";
                                        END
                                        """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { };
                String result = (String) loadClassAndRunMethod(bytecode, className, "a", params);
                assertEquals("SQUARE", result);
        	    });
        }

        @DisplayName("ganesan_santhanam_test0")
        @Test
        public void ganesan_santhanam_test5(TestInfo testInfo) throws Exception {
                String input = """
                                        FUN a(a:INT,b:INT,c:INT): INT
                                        DO
                                                WHILE c<b
                                                DO
                                                        a = a * c;
                                                        c = c + 1;
                                                END
                                           RETURN a;
                                        END
                                        """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { 1,5,2 };
                int result = (int) loadClassAndRunMethod(bytecode, className, "a", params);
                assertEquals(24, result);
        	    });
        }
       @DisplayName("adam_tamargo_test0")
        @Test
        public void adam_tamargo_test0(TestInfo testInfo) throws Exception {
                String input = """
                                VAR x = !((3+2)*2==10 || !TRUE);
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        		byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Class<?> testClass = getClass(bytecode, className);
                assertEquals(false, getBoolean(testClass, "x"));
        	    });
       }

        @DisplayName("adam_tamargo_test1")
        @Test
        public void adam_tamargo_test1(TestInfo testInfo) throws Exception {
                String input = """
                                VAR c:INT = 1;
                                FUN a(b:INT): INT
                                DO
                                  IF b < 0 DO
                                        RETURN 0;
                                  END
                                  WHILE b < 5 DO
                                        c = c + c;
                                        b = b+1;
                                  END
                                  RETURN c+b;
                                END
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { 1 };
                int result = (int) loadClassAndRunMethod(bytecode, className, "a", params);
                assertEquals(21, result);
                params[0] = -1;
                result = (int) loadClassAndRunMethod(bytecode, className, "a", params);
                assertEquals(0, result);
        	    });
        }

        @DisplayName("adam_tamargo_test2")
        @Test
        public void adam_tamargo_test2(TestInfo testInfo) throws Exception {
                String input = """
                                VAR z:INT = 0;
                                FUN a(x:INT, y:BOOLEAN)
                                DO
                                   IF y DO z=z+x+2; END
                                   IF !y DO z=z+x*2; END
                                END

                                FUN main()
                                DO
                                   a(1, TRUE);
                                   a(2, FALSE);
                                END
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = {};
                Class<?> testClass = getClass(bytecode, className);
                runMethod(testClass, "main", params);
                assertEquals(7, getInt(testClass, "z"));
        	    });
        }

        @DisplayName("adam_tamargo_test3")
        @Test
        public void adam_tamargo_test3(TestInfo testInfo) throws Exception {
                String input = """
                                FUN f(x:STRING, y:STRING):STRING
                                DO
                                   IF x == y DO RETURN x + " & " + y + " are the same."; END
                                   RETURN x + " & " + y + " are not the same.";
                                END
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { "this", "this" };
                String result = (String) loadClassAndRunMethod(bytecode, className, "f", params);
                assertEquals("this & this are the same.", result);
                params[1] = "that";
                result = (String) loadClassAndRunMethod(bytecode, className, "f", params);
                assertEquals("this & that are not the same.", result);
        	    });
        }

        @DisplayName("jackson_wu_test0")
        @Test
        public void jackson_wu_test0(TestInfo testInfo) throws Exception {
                String input = """
                                VAR x:INT;
                                FUN f()
                                DO
                                x = 15+15;
                                END
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Class<?> testClass = getClass(bytecode, className);
                runMethod(testClass, "f", null);
                assertEquals(30, getInt(testClass, "x"));
        	    });
        }



        @DisplayName("chenglin_zhou_test0")
        @Test
        public void testInt_Mutiple_Operator(TestInfo testInfo) throws Exception {
                String input = """
                                VAR a = 5;
                                VAR b = 10;
                                VAR c = 15;
                                VAR d = 0;
                                VAR x = a+b+c*d;
                                """;

        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Class<?> testClass = getClass(bytecode, className);
                assertEquals(15, getInt(testClass, "x"));
        	    });
        }
        @DisplayName("chenglin_zhou_test1")
        @Test
        public void testFun_withOP(TestInfo testInfo) throws Exception {
                String input = """
                                VAR sum = 0;
                                FUN a(i:INT, end:INT, by:INT):INT
                                DO
                                  WHILE i < end DO
                                     sum = sum + i;
                                     i = i + by;
                                     END
                                  RETURN sum;
                                END

                                FUN main(j:INT, endd:INT, byy:INT):INT
                                DO
                                        RETURN a(j+1,endd+1,byy+1);
                                END
                                """;
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { 0, 4, 0 };
                int result = (int) loadClassAndRunMethod(bytecode, className, "main", params);
                assertEquals(10, result);
        	    });
        }

        @DisplayName("chenglin_zhou_test2")
        @Test
        public void testLet_While_Let_If(TestInfo testInfo) throws Exception {
                String input = """
                                VAR sum = 0;
                                VAR ret:BOOLEAN = TRUE;
                                FUN a(i:INT, end:INT, by:INT):BOOLEAN
                                DO
                                  WHILE i < end DO
                                     sum = sum + i;
                                     i = i + by;
                                     LET x1 = sum
                                                DO
                                                        sum = x1+sum;
                                                                IF sum==52
                                                                        DO
                                                                                ret = TRUE;
                                                                        END
                                                END
                                     END
                                        RETURN ret;
                                END

                                """;
                // (((1x2+2)x2+3)x2+4)x2 = 52
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { 1, 5, 1 };
                boolean result = (boolean) loadClassAndRunMethod(bytecode, className, "a", params);
                assertEquals(true, result);
        	    });
        }
        @DisplayName("chenglin_zhou_test3")
        @Test
        public void testVal_Var_AND_OR(TestInfo testInfo) throws Exception {
                String input = """
                                VAL x:BOOLEAN = TRUE;
                                VAR y:BOOLEAN = TRUE;
                                VAR str = "";
                                FUN a(start:INT, end:INT):STRING
                                DO
                                        WHILE start < end
                                        DO
                                                IF x&&y
                                                DO
                                                        str = str + "flip";
                                                        y = FALSE;
                                                END
                                                start = start+1;
                                        END
                                        IF x||y
                                        DO
                                                RETURN str;
                                        END
                                        RETURN "";
                                END


                                """;
                // (((1x2+2)x2+3)x2+4)x2 = 52
        		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
        			byte[] bytecode = compile(input, className, packageName);
                show(CodeGenUtils.bytecodeToString(bytecode));
                Object[] params = { 1, 2 };
                String result = (String) loadClassAndRunMethod(bytecode, className, "a", params);
                assertEquals("flip", result);
        	    });
        }

    @DisplayName("testEvaluationInGlobalInitialization")
    @Test
    public void william_anderson_test0(TestInfo testInfo) throws Exception {
        String input = """
            VAR x = "x";
            FUN y(): STRING DO RETURN "y"; END
            VAL result = x + y();
            """;
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
        show(CodeGenUtils.bytecodeToString(bytecode));
        Class<?> testClass = getClass(bytecode, className);
        String result = getString(testClass, "result");
        assertEquals("xy", result);
	    });
	    }

    @DisplayName("testFalseWhileLoop")
    @Test
    public void william_anderson_test1(TestInfo testInfo) throws Exception {
        String input = """
            VAR result = "initial";
            FUN func(): STRING DO
                WHILE (FALSE) DO
                    result = "loop";
                END
                RETURN result;
            END
            """;
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);;
        show(CodeGenUtils.bytecodeToString(bytecode));
        String result = (String) loadClassAndRunMethod(bytecode, className, "func", null);
        assertEquals("initial", result);
    });
    }


    @DisplayName("testFunctionCallEvaluationOrder")
    @Test
    public void william_anderson_test3(TestInfo testInfo) throws Exception {
        String input = """
            VAR result = "";
            FUN log(message: STRING): STRING DO
                result = result + message;
                RETURN message;
            END
            FUN multiple(a: STRING, b: STRING, c: STRING) DO END
            FUN func(): STRING DO
                multiple(log("1") + log("2"), log("3"), log(log("4")));
                RETURN result;
            END
            """;
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			byte[] bytecode = compile(input, className, packageName);
        show(CodeGenUtils.bytecodeToString(bytecode));
        String result = (String) loadClassAndRunMethod(bytecode, className, "func", null);
        assertEquals("12344", result);
    });
}
    }
