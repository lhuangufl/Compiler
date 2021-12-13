package edu.ufl.cise.plpfa21.assignment5;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class ComprehensiveTestsHW6ForGrading extends CodeGenTestsBaseHW6ForGrading {


	@DisplayName("Int lit out of bounds")
	@Test
	public void intlitoutofbounds(TestInfo testInfo) throws Exception {
		String input = """
				a:INT = 88888888888888888888888888888888888888888888888888888888888;
				""";
				assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test50a")
	@Test
	public void test50a(TestInfo testInfo) throws Exception {
		String input = """
				VAR a=0
				VAR b=1;

				""";
				assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test51a")
	@Test
	public void test51a(TestInfo testInfo) throws Exception {
		String input = """
				FUN f(x:INT, y:BOOLEAN, z:LIST[INT]) DO
				END
				END
				""";
				assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test52a")
	@Test
	public void test52a(TestInfo testInfo) throws Exception {
		String input = """
				VAL a; INT = 0;
				""";
				assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test53")
	@Test
	public void test53(TestInfo testInfo) throws Exception {
		String input = """
				VAL a: STRIN = "hello";
				""";
				assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test54")
	@Test
	public void test54(TestInfo testInfo) throws Exception {
		String input = """
				VAL b: BOOLEAn = TRUE;
				""";
				assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test55")
	@Test
	public void test55(TestInfo testInfo) throws Exception {
		String input = """
				VAR b: LIST[;
				""";
				assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test56")
	@Test
	public void test56(TestInfo testInfo) throws Exception {
		String input = """
				VAL a= 0
				""";
				assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test57")
	@Test
	public void test57(TestInfo testInfo) throws Exception {
		String input = """
				VAL a == "hello";
				""";
				assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test58")
	@Test
	public void test58(TestInfo testInfo) throws Exception {
		String input = """
				VAl b =TRUE;
				""";
				assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test59")
	@Test
	public void test59(TestInfo testInfo) throws Exception {
		String input = """
				VAR a INT = 0;
				""";
				assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test60")
	@Test
	public void test60(TestInfo testInfo) throws Exception {
		String input = """
				VAR a: STRING == "hello";
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test61")
	@Test
	public void test61(TestInfo testInfo) throws Exception {
		String input = """
				VAR b: BOOLEA = TRUE;
				""";
		assertThrows(Exception.class, () -> {
			compile(input, className, packageName);
		});
	}

	@DisplayName("test62")
	@Test
	public void test62(TestInfo testInfo) throws Exception {
		String input = """
				VAR a, INT;
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test63")
	@Test
	public void test63(TestInfo testInfo) throws Exception {
		String input = """
				VAR a: STRING,
				""";
		assertThrows(Exception.class, () -> {
			compile(input, className, packageName);
		});
	}

	@DisplayName("test64")
	@Test
	public void test64(TestInfo testInfo) throws Exception {
		String input = """
				VAR c = (23));
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test65")
	@Test
	public void test65(TestInfo testInfo) throws Exception {
		String input = """
				VAR c = f(,);
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test66")
	@Test
	public void test66(TestInfo testInfo) throws Exception {
		String input = """
				VAL c = a+;
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test67")
	@Test
	public void test67(TestInfo testInfo) throws Exception {
		String input = """
				VAL d = a-*b
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test68")
	@Test
	public void test68(TestInfo testInfo) throws Exception {
		String input = """
				VAL d = a/*b;
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test69")
	@Test
	public void test69(TestInfo testInfo) throws Exception {
		String input = """
				VAL d = ((a+b)/c+f();
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test70")
	@Test
	public void test70(TestInfo testInfo) throws Exception {
		String input = """
				FUN b() BEGIN END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test71")
	@Test
	public void test71(TestInfo testInfo) throws Exception {
		String input = """
				FUN a(b:) DO END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test72")
	@Test
	public void test72(TestInfo testInfo) throws Exception {
		String input = """
				FUN a(b C:STRING) DO END
				""";
		assertThrows(Exception.class, () -> {
			compile(input, className, packageName);
		});
	}

	@DisplayName("test73")
	@Test
	public void test73(TestInfo testInfo) throws Exception {
		String input = """
				FUN f()
				DO
				   RETURN;
				END

				""";
		assertThrows(Exception.class, () -> {
			compile(input, className, packageName);
		});
	}

	@DisplayName("test74")
	@Test
	public void test74(TestInfo testInfo) throws Exception {
		String input = """
				FUN g():INT DO RETURN 1; END,
				FUN f()
				DO
				   RETURN g();
				   END

				""";
		assertThrows(Exception.class, () -> {
			compile(input, className, packageName);
		});
	}

	@DisplayName("test75")
	@Test
	public void test75(TestInfo testInfo) throws Exception {
		String input = """
				FUN f()
				DO
					RETURN NIL;;
				END

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test76")
	@Test
	public void test76(TestInfo testInfo) throws Exception {
		String input = """
				VAL pi = 3
				FUN main():INT DO
				   RETURN pi + pi;
				   END

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test77")
	@Test
	public void test77(TestInfo testInfo) throws Exception {
		String input = """
				FUN func() DO
				SWITCH x
				CASE 0 a y=0;
				CASE 1 : y=1;
				CASE 2 : y=2;
				DEFAULT y=3;
				END  /*SWITCH*/

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test78")
	@Test
	public void test78(TestInfo testInfo) throws Exception {
		String input = """
				FUN func() DO
				SWITCH x
				CASE 0 a y=0;
				CASE 1 : y=1;
				CASE 2 : y=2;
				DEFAULT y=3;
				END  /*SWITCH*/
				END  /*FUN*/

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test79")
	@Test
	public void test79(TestInfo testInfo) throws Exception {
		String input = """
				FUN func() DO
				IF x=0 DO x = 1; END
				END  /*FUN*/

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test80")
	@Test
	public void test80(TestInfo testInfo) throws Exception {
		String input = """
				FUN func() DO
				WHILE x>0 DO x=x-1 END
				END

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {assertThrows(Exception.class, () -> {compile(input, className, packageName);});});
	}

	@DisplayName("test81")
	@Test
	public void test81(TestInfo testInfo) throws Exception {
		String input = """
				FUN b(a,c/d) DO END
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test82")
	@Test
	public void test82(TestInfo testInfo) throws Exception {
		String input = """
				VAL d = d = a*-b;
				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test31")
	@Test
	public void test31(TestInfo testInfo) throws Exception {
		String input = """
				VAL a: INT = "hello";

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test32")
	@Test
	public void test32(TestInfo testInfo) throws Exception {
		String input = """
				VAL a: STRING = TRUE;

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test33")
	@Test
	public void test33(TestInfo testInfo) throws Exception {
		String input = """
				VAL a: BOOLEAN = 0;

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test34")
	@Test
	public void test34(TestInfo testInfo) throws Exception {
		String input = """
				VAR a: BOOLEAN = "hello";

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test35")
	@Test
	public void test35(TestInfo testInfo) throws Exception {
		String input = """
				VAR a: INT = "hello" + "goodbye";
					VAR b: INT = a-1;

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test36")
	@Test
	public void test36(TestInfo testInfo) throws Exception {
		String input = """
				VAL a: INT = a+1;

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test37")
	@Test
	public void test37(TestInfo testInfo) throws Exception {
		String input = """
				VAR a: LIST[LIST[INT]];
				VAR b: LIST[LIST[STRING]] = a[2];


				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test38")
	@Test
	public void test38(TestInfo testInfo) throws Exception {
		String input = """
					VAL  a: STRING = "hello";
				VAL  b: INT = a-1;

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test39")
	@Test
	public void test39(TestInfo testInfo) throws Exception {
		String input = """
				VAL  a: STRING = "hello";
				VAL  b: INT = 3;
				VAR  c: BOOLEAN = a<b;
				VAR  e:  BOOLEAN= c == d;
				VAR  f:  BOOLEAN= e != d;
				VAR  g:  BOOLEAN = !f != d;
				VAR  h:  BOOLEAN  = !(f == d);
				VAR  i:  BOOLEAN = g && h;
				VAR  j:  BOOLEAN = g || h;

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test40")
	@Test
	public void test40(TestInfo testInfo) throws Exception {
		String input = """
				VAL  a: INT = 2;
				VAL  b: INT = 3;
				VAR  c: BOOLEAN = a<b;
				VAR  e:  BOOLEAN= c == d; /* d not declared */

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test41")
	@Test
	public void test41(TestInfo testInfo) throws Exception {
		String input = """
				VAL  a: INT = 2;
				VAL  b: INT = 3;
				VAR  c: BOOLEAN = a<b;
				VAR  d: BOOLEAN = TRUE;
				VAR  e:  BOOLEAN= c == b;

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test42")
	@Test
	public void test42(TestInfo testInfo) throws Exception {
		String input = """
				VAL  a: INT = 2;
				VAL  b: INT = 3;
				VAR  c: BOOLEAN = a<b;
				VAR  d: BOOLEAN = TRUE;
				VAR  e:  BOOLEAN= c == d;
				VAR  f:  BOOLEAN= e != d;
				VAR  g:  BOOLEAN = !b != d;  /*b wrong type*/
				VAR  h:  BOOLEAN  = !(f == d);
				VAR  i:  BOOLEAN = g && h;
				VAR  j:  BOOLEAN = g || h;

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test43")
	@Test
	public void test43(TestInfo testInfo) throws Exception {
		String input = """
				VAL a: INT = 4;
				VAL b: INT = -a;
				VAL c: INT = -(-a);
				VAL d: INT = -5;
				FUN f(x:INT, y:BOOLEAN, s:STRING):STRING
				                  DO RETURN "hello"; END
				VAR e:STRING = f(a,TRUE,d);

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test44")
	@Test
	public void test44(TestInfo testInfo) throws Exception {
		String input = """
				VAL a:INT = 42;
				FUN f(x:INT, y:BOOLEAN):INT
				DO
				   IF y DO a = x; END
				   IF !y DO a = 0; END
				END

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test45")
	@Test
	public void test45(TestInfo testInfo) throws Exception {
		String input = """
				VAR a: INT;
				FUN f(x:INT) DO a = x; END
				FUN main() DO
				f(3) = 4;
				END

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test46")
	@Test
	public void test46(TestInfo testInfo) throws Exception {
		String input = """
				VAR a: INT;
				FUN f(x) DO a = x; END
				FUN main() DO
				f(3);
				END

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test47")
	@Test
	public void test47(TestInfo testInfo) throws Exception {
		String input = """
				VAR a: LIST[INT];
				VAR N = 5;
				FUN f(b: LIST[])
				DO
						LET i:INT=0
						DO  LET c:LIST[INT]
						    DO
						    WHILE i < N
						       DO
						        c[i] = b[i] + i;
						       END

						    END
						END
				      END
				   VAR j=0;
				   FUN init() DO
				      WHILE j < N
				      DO
				          a[j] = j;
				      END
				   END
				   FUN main() DO
				       init();
				       f(a);
				   END

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test48")
	@Test
	public void test48(TestInfo testInfo) throws Exception {
		String input = """
				VAR a: LIST[INT] = NIL;
				VAR N = 5;
				FUN f(b: LIST[])
				DO
						LET i:INT=0
						DO  LET c:LIST[INT]
						    DO
						    WHILE i < N
						       DO
						        c[i] = i;
						        a[i] = b[i] + c[i];
						       END

						    END
						END
				      END
				   VAR j=0;
				   FUN init() DO
				      WHILE j < N
				      DO
				          a[j] = j;
				      END
				   END
				   FUN main() DO
				       init();
				       f(a);
				   END

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test49")
	@Test
	public void test49(TestInfo testInfo) throws Exception {
		String input = """
				VAL a: LIST[INT] = NIL;
				VAR N = 5;
				FUN f(b: LIST[])
				DO
						LET i:INT=0
						DO  LET c:LIST[INT]
						    DO
						    WHILE i < N
						       DO
						        c[i] = i;
						        a[i] = b[i] + c[i];
						       END

						    END
						END
				      END
				   VAR j=0;
				   FUN init() DO
				      WHILE j < N
				      DO
				          a[j] = j;
				      END
				   END
				   FUN main() DO
				       init();
				       f(a);
				   END

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test50")
	@Test
	public void test50(TestInfo testInfo) throws Exception {
		String input = """
				VAR a: LIST[INT];
				VAR b: LIST[] = a[0];

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test51")
	@Test
	public void test51(TestInfo testInfo) throws Exception {
		String input = """

				VAR a: LIST[INT];
				VAR b: LIST[] = a[0];
				VAR c: LIST[] = b[0];

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertThrows(Exception.class, () -> {
				compile(input, className, packageName);
			});
		});
	}

	@DisplayName("test52")
	@Test
	public void test52(TestInfo testInfo) throws Exception {
		String input = """
				VAL a = 1;
				VAR b = 2;
				VAR x: INT;
				FUN f(x:INT)
				DO
				SWITCH x
				CASE 0 :
				CASE a :
				CASE b :
				DEFAULT
				END

				END

				""";
		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
				assertThrows(Exception.class, () -> {
					compile(input, className, packageName);
				});
			});
		});
	}
	
}
