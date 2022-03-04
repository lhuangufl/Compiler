# COP5556FA2021
Course Project Fall 2021

## Lexical Structure
Token ::= Symbol | Keyword | Identifier | IntLiteral | StringLiteral
Symbol::= =|,|;|:|(|)|[|]|&&||||<|>|==|!= |!|+|-|*|/ Keyword::= VAR | VAL | FUN | DO | END | LET | SWITCH | CASE | DEFAULT
|IF |WHILE |RETURN |NIL |TRUE |FALSE| INT| STRING
| BOOLEAN | LIST
Identifier::= (a..z|A..Z|_|$) (a..z|A..Z|_|$|0..9)*
IntLiteral::= ( 0 .. 9 ) +
StringLiteral::= "(~(\|“)|EscapeSeq)*“ | ‘(~(\|‘)|EscapeSeq)*‘ EscapeSeq::= \b |\t |\n |\r| \f |\” |\’ |\\
Comment /*(~* | *~/)*/
WhiteSpace ::= space | tab | NL | CR (denoted in Java as ‘ ‘, ‘\t’, ‘\n’ and ‘\r’)
White space and comments separate tokens, but otherwise are ignored.
Notation: X+ means one or more instances of X. ~a means any character other than a.

## Grammer
Program ::= Declaration* Declaration::=
Function | VAR NameDef (= Expression)? ; | VAL NameDef = Expression ; Function::= FUN Identifier ( ( NameDef (, NameDef)* )? ) (: Type)? DO Block END Block::= Statement*
NameDef::= Identifier (: Type)?
Statement::=
LET NameDef (= Expression)? ; |
SWITCH Expression ( CASE Expression : Block )* DEFAULT Block END | IF Expression DO Block END |
WHILE Expression DO Block END |
RETURN Expression ; |
Expression (= Expression)? ;
Expression::= LogicalExpression LogicalExpression returns Expression::=
ComparisonExpression ( ( && | || ) ComparisonExpression)*
ComparisonExpression ::= AdditiveExpression ( ( < | > | == | != ) AdditiveExpression)*
AdditiveExpression ::= MultiplicativeExpression ( ( + |- ) MultiplicativeExpression )*
MultiplicativeExpression returns Expression ::= UnaryExpression ( ( * | / ) UnaryExpression)*
UnaryExpression returns Expression::= (! | - )? PrimaryExpression
PrimaryExpression ::=
NIL | TRUE | FALSE | IntLiteral | StringLiteral | ( Expression ) |
Identifier ( (Expression ( , Expression)* )? ) |
Identifier | Identifier [ Expression ] Type:
INT | STRING | BOOLEAN | LIST [ Type ]

## Implementation of Abstract Syntax Tree
Program::=
{Program} declarations += Declaration*
Declaration::=
{Function} 'FUN' name= Identifier
'(' ( arguments += NameDef (',' arguments += NameDef)* )? ')' (':' resultType = Type)?
'DO' block = Block 'END' |
{MutableGlobal} 'VAR' varDef = NameDef ('=' expression=Expression)? ';' | {ImmutableGlobal} 'VAL' valDef = NameDef '=' expression = Expression ';'
Block::=
{Block} statements += Statement*
 NameDef::=
{NameDef} name = Identifier (':' type = Type)?
Statement::=
{LetStatement} 'LET' localDef = NameDef ('=' expression= Expression)? 'BEGIN' block = Block
'END' |
{SwitchStatement} 'SWITCH' switchExpr = Expression ( 'CASE' branchExprs += Expression ':'
blocks += Block )* 'DEFAULT' defaultBlock = Block 'END'|
{IfStatement} 'IF' guardExpr = Expression 'DO' ifBlock = Block 'END' | {WhileStatement} 'WHILE' guardExpression = Expression 'DO' block = Block 'END' | {ReturnStatement} 'RETURN' returnExpr = Expression ';' |
{AssignmentStatement} leftExpr=Expression ( '=' rightExpr = Expression)? ';'
;
Expression::= LogicalExpression
LogicalExpression returns{Expression}::=
{BinaryExpression} ComparisonExpression ( op = ('&&' | '||') ComparisonExpression)*
ComparisonExpression returns{Expression}::=
{BinaryExpression} AdditiveExpression ( op = ('<' | '>' | '==' | '!=') AdditiveExpression)*
AdditiveExpression returns{Expression}::=
{BinaryExpression} MultiplicativeExpression ( op = ('+'|'-') MultiplicativeExpression )*
MultiplicativeExpression returns{Expression}::=
{BinaryExpression} UnaryExpression ( op=('*'|'/') UnaryExpression )*
UnaryExpression returns {Expression}::=
{UnaryExpression} op = ('!'|'-')? expression = PrimaryExpression
PrimaryExpression returns {Expression}::= {NilConstantExpression} 'NIL' |

{BooleanLiteralExpression} value = ('TRUE' | 'FALSE') | {IntLiteralExpression} value = INT | {StringLiteralExpression} value = STRING |
'(' Expression ')'|
{FunctionCallExpression} name = Identifier '(' (arguments += Expression (',' argumnets += Expression)* )? ')' |
{IdentExpression} name = Identifier |
{ListSelectorExpression} name = Identifier '[' index = Expression ']'
Type returns {Type}::=
{PrimitiveType} type = ('INT' | 'STRING' | 'BOOLEAN') | {ListType}'LIST' '[' (type = Type)? ']'
Identifier::=
{Identifier} name = ID
