/*
  Created by: Kristan Samaroo and Indeep Farma
  File Name: Main.java
  To Build: 
  After the scanner, tiny.flex, and the parser, tiny.cup, have been created.
    javac Main.java
  
  To Run: 
    java -classpath /usr/share/java/cup.jar:. Main gcd.tiny

  where gcd.tiny is an test input file for the tiny language.
*/
   
import java.io.*;
import absyn.*;
   
class Main {
  public static boolean SHOW_TREE = false;
  public static boolean SHOW_SYM = false;

  public static String filename = null;

  static public void main(String argv[]) {    
    /* Start the parser */
    try {
      setFlags(argv);
      
      parser p = new parser(new Lexer(new FileReader(filename)));
      
      // Set flags for showing AST or SymbolTree
      p.filename = filename;
      p.SHOW_SYM = SHOW_SYM;

      Absyn result = (Absyn)(p.parse().value);

      if (SHOW_TREE && result != null) {
        System.out.println("The abstract syntax tree is:");
        ShowTreeVisitor visitor = new ShowTreeVisitor();
        result.accept(visitor, 0); 
      }
    } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }
  }

  public static void setFlags(String argv[]) {
    for (String arg: argv) {
      if (arg.endsWith(".cm")) {
        filename = arg; 
      }
      else if (arg.equals("-a")){
        // Show AST
        System.out.println("Showing Abstract Syntax Tree:\n");
        SHOW_TREE = true; 
      }
      else if (arg.equals("-s")) {
        // Show Symbol Table
        System.out.println("\n");
        SHOW_SYM = true; 
      }
      else {
        System.err.println("Error: Please enter a valid argument. {-a, -s}");
      }
    }
  }
}


