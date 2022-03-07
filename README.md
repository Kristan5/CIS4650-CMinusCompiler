
## Development Steps:
### 1. cm.flex (DONE)
### 2. cm.cup (ERROR HANDLING TODO)
### 3. ShowTreeVisitor.java  (NOT DONE)
### 4. /absyn classes (MOSTLY DONE)
### 5. absyn/AbsynVisitor.java (DONE)
 
 ShowTreeVisitor: 
 - [ ]  ArrayDec
 - [ ]  AssignExp
 - [ ]  CallExp
 - [ ]  Dec
 - [x]  DecList
 - [ ]  Exp
 - [x]  ExpList
 - [ ]  FunctionDec
 - [x]  IfExp
 - [x]  IndexVar
 - [x]  IntExp
 - [ ]  NilExp
 - [x]  OpExp
 - [ ]  ReturnExp
 - [ ]  SimpleDec
 - [ ]  SimpleVar
 - [ ]  Type
 - [ ]  Var
 - [ ]  VarDec
 - [ ]  VarDecList
 - [ ]  VarExp
 - [ ]  WhileExp

## TODO: 
- Add error RESULT after each '''parser.report_error()'''
  - And other error handling
- change all '''pos''' to row and col
- CallExp Class

# Other: 

## A JFlex+Cup implementation for the Tiny programming language.

  Please note that you need to modify the paths for CLASSPATH, JFLEX, and CUP in 
the Makefile so that the related commands can be found on your particular 
machine.  For example, the given settings in Makefile are for the Linux 
server and the commented settings are for my MacBook.

  To build the parser, type "make" in the current directory, which will 
generate an executable program called "Main".

  To test source code like "fac.tiny", type 

    "java -cp /usr/share/java/cup.jar:. Main fac.tiny" 

and the syntax tree will be displayed on the screen.

  To rebuild the parser, type "make clean" and type "make" again.

  Also note that all the abstract syntax tree structures are defined under
the directory "absyn" and the methods for showing a syntax tree is implemented
by the visitor pattern in "ShowTreeVisitor.java".  Since some java files are 
generated automatically, they may contain variables that are not used, which 
are safe to ignore in the compilation process.

  Also included in this package are four versions of the tiny.cup file: tiny.cup.bare
defines all token types but has basically no grammar rules, which can be used to test 
your scanner implementation; tiny.cup.rules contains all the grammar rules but doesn't
generate any output, which can be used to test if the given grammar can be run properly;
tiny.cup.layered uses the same grammar and shows the syntax trees through the visitor
pattern; and tiny.cup.ordered simplifies the grammar with precedence directives in CUP.
You are encouraged to follow these steps to build your parser incrementaly for Checkpoint
One implementation.
