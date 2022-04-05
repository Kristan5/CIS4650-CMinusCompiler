import absyn.*; 
import symbol.*; 
import java.io.*;
import java.util.ArrayList;

public class AssemblyGenerator {
  public DecList result; 
  public SymbolTable symbolTable; 
  public String filename; 

  // Special Registers, Slide 10 Lecture 11 - TMSimulator
  public static final int PC = 7;
  public static final int GP = 6; // Points to top of dMem
  public static final int FP = 5; // Points to curr stackframe in dMem
  public static final int AC = 0;
  public static final int AC1 = 1;

  //Points to curr instr generating, may go back to earlier loc for backpatching
  public int emitLoc = 0;
  // Points to next available space so we can continue adding new instr
  public int highEmitLoc = 0; 
  // Points to the bottom of the global stackframe dMem
  public int globalOffset = 0;


  public AssemblyGenerator(String filename, DecList result) {
    this.result = result; 
    this.filename = filename; 
    this.symbolTable = new SymbolTable(false);
    
    visit(result);
  }

  /*  Code Emitting Routines: Slide 19 Lecture 11 - TMSimulator
      Functions to maintain code space: some methods like emitRO, emitRM, and
      emitComment need to be added */
  public int emitSkip(int distance) {
    int i = emitLoc;
    emitLoc += distance;

    if(highEmitLoc < emitLoc) {
      highEmitLoc = emitLoc;
    }

    return i;
  }

  public void emitBackup(int loc) {
    if(loc > highEmitLoc) {
      emitComment("BUG in emitBackup");
    }
    emitLoc = loc;
  }

  public void emitRestor(void) {
    emitLoc = highEmitLoc;
  }

  public void emitRM_Abs(String op, int r, int a, String comment) {
    String output = emitLoc + ": " + op + " " + r + "," + (a - (emitLoc + 1)) + "(" + PC + ")";

    outputCode(output);
    ++emitLoc;
    outputCode("\t" + comment + "\n");

    if(highEmitLoc < emitLoc) {
      highEmitLoc = emitLoc;
    }
  }

  // Write out code to file
  public void outputCode(String code) {
    PrintWriter output = null;

    try {
      output = new PrintWriter(new FileOutputStream(this.filename, true));
      output.printf(code);
      output.close();
    } catch( FileNotFoundException err) {
      err.printStackTrace();
    }
  }

  public void emitComment(String comment) {
    String output = "* " + comment + "\n";
    outputCode(output);
  }

  public void emitRM(String op, int r, int offset, int r1, String comment) {
    String output = emitLoc + ": " + op + " " + r + "," + offset + "(" + r1 + ")";

    outputCode(output);
    ++emitLoc;
    outputCode("\t" + comment + "\n");

    if(highEmitLoc < emitLoc) {
      highEmitLoc = emitLoc;
    }
  }



  // Expression List
  public void visit( ExpList expList) {
    // while( expList != null ) {
    //   if (expList.head != null) visit(expList.head); 
    //   expList = expList.tail;
    // } 
  }

  // ArrayDec
  public void visit( ArrayDec expList) {
    // int type = expList.type.type;
    // String name = expList.name;
    // int size = expList.size.value;
    // int row = expList.row + 1; 

    // // Mismatched types: 
    // if (type == Type.VOID) {
    //   setHasErrors(); 
    //   System.err.println("Error: Variable: '" + name + "' was declared as void on line: " + row);       
    // }

    // // Redeclaration: 
    // if (symbolTable.isSameScope(name)) {
    //   setHasErrors();
    //   System.err.println("Error: Redeclaration of variable '" + name + "' on line: " + row); 
    //   return;
    // }

    // ArraySymbol varSymbol = new ArraySymbol(type, name, size);
    // symbolTable.addSymbol(name, (Symbol)varSymbol);
  }

  // Assign Expression
  public void visit( AssignExp exp) {
    // visit(exp.lhs);
    // visit(exp.rhs);
  }

  // If Expression
  public void visit( IfExp exp) {
    // visit(exp.test);
    // visit(exp.thenpart);
    // if (exp.elsepart != null ){
    //   visit(exp.elsepart);
    // }
  }

  // Operation Expression
  public void visit( OpExp exp) {    
    // visit(exp.left);
    // visit(exp.right);
  }

