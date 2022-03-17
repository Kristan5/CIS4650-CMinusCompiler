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
  // public final static boolean SHOW_TREE = true;

  public static String filename = null;
  public static boolean arg_AST = false; 
  public static boolean arg_SYM = false; 
  // public static Absyn result; 

  static public void main(String argv[]) {    
    /* Start the parser */

    // System.out.println("filename: " + filename);
    // System.out.println("arg_AST: " + arg_AST);
    // System.out.println("arg_SYM: " + arg_SYM);
    
    try {
      setFlags(argv);
      parser p = new parser(new Lexer(new FileReader(filename)));
      
      // Set flags for showing AST or SymbolTree
      
      Absyn result = (Absyn)(p.parse().value);
      // result = (Absyn)(p.parse().value);
      // p.filename = filename;
      // p.arg_AST = arg_AST;
      // p.arg_SYM = arg_SYM;
      // p.mainResult = result;
      if (arg_AST && result != null) {
        System.out.println("The abstract syntax tree is:");
        ShowTreeVisitor visitor = new ShowTreeVisitor();
        result.accept(visitor, 0); 
      }
      else if (arg_SYM && result != null) {
        System.out.println("The symbol table is:");

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
        System.out.println("Showing Abstract Syntax Tree:");
        arg_AST = true; 
      }
      else if (arg.equals("-s")) {
        // Show Symbol Table
        System.out.println("Showing Symbol Table:");
        arg_SYM = true; 
      }
      else {
        System.err.println("Error: Please enter a valid argument. {-a, -s}");
      }
    }
  }
}


