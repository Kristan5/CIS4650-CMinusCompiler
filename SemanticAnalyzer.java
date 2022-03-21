import java.util.ArrayList;

import absyn.*; 
import symbol.*; 

// TODO: Add exitscope() method call to compound exp after symbol table implementation
public class SemanticAnalyzer {
  private SymbolTable symbolTable; 
  private int functionReturnType; 

  private boolean hasMain;
  private boolean hasErrors;

  public SemanticAnalyzer(boolean SHOW_SYM, DecList result) {
    // Will need to add SHOW_SYM boolean to SymbolTable params
    symbolTable = new SymbolTable(SHOW_SYM);
    
    this.hasMain = false; 
    this.hasErrors = false; 

    // Start visiting declist
    visit(result);
  }
  
  // Expression List
  public void visit( ExpList expList) {
    while( expList != null ) {
      if (expList.head != null) visit(expList.head); 
      expList = expList.tail;
    } 
  }

  // ArrayDec
  public void visit( ArrayDec expList) {
    
    
    // visit(expList.type);
    
    // if(expList.size != null) {
    //   visit(expList.size);
    // }
  }

  // Assign Expression
  public void visit( AssignExp exp) {
    visit(exp.lhs);
    visit(exp.rhs);
  }

  // If Expression
  public void visit( IfExp exp) {
    // Might have to change this
    visit(exp.test);
    visit(exp.thenpart);
    if (exp.elsepart != null ){
      visit(exp.elsepart);
    }
  }
  
  // Int Expression
  public void visit( IntExp exp) {


  }

  // Operation Expression
  public void visit( OpExp exp) {    
    // Might have to change this
    visit(exp.left);
    visit(exp.right);
  }

  // Call Expression
  public void visit( CallExp exp) {
    

    visit(exp.args);
  }

  // Declaration List Expression
  public void visit( DecList decList) {
    // System.out.println("DecList");
    symbolTable.newScope(); 
    
    // TODO: CHECK INPUT/ OUTPUT FUCNTION???
    
    while(decList != null) {
      if(decList.head != null)
        visit(decList.head);
      decList = decList.tail;
    }

    // Check Main Function
    if (!this.hasMain) {
      setHasErrors(); 
      System.err.println("Error: File does not have a main function");
    }

    symbolTable.delCurrScope();
  }
  