  // Call Expression
  public void visit( CallExp exp) {
    // String name = exp.function;
    // int row = exp.row + 1; 
    // FunctionSymbol functionSymbol = (FunctionSymbol)symbolTable.getFunction(name);
    // int functionSymbolParamsCount = symbolTable.getFunctionParamCount(name);
    // int callExpParamsCount = exp.params_count(); 

    // // Check if function exists
    // if (symbolTable.getFunction(name) == null) {
    //   setHasErrors();
    //   System.err.println("Error: Undefined function '" + name + "' on line: " + row); 
    // }
    
    // // Check if signature correct (if number of arguments is correct)F
    // if (functionSymbolParamsCount != callExpParamsCount) {
    //   setHasErrors();
    //   System.err.println("Error: Wrong number of parameters for function '" + name + "' on line: " + row); 
    // }

    // // Check if params are correct 
    // checkFunctionCallParams(exp.args, functionSymbol, functionSymbolParamsCount); 

  }

  // Declaration List Expression
  public void visit( DecList decList) {
    // // System.out.println("DecList");
    // symbolTable.newScope(); 
    
    // // input() and output() functions
    // FunctionSymbol input = new FunctionSymbol(Type.INT, "input", new ArrayList<Symbol>());
    // symbolTable.addSymbol("input", input);
    
    // ArrayList<Symbol> params = new ArrayList<Symbol>();
    // params.add(new VarSymbol(Type.INT, ""));
    
    // FunctionSymbol output = new FunctionSymbol(Type.VOID, "output", params);
    // symbolTable.addSymbol("output", output);

    // while(decList != null) {
    //   if(decList.head != null)
    //     visit(decList.head);
    //   decList = decList.tail;
    // }

    // // Check Main Function
    // if (!this.hasMain) {
    //   setHasErrors(); 
    //   System.err.println("Error: File does not have a main function");
    // }

    // symbolTable.delCurrScope();
  }
  
  // Declaration
  public void visit( Dec decl) {
    // if(decl instanceof VarDec) {
    //   visit((VarDec)decl);
    // } else if(decl instanceof FunctionDec) {
    //   visit((FunctionDec)decl);
    // }
  }

  // Expression
  public void visit( Exp exp) {
    if(exp instanceof ReturnExp) {
      visit((ReturnExp)exp);
    } else if(exp instanceof CompoundExp) {
      visit((CompoundExp)exp);
    } else if(exp instanceof WhileExp) {
      visit((WhileExp)exp);
    } else if(exp instanceof IfExp) {
      visit((IfExp)exp);
    } else if(exp instanceof AssignExp) {
      visit((AssignExp)exp);
    } else if(exp instanceof OpExp) {
      visit((OpExp)exp);
    } else if(exp instanceof CallExp) {
      visit((CallExp)exp);
    } else if(exp instanceof VarExp) {
      visit((VarExp)exp);
    } else if(exp instanceof IntExp) {
      visit((IntExp)exp);
    }
  }

  public void visit( IntExp exp) {
    
  }

  // Function Expression
  public void visit( FunctionDec exp) {
    // // Adding params for function
    // ArrayList<Symbol> functionParams = addFunctionParams(exp.param_list);
    // // Adding function to table
    // int type = exp.type.type;
    // String name = exp.function;
    // FunctionSymbol functionSymbol = new FunctionSymbol(type, name, functionParams); 
    // symbolTable.addSymbol(name, (Symbol)functionSymbol);
    // symbolTable.newScope();
    
    // // Setting FunctionReturnType
    // functionReturnType = type;

    // if (name.equals("main")) hasMain = true; 

    // // Analyze param_list 
    // visit(exp.param_list);
    // // Analyze function body (CompoundExp)
    // visit((CompoundExp)exp.test, functionSymbol);
  }

  // Compound Expression
  public void visit( CompoundExp exp, FunctionSymbol function) {
    // // TODO: NEED TO FIGURE OUT WAY TO CHECK IF GETS TO END OF FUNCTION WITHOUT RETURN
    // boolean nonVoid;

    // if (function.type == Type.INT) nonVoid = true; 
    // else nonVoid = false; 

    // visit(exp.decList);
    // visit(exp.expList);

    // symbolTable.delCurrScope();
  }

