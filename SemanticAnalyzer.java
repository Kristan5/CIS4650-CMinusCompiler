import java.util.ArrayList;

import absyn.*; 
import symbol.*; 

// TODO: Add exitscope() method call to compound exp after symbol table implementation
public class SemanticAnalyzer {
  private SymbolTable symbolTable; 
  private boolean functionReturnType; 

  public SemanticAnalyzer(boolean SHOW_SYM, DecList result) {
    // Will need to add SHOW_SYM boolean to SymbolTable params
    symbolTable = new SymbolTable(SHOW_SYM);
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
    // symbolTable.newScope(); 
    
    while(decList != null) {
      if(decList.head != null){
        visit(decList.head);
      }
      decList = decList.tail;
    }
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
    // Might have to change this
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
    ArrayList<Symbol> functionParams = new ArrayList<Symbol>();
    VarDecList param_list = exp.param_list; 
    
    // Iterate through function params
    while (param_list != null) {
      if (param_list.head instanceof SimpleDec) {
        int type = param_list.head.type.type;
        String name = param_list.head.name;
        VarSymbol param = new VarSymbol(type, name, -1);
        functionParams.add(param);
      }
      else if (param_list.head instanceof ArrayDec) {
        int type = param_list.head.type.type;
        String name = param_list.head.name;
        ArraySymbol param = new ArraySymbol(type, name, -1);
        functionParams.add(param);
      }
    }




    // visit(exp.type);    
    // visit(exp.param_list);
    // visit(exp.test);
  }

  // Index Variable
  public void visit( IndexVar exp) {
  
    Symbol sym = symbolTable.getSymbol(exp.name);
    int row = exp.row + 1;

    if (sym != null) {
      if (!(sym instanceof ArraySymbol)) {
        System.err.println("Error: Line " + row + ": " + exp.name + " is not an array");
      }
    }
    else {
      System.err.println("");
    }

    visit(exp.index);

  }
  
  // // Nil Expression
  // public void visit( NilExp exp) {
  
  // }

  // Return Expression
  public void visit( ReturnExp exp) {
    // if () {

    // }


    // if (exp.test != null) {
    //   visit(exp.test);
    // }
  }

  // Simple Declaration
  public void visit( SimpleDec exp) {
    // visit(exp.type);
  }
  
  // Simple Variable
  public void visit( SimpleVar exp) {

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

  //Compound Expression
  public void visit( CompoundExp exp) {
    symbolTable.newScope();
    visit(exp.decList);
    visit(exp.expList);


    // Might have to change this:

    //symbolTable.exitScope(); 
  }
}