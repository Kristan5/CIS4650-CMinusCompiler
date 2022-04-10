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

  public void emitRestore() {
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
      output = new PrintWriter(new FileOutputStream("test.tm", true)); //MAKE SURE TO CHANGE HARDCODED FILENAME
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

  public void emitOp(String op, int dest, int r, int r1, String comment) {
    String output = emitLoc + ": " + op + " " + dest + "," + r + "," + r1;
    
    outputCode(output);
    ++emitLoc;
    outputCode("\t" + comment + "\n");
  }


  // Expression List
  public void visit( ExpList expList, int offset) {
    while( expList != null ) {
      if (expList.head != null) {
       visit(expList.head, offset, false); 
      } 
      expList = expList.tail;
    } 
  }

  // ArrayDec
  // public void visit( ArrayDec expList) {
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
  // }

  // Assign Expression
  public void visit( AssignExp exp, int offset) {
    // emitComment("-> OP");

    // if(exp.lhs instanceof SimpleVar) {
    //   visit( (SimpleVar)exp.lhs, offset, true);
    //   emitRM("ST", AC, offset, FP, "OP: Push Left");
    //   offset = offset - 1;
    // } else if(exp.lhs instanceof IndexVar) {
    //   visit( (IndexVar)exp.lhs, offset, false);
    //   offset = offset - 1;
    // }

    // if(exp.rhs instanceof OpExp) {
    //   visit(exp.rhs, offset, false);
    // }
    // else if(exp.rhs instanceof CallExp) {
    //   visit(exp.rhs, offset, false);
    // }
    // else if(exp.rhs instanceof IntExp) {
    //   visit(exp.rhs, offset, false);
    // }
    // else if(exp.rhs instanceof VarExp) {
    //   visit(exp.rhs, offset, false);
    // }
    
    // offset = offset + 1;
    // emitRM("LD", 1, offset, FP, "OP: Load Left");
    // emitRM("ST", AC, 0, 1 , "Assign: Store Value");
    // emitComment("<- OP");
    
   }

  // If Expression
  public void visit( IfExp exp, int offset) {
    emitComment("-> IF");
    symbolTable.newScope();

    visit(exp.test, offset, false);
    int savedLoc = emitSkip(1);
    visit(exp.thenpart, offset, false);
    int savedLoc2 = emitSkip(0);

    emitBackup(savedLoc);
    emitRM_Abs("JEQ", 0, savedLoc2, "IF: Jump to Else Part");
    emitRestore();
    visit(exp.elsepart, offset, false);

    symbolTable.delCurrScope();
    emitComment("<- IF");
  }

  // Operation Expression
  public void visit( OpExp exp, int offset) {    
    emitComment("-> OP");

    if(exp.left instanceof VarExp) {
      VarExp varExp = (VarExp)exp.left;

      if(varExp.var instanceof SimpleVar) {
        visit(varExp, offset, false);
        emitRM("ST", AC, offset, FP, "OP: Push Left");
        offset = offset - 1;
      } else {
        visit(varExp, offset, true);
        offset = offset - 1;
      }
    }
    else if(exp.left instanceof OpExp) {
      visit(exp.left, offset, false);
      emitRM("ST", AC, offset, FP, "");
      offset = offset - 1;
    }
    else if(exp.left instanceof IntExp) {
      visit(exp.left, offset, false);
      emitRM("ST", AC, offset, FP, "OP: Push Left");
      offset = offset - 1;
    }
    else if(exp.left instanceof CallExp) {
      visit(exp.left, offset, false);
    }

    if(exp.right instanceof VarExp) {
      VarExp varExp = (VarExp)exp.right;

      if(varExp.var instanceof SimpleVar) {
        visit(varExp, offset, false);
      } else {
        visit(varExp, offset, true);
      }
    }
    else if(exp.right instanceof OpExp) {
      visit(exp.right, offset, false);
    }
    else if(exp.right instanceof IntExp) {
      visit(exp.right, offset, false);
    }
    else if(exp.right instanceof CallExp) {
      visit(exp.right, offset, false);
    }
    
    offset = offset + 1;
    emitRM("LD", 1, offset, FP, "OP: Load Left");

    switch(exp.op) {
      case OpExp.PLUS:
        emitOp("ADD", AC, 1, AC, "OP +");
        break;
      case OpExp.MINUS:
        emitOp("SUB", AC, 1, AC, "OP -");
        break;
      case OpExp.TIMES:
        emitOp("MUL", AC, 1, AC, "OP *");
        break;
      case OpExp.DIVIDE:
        emitOp("DIV", AC, 1, AC, "OP /");
        break;
      case OpExp.EQ:
        emitOp("EQU", AC, 1, AC, "OP =");
        break;
      case OpExp.EQEQ:
        emitOp("SUB", AC, 1, AC, "OP == ");
        emitRM("JEQ", AC, 2, PC, " ");
        emitRM("LDC", AC, 0, 0, "False Casse");
        emitRM("LDA", PC, 1, PC, "Unconditional Jump");
        emitRM("LDC", AC, 1, 0, "True Case");
        break;
      case OpExp.LT:
        emitOp("SUB", AC, 1, AC, "OP <");
        emitRM("JLT", AC, 2, PC, " ");
        emitRM("LDC", AC, 0, 0, "False Case");
        emitRM("LDA", PC, 1, PC, "Unconditional Jump");
        emitRM("LDC", AC, 1, 0, "True Case");
        break;
      case OpExp.GT:
        emitOp("SUB", AC, 1, AC, "OP >");
        emitRM("JGT", AC, 2, PC, " ");
        emitRM("LDC", AC, 0, 0, "False Case");
        emitRM("LDA", PC, 1, PC, "Unconditional Jump");
        emitRM("LDC", AC, 1, 0, "True Case");
        break;
      case OpExp.NOTEQ:
        emitOp("SUB", AC, 1, AC, "OP !=");
        emitRM("JNE", AC, 2, PC, " ");
        emitRM("LDC", AC, 0, 0, "False Case");
        emitRM("LDA", PC, 1, PC, "Unconditional Jump");
        emitRM("LDC", AC, 1, 0, "True Case");
        break;
      case OpExp.LTE:
        emitOp("SUB", AC, 1, AC, "OP <=");
        emitRM("JLE", AC, 2, PC, " ");
        emitRM("LDC", AC, 0, 0, "False Case");
        emitRM("LDA", PC, 1, PC, "Unconditional Jump");
        emitRM("LDC", AC, 1, 0, "True Case");
        break;
      case OpExp.GTE:
        emitOp("SUB", AC, 1, AC, "OP >=");
        emitRM("JGE", AC, 2, PC, " ");
        emitRM("LDC", AC, 0, 0, "False Case");
        emitRM("LDA", PC, 1, PC, "Unconditional Jump");
        emitRM("LDC", AC, 1, 0, "True Case");
        break;
    }

    emitComment("<- OP");
  }

  // Call Expression
  public void visit( CallExp exp, int offset) {
    String name = exp.function;
    FunctionSymbol functionSymbol = (FunctionSymbol)symbolTable.getFunction(name);
    
    emitComment("-> Call");
    emitComment("Call of Function: " + exp.function);

    int off = -2;

    while(exp.args != null) {
      if(exp.args.head != null) {
        visit(exp.args.head, offset, false);
        emitRM("ST", AC, offset + off, FP, "OP: Push Left");
        off = off - 1;
      }
      exp.args = exp.args.tail;
    }
     
    emitRM("ST", FP, offset, FP, "Push OFP");
    emitRM("LDA", FP, offset, FP, "Push Frame");
    emitRM("LDA", 0, 1, PC, "Load AC with Ret Pointer");
    emitRM_Abs("LDA", PC, functionSymbol.address, "Jump to Function Location");
    emitRM("LD", FP, 0, FP, "Pop Frame");
    emitComment("<- Call");

  }

  // Declaration List Expression
  public void visit( DecList decList) {
    try {
      PrintWriter writer = new PrintWriter(this.filename);
      writer.close();
    } catch (FileNotFoundException err) {
      err.printStackTrace();
    }

    // Init symbol table and new scope
    symbolTable.newScope(); 
    
    // input() and output() functions
    FunctionSymbol input = new FunctionSymbol(Type.INT, "input", new ArrayList<Symbol>());
    symbolTable.addSymbol("input", input);
    
    ArrayList<Symbol> params = new ArrayList<Symbol>();
    params.add(new VarSymbol(Type.INT, ""));
    
    FunctionSymbol output = new FunctionSymbol(Type.VOID, "output", params);
    symbolTable.addSymbol("output", output);

    // Prelude for code generation, Slide 10, Lecture 11 - TMSimulator
    emitComment("Standard Prelude");
    emitRM("LD", GP, 0, AC, "Load GP with max address");
    emitRM("LDA", FP, 0, GP, "Copy GP to FP");
    emitRM("ST", 0, 0, 0, "Clear value at location 0");
    int savedLoc = emitSkip(1);

    // Jump around I/O functions, Slide 15 and 20, Lecture 11 - TMSimulator
    // Input
    emitComment("Jump around I/O functions");
    emitComment("Code for Input Routine");
    emitRM("ST", 0, -1, FP, "Store return");
    emitOp("IN", 0, 0, 0, "Input");
    emitRM("LD", PC, -1, FP, "Return caller");

    // Output
    emitComment("Code for Output Routine");
    emitRM("ST", 0, -1, FP, "Store return");
    emitRM("LD", 0, -2, FP, "Load output value");
    emitOp("OUT", 0, 0, 0, "Output");
    emitRM("LD", 7, -1, FP, "Return caller");

    // Jump around I/O
    int savedLoc2 = emitSkip(0);
    emitBackup(savedLoc);
    emitRM_Abs("LDA", PC, savedLoc2, "Jump around I/O code");
    emitRestore();

    /* Recursive code generation */
    while (decList != null){
			if (decList.head != null){
				visit(decList.head);
			}
			decList = decList.tail;
		}

    FunctionSymbol adr = (FunctionSymbol)symbolTable.getFunction("main");

    // Generate Code

    // Finale for code generation, Slide 16, Lecture 11 - TMSimulator
    emitComment("Finale Generation");
    emitRM("ST", FP, globalOffset, FP, "Push Old Frame Pointer");
    emitRM("LDA", FP, globalOffset, FP, "Push frame");
    emitRM("LDA", 0, 1, PC, "Load AC with return pointer");
    emitRM_Abs("LDA", PC, adr.address, "Jump to main location");
    emitRM("LD", FP, 0, FP, "Pop frame");
    emitOp("HALT", 0, 0, 0, "HALT");

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

    symbolTable.delCurrScope();
  }
  
  // Declaration
  public void visit( Dec decl) {
    if(decl instanceof VarDec) {

      VarDec v = (VarDec)decl;

      if(v instanceof SimpleDec) {
        SimpleDec sV = (SimpleDec)v;
        VarSymbol symb = new VarSymbol(Type.INT, sV.name, globalOffset);
        symbolTable.addSymbol(sV.name, symb);
        emitComment("Allocating global variable: " + sV.name);
        emitComment("<- varDecl");
        globalOffset = globalOffset - 1;
      } else if(v instanceof ArrayDec) {
        ArrayDec aV = (ArrayDec)v;
        ArraySymbol symb = new ArraySymbol(Type.INT, aV.name, aV.size.value, globalOffset - (aV.size.value - 1));
        symbolTable.addSymbol(aV.name, symb);
        emitComment("Allocating global variable: " + aV.name);
        emitComment("<- varDecl");
        globalOffset = globalOffset - aV.size.value;
      }
      visit((VarDec)decl);
    } else if(decl instanceof FunctionDec) {
      visit((FunctionDec)decl);
    }
  }

  // Expression
  public int visit( Exp exp, int offset, boolean isAdd) {
    if(exp instanceof ReturnExp) {
      visit((ReturnExp)exp, offset);
    } else if(exp instanceof CompoundExp) {
      offset = visit((CompoundExp)exp, offset);
    } else if(exp instanceof WhileExp) {
      visit((WhileExp)exp, offset);
    } else if(exp instanceof IfExp) {
      visit((IfExp)exp, offset);
    } else if(exp instanceof AssignExp) {
      visit((AssignExp)exp, offset);
    } else if(exp instanceof OpExp) {
      visit((OpExp)exp, offset);
    } else if(exp instanceof CallExp) {
      visit((CallExp)exp, offset);
    } else if(exp instanceof VarExp) {
      visit((VarExp)exp, offset, isAdd);
    } else if(exp instanceof IntExp) {
      visit((IntExp)exp);
    }
    return offset;
  }

  public void visit( IntExp exp) {
    emitComment("-> Constant");
    emitRM("LDC", AC, exp.value, 0, "Load Constant");
    emitComment("<- Constant");
  }

  // Function Expression
  public void visit( FunctionDec exp) {
    emitComment("-> FunctionDec");
    emitComment("Processing Function: " + exp.function);
    emitComment("Jump around function body here");

    int offset = -2;
    int savedLoc = emitSkip(1);

    FunctionSymbol function = new FunctionSymbol(Type.INT, exp.function, null, emitLoc);
    symbolTable.addSymbol(exp.function, function);
    symbolTable.newScope();

    emitRM("ST", 0, -1, FP, "Store Return");
    offset = visit(exp.param_list, offset, true);
    offset = visit(exp.test, offset, true);
    emitRM("LD", PC, -1, FP, "Return Caller");

    int savedLoc2 = emitSkip(0);
    emitBackup(savedLoc);
    emitRM_Abs("LDA", PC, savedLoc2, "Jump around function body");
    emitRestore();
    emitComment("<- FunctionDec");
    symbolTable.delCurrScope();
  
  }

  // Compound Expression
  public int visit( CompoundExp exp, int offset) {
    emitComment("-> Compound Statement");
    offset = visit(exp.decList, offset, false);
    visit(exp.expList, offset);
    emitComment("<- Compound Statement");
    return offset;
  }

  /*Compound Expression
  public void visit( CompoundExp exp) {
    // symbolTable.newScope();
    // visit(exp.decList);
    // visit(exp.expList);
    // symbolTable.delCurrScope(); 
  }*/

  // Index Variable
  public void visit( IndexVar exp, int offset, boolean isAdd) {
    IndexVar e = (IndexVar) exp;
    String name = e.name;
    ArraySymbol var = (ArraySymbol)symbolTable.getSymbol(name);

    emitComment("-> Subs");

    if(symbolTable.symbExists(name) == 0) {
      emitRM("LD", AC, var.offset, GP, "Load ID Value");
      emitRM("ST", AC, offset, GP, "Store Array Address");
      offset = offset - 1;
      visit(e.index, offset, false);
      emitComment("<- Subs");
    } else {
      emitRM("LD", AC, var.offset, FP, "Load ID Value");
      emitRM("ST", AC, offset, FP, "Store Array Address");
      offset = offset - 1;
      visit(e.index, offset, false);
      emitComment("<- Subs");
    }

  }

  // Return Expression
  public void visit( ReturnExp exp, int offset) {
    emitComment("-> Return");
    visit(exp.test, offset, false);
    emitRM("LD", PC, -1, FP, "Return to Caller");
    emitComment("<- Return");
  }

  // Simple Declaration
  // public void visit( SimpleDec exp) {
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
  // }
  
  // Simple Variable
  public void visit( SimpleVar exp, int offset, boolean isAdd) {
    String name = exp.name;
    VarSymbol var = (VarSymbol) symbolTable.getSymbol(name);

    emitComment("-> ID");
    emitComment("Looking up ID: " + name);

    if(symbolTable.symbExists(name) == 0) {
      if(isAdd) {
        emitRM("LDA", 0, var.offset, GP, "Load ID Address");
      } else {
        emitRM("LD", 0, var.offset, GP, "Load ID Value");
      }
    } else {
      if(isAdd) {
        emitRM("LDA", 0, var.offset, FP, "Load ID Address");
      } else {
        emitRM("LD", 0, var.offset, FP, "Load ID Address");
      }
    }

    emitComment("<- ID");
  }

  // Variable
  // public void visit( Var exp) {
    // if(exp instanceof IndexVar) {
    //   visit((IndexVar)exp);
    // } else if(exp instanceof SimpleVar) {
    //   visit((SimpleVar)exp);
    // }
  // }

  // Variable Declaration
  public int visit( VarDec exp, int offset, boolean isParam) {
    if(isParam) {
      if(exp instanceof ArrayDec) {
        ArrayDec dec = (ArrayDec)exp;
        ArraySymbol symb = new ArraySymbol(Type.INT, dec.name, 1, offset--);
        symbolTable.addSymbol(dec.name, symb);
      } 
      else if(exp instanceof SimpleDec) {
        SimpleDec dec = (SimpleDec)exp;
        VarSymbol symb = new VarSymbol(Type.INT, dec.name, offset);
        offset = offset - 1;
        symbolTable.addSymbol(dec.name, symb);
      }
    } else {
      if(exp instanceof ArrayDec) {
        ArrayDec dec = (ArrayDec)exp;
        offset = offset - (dec.size.value - 1);
        ArraySymbol symb = new ArraySymbol(Type.INT, dec.name, dec.size.value, offset);
        offset = offset - 1;
        symbolTable.addSymbol(dec.name, symb);
        emitComment("Processing local variable : " + dec.name);
      } 
      else if(exp instanceof SimpleDec) {
        SimpleDec dec = (SimpleDec)exp;
        ArraySymbol symb = new ArraySymbol(Type.INT, dec.name, offset);
        offset = offset - 1;
        symbolTable.addSymbol(dec.name, symb);
        emitComment("Processing local variable : " + dec.name);
      }
    }
    return offset;
  }

  // Variable Declaration List
  public int visit( VarDecList exp, int offset, boolean isParam) {
    while(exp != null) {
      if(exp.head != null) {
        offset = visit(exp.head, offset, isParam);
      }
      exp = exp.tail;
    }
    return offset;
  }

  // Variable Expression
  public void visit( VarExp exp, int offset, boolean isAdd) {
    if(exp.var instanceof IndexVar) {
      IndexVar indExp = (IndexVar) exp.var;
      ArraySymbol var = (ArraySymbol) symbolTable.getSymbol(indExp.name);

      if(symbolTable.symbExists(indExp.name) == 0) {
        emitComment("-> Subs");
        emitRM("LD", AC, var.offset, GP, "Load ID Value");
        emitRM("ST", AC, offset, GP, "Store Array Address");
        offset = offset - 1;
        visit(indExp.index, offset, false);
        emitComment("<- Subs");
      }
      else {
        emitComment("-> Subs");
        emitRM("LD", AC, var.offset, FP, "Load ID Value");
        emitRM("ST", AC, offset, FP, "Store Array Address");
        offset = offset - 1;
        visit(indExp.index, offset, false);
        emitComment("<- Subs");
      }
    }
    else if(exp.var instanceof SimpleVar) {
      SimpleVar simExp = (SimpleVar) exp.var;
      VarSymbol var = (VarSymbol) symbolTable.getSymbol(simExp.name);
      
      if(symbolTable.symbExists(simExp.name) == 0) {
        emitComment("-> ID");
        emitComment("Looking up ID: " + simExp.name);
        if(isAdd) {
          emitRM("LDA", 0, var.offset, GP, "Load ID Address");
        } else {
          emitRM("LD", 0, var.offset, GP, "Load ID Value");
        }
        emitComment("<- ID");
      } 
      else {
        emitComment("-> ID");
        emitComment("Looking up ID: " + simExp.name);
        if(isAdd) {
          emitRM("LDA", 0, var.offset, FP, "Load ID Address");
        } else {
          emitRM("LD", 0, var.offset, FP, "Load ID Value");
        }
        emitComment("<- ID");
      }
    }
  }

  // While Expression
  public void visit( WhileExp exp, int offset) {
    emitComment("-> WHILE");
    symbolTable.newScope();

    emitComment("While: Jump After Body Comes Back Here");
    int savedLoc = emitSkip(0);
    visit(exp.test, offset, false);
    int savedLoc2 = emitSkip(1);
    visit(exp.block, offset, false);
    emitRM_Abs("LDA", PC, savedLoc, "While: Absolute Jump to Test");
    int savedLoc3 = emitSkip(0);
    emitBackup(savedLoc2);
    emitRM_Abs("JEQ", 0, savedLoc3, "While: Jump to End");
    
    symbolTable.delCurrScope();
    emitComment("<- While");
  }

}
