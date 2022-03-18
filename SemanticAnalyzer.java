import absyn.*; 
import symbol.*; 

public class SemanticAnalyzer {
  private SymbolTable symbolTable; 


  public SemanticAnalyzer(boolean SHOW_SYM, DecList result) {
    // Will need to add SHOW_SYM boolean to SymbolTable params
    symbolTable = new SymbolTable(SHOW_SYM);
    // Start visiting declist
    visit(result);

      
  }
  
  /* 
  COPIED FROM SHOWTREEVISITOR,
  NEED TO GO THROUGH THIS AND MODIFY METHODS 
  */
  
  
  // Expression List
  public void visit( ExpList expList) {
    // while( expList != null ) {
    //   expList.head.accept( this);
    //   expList = expList.tail;
    // } 
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
    // visit(exp.lhs);
    // visit(exp.rhs);
  }

  // If Expression
  public void visit( IfExp exp) {

    // exp.test.accept( this);
    // exp.thenpart.accept( this);
    // if (exp.elsepart != null ){
    //   exp.elsepart.accept( this);
    // }
  }
  
  // Int Expression
  public void visit( IntExp exp) {


  }

  // Operation Expression
  public void visit( OpExp exp) {
    

    switch( exp.op ) {
      case OpExp.PLUS:

        break;
      case OpExp.MINUS:

        break;
      case OpExp.TIMES:

        break;
      case OpExp.DIVIDE:

        break;
      case OpExp.EQ:

        break;
      case OpExp.LT:

        break;
      case OpExp.GT:

        break;
      case OpExp.EQEQ:

        break;
      case OpExp.NOTEQ:

        break;
      case OpExp.LTE:

        break;
      case OpExp.GTE:

        break;
    }
    
    // exp.left.accept( this);
    // exp.right.accept( this);
  }

  // Call Expression
  public void visit( CallExp exp) {
    

    visit(exp.args);
  }

  // Declaration List Expression
  public void visit( DecList decList) {
    // while(decList != null) {
    //   if(decList.head != null){
    //     // decList.head.accept(this); 
    //   }
    //   decList = decList.tail;
    // }
  }
  
  // Declaration
  public void visit( Dec decl) {
    // if(decl instanceof VarDec) {
    //   visit((VarDec)decl);
    // } else if(decl instanceof FunctionDec) {
    //   visit((FunctionDec)decl);
    // } else {

    // }
  }

  // Expression
  public void visit( Exp exp) {
    // if(exp instanceof ReturnExp) {
    //   visit((ReturnExp)exp);
    // } else if(exp instanceof CompoundExp) {
    //   visit((CompoundExp)exp);
    // } else if(exp instanceof WhileExp) {
    //   visit((WhileExp)exp);
    // } else if(exp instanceof IfExp) {
    //   visit((IfExp)exp);
    // } else if(exp instanceof AssignExp) {
    //   visit((AssignExp)exp);
    // } else if(exp instanceof OpExp) {
    //   visit((OpExp)exp);
    // } else if(exp instanceof CallExp) {
    //   visit((CallExp)exp);
    // } else if(exp instanceof IntExp) {
    //   visit((IntExp)exp);
    // } else if(exp instanceof VarExp) {
    //   visit((VarExp)exp);
    // } else {
      

    // }
  }

  // Function Expression
  public void visit( FunctionDec exp) {
    // visit(exp.type);    
    // visit(exp.param_list);
    // visit(exp.test);
  }

  // Index Variable
  public void visit( IndexVar exp) {
  
    // visit(exp.index);
  }
  
  // Nil Expression
  public void visit( NilExp exp) {
  
  }

  // Return Expression
  public void visit( ReturnExp exp) {

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
  
  public void visit( Type exp) {
    // if(exp.type == Type.INT) { 

    // } else {

    // }
  }
  
  // Variable
  public void visit( Var exp) {
    // if(exp instanceof IndexVar) {
    //   visit((IndexVar)exp);
    // } else if(exp instanceof SimpleVar) {
    //   visit((SimpleVar)exp);
    // } else {
    //   
    // }
  }

  // Variable Declaration
  public void visit( VarDec exp) {
    // if(exp instanceof SimpleDec) {
    //   visit((SimpleDec)exp);
    // } else if(exp instanceof ArrayDec) {
    //   visit((ArrayDec)exp);
    // } else {
    //   

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
    // System.out.println("WhileExp: ");
    // visit(exp.test);
    // visit(exp.block);
  }

  //Compound Expression
  public void visit( CompoundExp exp) {

    // visit(exp.decList);
    // visit(exp.expList);
  }
}