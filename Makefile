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
	make remake
	java -cp /usr/share/java/cup.jar:. Main testFiles/1.cm

remake: 
	make clean
	make

Main.class: absyn/*.java parser.java sym.java Lexer.java ShowTreeVisitor.java Scanner.java Main.java
# Main.class: absyn/*.java parser.java sym.java Lexer.java Main.java

%.class: %.java
	$(JAVAC) $(CLASSPATH) $^

Lexer.java: cm.flex
	$(JFLEX) cm.flex

parser.java: cm.cup
	#$(CUP) -dump -expect 3 cm.cup
	$(CUP) -expect 3 cm.cup

clean:
	rm -f parser.java Lexer.java sym.java *.class absyn/*.class *~

# Test files for Checkpoint 1 demo: 
fac: 
	make remake
	java -cp /usr/share/java/cup.jar:. Main testFiles/fac.cm

gcd: 
	make remake
	java -cp /usr/share/java/cup.jar:. Main testFiles/gcd.cm

sort: 
	make remake
	java -cp /usr/share/java/cup.jar:. Main testFiles/sort.cm

ex1:
	make remake
	java -cp /usr/share/java/cup.jar:. Main testFiles/1.cm

ex2:
	make remake
	java -cp /usr/share/java/cup.jar:. Main testFiles/2.cm

ex3:
	make remake
	java -cp /usr/share/java/cup.jar:. Main testFiles/3.cm

ex4:
	make remake
	java -cp /usr/share/java/cup.jar:. Main testFiles/4.cm

ex5:
	make remake
	java -cp /usr/share/java/cup.jar:. Main testFiles/5.cm