  // Declaration
  public void visit( Dec decl) {
    if(decl instanceof VarDec) {
      visit((VarDec)decl);
    } else if(decl instanceof FunctionDec) {
      visit((FunctionDec)decl);
    }
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
    } else if(exp instanceof IntExp) {
      visit((IntExp)exp);
    } else if(exp instanceof VarExp) {
      visit((VarExp)exp);
    }
  }

  // Function Expression
  public void visit( FunctionDec exp) {
    // Adding params for function
    ArrayList<Symbol> functionParams = addFunctionParams(exp.param_list);
    // Adding function to table
    int type = exp.type.type;
    String name = exp.function;
    FunctionSymbol functionSymbol = new FunctionSymbol(type, name, functionParams); 
    symbolTable.addSymbol(name, (Symbol)functionSymbol);
    symbolTable.newScope();
    
    // Setting FunctionReturnType
    functionReturnType = type;

    if (name.equals("main")) hasMain = true; 

    // Analyze param_list 
    visit(exp.param_list);
    // Analyze function body (CompoundExp)
    visit((CompoundExp)exp.test, functionSymbol);
  }

  // Compound Expression
  public void visit( CompoundExp exp, FunctionSymbol function) {
    // TODO: NEED TO FIGURE OUT WAY TO CHECK IF GETS TO END OF FUNCTION WITHOUT RETURN
    boolean nonVoid;

    if (function.type == Type.INT) nonVoid = true; 
    else nonVoid = false; 

    visit(exp.decList);
    visit(exp.expList);

    symbolTable.delCurrScope();
  }

  //Compound Expression
  public void visit( CompoundExp exp) {
    symbolTable.newScope();
    visit(exp.decList);
    visit(exp.expList);



    symbolTable.delCurrScope(); 
  }

  // TODO: NEED TO IMPLEMENT THIS??
  // public void visit( CompoundExp exp, boolean CHANGETHIS) {
  //   boolean nonVoid = false; 

  // }

  // Index Variable
  public void visit( IndexVar exp) {
  
    Symbol sym = symbolTable.getSymbol(exp.name);
    int row = exp.row + 1;

    if (sym != null) {
      if (!(sym instanceof ArraySymbol)) {
        setHasErrors(); 
        System.err.println("Error: Line " + row + ": " + exp.name + " is not an array");
      }
    }
    else {
      setHasErrors(); 
      System.err.println("");
    }

    visit(exp.index);

  }
  
  // // Nil Expression
  // public void visit( NilExp exp) {
  
  // }

  // Return Expression
  public void visit( ReturnExp exp) {
    // If void function returns something
    if (functionReturnType == Type.VOID) {
      if (exp.test != null) {
        setHasErrors(); 
        int row = exp.row + 1; 
        System.err.println("Error: Function with VOID return type returns value on line: " + row);
        return; 
      }
    }
    else {
      if (exp.test == null) {
        setHasErrors(); 
        int row = exp.row + 1; 
        System.err.println("Error: Function with Non-Void (INT) return type returns nothing on lin:e: " + row); 
      }
      else {
        visit(exp.test);
      }
    }
  }

  // Simple Declaration
  public void visit( SimpleDec exp) {
    int type = exp.type.type;
    String name = exp.name;

    // Mismatched types: 
    if (type == Type.VOID) {
      setHasErrors(); 
      int row = exp.row + 1; 
      System.err.println("Error: Variable: '" + name + "' was declared as void on line: " + row);       
    }

    // Redeclaration: 
    if (symbolTable.isSameScope(name)) {
      setHasErrors(); 
      int row = exp.row + 1; 
      System.err.println("Error: Redeclaration of variable '" + name + "' on line: " + row); 
      return;
    }

    VarSymbol varSymbol = new VarSymbol(type, name);
    symbolTable.addSymbol(name, (Symbol)varSymbol);
  }
  
  // Simple Variable
  public void visit( SimpleVar exp) {
    String name = exp.name;
    int row = exp.row;
    // TODO: CHECK IF THIS WORKS
    if (symbolTable.getSymbol(name) != null) {
      if (symbolTable.getSymbol(name) instanceof VarSymbol && symbolTable.getSymbol(name).type != Type.INT) {
        setHasErrors(); 
        System.err.println("Error: Expected integer instead of void variable '" + name + "' on line: " + row); 
      }
      else if (symbolTable.getSymbol(name).type != Type.INT) {
        setHasErrors();
        System.err.println("Error: Expected integer instead of void Array variable '" + name + "' on line: " + row); 
      }
      else {
        setHasErrors();
        System.err.println("Error: Can't convert array '" + name + "' to int on line: " + row); 
      }
    }
    else {
      setHasErrors(); 
      System.err.println("Error: Undefined variable '" + name + "' on line: " + row); 
    }
  }
  
  // public void visit( Type exp) {
  //   // if(exp.type == Type.INT) { 

  //   // } else {

  //   // }
  // }
  
  // Variable
  public void visit( Var exp) {
    if(exp instanceof IndexVar) {
      visit((IndexVar)exp);
    } else if(exp instanceof SimpleVar) {
      visit((SimpleVar)exp);
    }
  }

  // Variable Declaration
  public void visit( VarDec exp) {
    if(exp instanceof SimpleDec) {
      visit((SimpleDec)exp);
    } else if(exp instanceof ArrayDec) {
      visit((ArrayDec)exp);
    } 
  }

  // Variable Declaration List
  public void visit( VarDecList exp) {
    while(exp != null) {
      if(exp.head != null) {
        visit(exp.head);
      }
      exp = exp.tail;
    }
  }

  // Variable Expression
  public void visit( VarExp exp) {
    visit(exp.var);
  }

  // While Expression
  public void visit( WhileExp exp) {
    // Might have to change this
    visit(exp.test);
    visit(exp.block);
  }

  /* ------------------------- HELPER FUNCTIONS ------------------------- */

  private ArrayList<Symbol> addFunctionParams(VarDecList param_list) {
    ArrayList<Symbol> functionParams = new ArrayList<Symbol>();
    
    // Iterate through function params creating symbols for variables
    while (param_list != null) {
      if (param_list.head instanceof SimpleDec) {
        int var_type = param_list.head.type.type;
        String var_name = param_list.head.name;
        VarSymbol param = new VarSymbol(var_type, var_name, -1);
        functionParams.add(param);
      }
      else if (param_list.head instanceof ArrayDec) {
        int array_type = param_list.head.type.type;
        String array_name = param_list.head.name;
        ArraySymbol param = new ArraySymbol(array_type, array_name, -1);
        functionParams.add(param);
      }
      param_list = param_list.tail;
    }

    return functionParams;
  }

  private void setHasErrors() {
    this.hasErrors = true;
  }
}