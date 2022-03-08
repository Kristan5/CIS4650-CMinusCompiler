JAVA=java
JAVAC=javac
JFLEX=jflex
CLASSPATH=-cp /usr/share/java/cup.jar:.
CUP=cup
#JFLEX=~/Projects/jflex/bin/jflex
#CLASSPATH=-cp ~/Projects/java-cup-11b.jar:.
#CUP=$(JAVA) $(CLASSPATH) java_cup.Main

all: Main.class

test: 
	make clean 
	make
	# java -cp /usr/share/java/cup.jar:. Main testFiles/fac.cm
	# java -cp /usr/share/java/cup.jar:. Main testFiles/fac_simple.cm
	java -cp /usr/share/java/cup.jar:. Main testFiles/gcd.cm

remake: 
	make clean
	make

Main.class: absyn/*.java parser.java sym.java Lexer.java ShowTreeVisitor.java Scanner.java Main.java

%.class: %.java
	$(JAVAC) $(CLASSPATH) $^

Lexer.java: cm.flex
	$(JFLEX) cm.flex

parser.java: cm.cup.rules
	#$(CUP) -dump -expect 3 cm.cup.rules
	$(CUP) -expect 3 cm.cup.rules

clean:
	rm -f parser.java Lexer.java sym.java *.class absyn/*.class *~