  //Compound Expression
  public void visit( CompoundExp exp) {
    // symbolTable.newScope();
    // visit(exp.decList);
    // visit(exp.expList);
    // symbolTable.delCurrScope(); 
  }

  // Index Variable
  public void visit( IndexVar exp) {
  
    // Symbol sym = symbolTable.getSymbol(exp.name);
    // int row = exp.row + 1;

    // if (sym != null) {
    //   if (!(sym instanceof ArraySymbol)) {
    //     setHasErrors(); 
    //     System.err.println("Error: Line " + row + ": " + exp.name + " is not an array");
    //   }
    // }
    // else {
    //   setHasErrors(); 
    //   System.err.println("");
    // }

    // visit(exp.index);

  }

  // Return Expression
  public void visit( ReturnExp exp) {
    // // If void function returns something
    // if (functionReturnType == Type.VOID) {
    //   if (exp.test != null) {
    //     setHasErrors(); 
    //     int row = exp.row + 1; 
    //     System.err.println("Error: Function with VOID return type returns value on line: " + row);
    //     return; 
    //   }
    // }
    // else {
    //   if (exp.test == null) {
    //     setHasErrors(); 
    //     int row = exp.row + 1; 
    //     System.err.println("Error: Function with Non-Void (INT) return type returns nothing on line: " + row); 
    //   }
    //   else {
    //     visit(exp.test);
    //   }
    // }
  }

  // Simple Declaration
  public void visit( SimpleDec exp) {
    // int type = exp.type.type;
    // String name = exp.name;

    // // Mismatched types: 
    // if (type == Type.VOID) {
    //   setHasErrors(); 
    //   int row = exp.row + 1; 
    //   System.err.println("Error: Variable: '" + name + "' was declared as void on line: " + row);       
    // }

    // // Redeclaration: 
    // if (symbolTable.isSameScope(name)) {
    //   setHasErrors(); 
    //   int row = exp.row + 1; 
    //   System.err.println("Error: Redeclaration of variable '" + name + "' on line: " + row); 
    //   return;
    // }

    // VarSymbol varSymbol = new VarSymbol(type, name);
    // symbolTable.addSymbol(name, (Symbol)varSymbol);
  }
  
  // Simple Variable
  public void visit( SimpleVar exp) {
    // String name = exp.name;
    // int row = exp.row + 1;

    // if (symbolTable.getSymbol(name) != null) {
    //   if (symbolTable.getSymbol(name) instanceof VarSymbol) {
    //     if (symbolTable.getSymbol(name).type != Type.INT) {
    //       setHasErrors(); 
    //       System.err.println("Error: Expected integer instead of void variable '" + name + "' on line: " + row); 
    //     }
    //   }
    //   else if (symbolTable.getSymbol(name).type != Type.INT) {
    //     setHasErrors();
    //     System.err.println("Error: Expected integer instead of void Array variable '" + name + "' on line: " + row); 
    //   }
    //   else {
    //     setHasErrors();
    //     System.err.println("Error: Can't convert array '" + name + "' to int on line: " + row); 
    //   }
    // }
    // else {
    //   setHasErrors(); 
    //   System.err.println("Error: Undefined variable '" + name + "' on line: " + row); 
    // }
  }

  // Variable
  public void visit( Var exp) {
    // if(exp instanceof IndexVar) {
    //   visit((IndexVar)exp);
    // } else if(exp instanceof SimpleVar) {
    //   visit((SimpleVar)exp);
    // }
  }

  // Variable Declaration
  public void visit( VarDec exp) {
    // if(exp instanceof SimpleDec) {
    //   visit((SimpleDec)exp);
    // } else if(exp instanceof ArrayDec) {
    //   visit((ArrayDec)exp);
    // } 
  }

  // Variable Declaration List
  public void visit( VarDecList exp) {
    // while(exp != null) {
    //   if(exp.head != null) {
    //     visit(exp.head);
    //   }
    //   exp = exp.tail;
    // }
  }

  // Variable Expression
  public void visit( VarExp exp) {
    // visit(exp.var);
  }

  // While Expression
  public void visit( WhileExp exp) {
    // // Might have to change this
    // visit(exp.test);
    // visit(exp.block);
  }

}
