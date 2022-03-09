import absyn.*;

public class ShowTreeVisitor implements AbsynVisitor {

  final static int SPACES = 4;

  private void indent( int level ) {
    for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
  }

  // Expression List
  public void visit( ExpList expList, int level ) {
    while( expList != null ) {
      expList.head.accept( this, level );
      expList = expList.tail;
    } 
  }

  // ArrayDec
  public void visit( ArrayDec expList, int level ) {
    indent( level );
    System.out.println("ArrDec: ");
    level += level;
    visit(expList.type, level);
    indent( level );
    System.out.println("Array Name: " + expList.name);
    if(expList.size != null) {
      visit(expList.size, level);
    }
  }

  // Assign Expression
  public void visit( AssignExp exp, int level ) {
    indent(level);
    System.out.println("AssignExp: ");
    level += level;
    visit(exp.lhs, level);
    level += level;
    indent(level);
    System.out.println(" = ");
    visit(exp.rhs, level);
  }

  // If Expression
  public void visit( IfExp exp, int level ) {
    indent( level );
    System.out.println( "IfExp:" );
    level++;
    exp.test.accept( this, level );
    exp.thenpart.accept( this, level );
    if (exp.elsepart != null ){
      exp.elsepart.accept( this, level );
    }
  }
  
  // Int Expression
  public void visit( IntExp exp, int level ) {
    indent( level );
    System.out.println( "IntExp: "); 
    level += level;
    indent( level );
    System.out.println(exp.value);
  }

  // Operation Expression
  public void visit( OpExp exp, int level ) {
    indent( level );
    System.out.print( "OpExp:" ); 
    switch( exp.op ) {
      case OpExp.PLUS:
        System.out.println( " + " );
        break;
      case OpExp.MINUS:
        System.out.println( " - " );
        break;
      case OpExp.TIMES:
        System.out.println( " * " );
        break;
      case OpExp.DIVIDE:
        System.out.println( " / " );
        break;
      case OpExp.EQ:
        System.out.println( " = " );
        break;
      case OpExp.LT:
        System.out.println( " < " );
        break;
      case OpExp.EQEQ:
        System.out.println( " == " );
        break;
      case OpExp.NOTEQ:
        System.out.println( " != " );
        break;
      case OpExp.LTE:
        System.out.println( " <= " );
        break;
      case OpExp.GTE:
        System.out.println( " >= " );
        break;
      default:
        System.out.println( "Unrecognized operator at line " + exp.row + " and column " + exp.col);
    }
    level++;
    exp.left.accept( this, level );
    exp.right.accept( this, level );
  }

  // Call Expression
  public void visit( CallExp exp, int level ) {
    indent( level );
    System.out.println("CallExp: ");
    level += level;
    indent( level );
    System.out.println(exp.function);
    visit(exp.args, level);
  }

  // Declaration List Expression
  public void visit( DecList decList, int level) {
    System.out.println("SHOWSTREEVISITOR");
    while(decList != null) {
      if(decList.head != null){
        decList.head.accept(this, level); 
      }
      decList = decList.tail;
    }
  }
  
  // Declaration
  public void visit( Dec decl, int level) {
    if(decl instanceof VarDec) {
      visit((VarDec)decl, level);
    } else if(decl instanceof FunctionDec) {
      visit((FunctionDec)decl, level);
    } else {
      indent(level);
      System.out.println("Illegal Expression. Row: " + decl.row + " Col: " + decl.col);
    }
  }

  // Expression
  public void visit( Exp exp, int level ) {
    if(exp instanceof ReturnExp) {
      visit((ReturnExp)exp, level);
    } else if(exp instanceof CompoundExp) {
      visit((CompoundExp)exp, level);
    } else if(exp instanceof WhileExp) {
      visit((WhileExp)exp, level);
    } else if(exp instanceof IfExp) {
      visit((IfExp)exp, level);
    } else if(exp instanceof AssignExp) {
      visit((AssignExp)exp, level);
    } else if(exp instanceof OpExp) {
      visit((OpExp)exp, level);
    } else if(exp instanceof CallExp) {
      visit((CallExp)exp, level);
    } else if(exp instanceof IntExp) {
      visit((IntExp)exp, level);
    } else if(exp instanceof VarExp) {
      visit((VarExp)exp, level);
    } else {
      indent(level);
      System.out.println("Illegal expression. Row: " + exp.row + " Col: " + exp.col);
    }
  }

  // Function Expression
  public void visit( FunctionDec exp, int level ) {
    indent( level );
    System.out.println("FunctionDec: ");
    level += level;
    visit(exp.type, level);
    indent( level );
    System.out.println("Function: " + exp.function);
    visit(exp.param_list, level);
    visit(exp.test, level);    /******* COME BACK TO THIS BOYO */
  }

  // Index Variable
  public void visit( IndexVar exp, int level ) {
    indent(level);
    System.out.println("Index var: " + exp.name);
    level++;
    exp.index.accept(this, level);
  }
  
  // Nil Expression
  public void visit( NilExp exp, int level ) {
    indent( level );
    System.out.println( "NilExp" ); 
  }

  // Return Expression
  public void visit( ReturnExp exp, int level ) {
    indent( level );
    System.out.println("ReturnExp: ");
    level ++; 
    if (exp.test != null)
      exp.test.accept(this, level);
  }

  // Simple Declaration
  public void visit( SimpleDec exp, int level ) {
    indent(level);
    System.out.println("SimpleDec: ");
    indent(level);
    visit(exp.type, level);
    level += level;
    indent(level);
    System.out.println("SimpleDec name: " + exp.name);
  }
  
  // Simple Variable
  public void visit( SimpleVar exp, int level ) {
    indent( level );
    System.out.println( "SimpleVar: ");
    level += level;
    indent( level );
    System.out.println("SimpleVar name: " + exp.name);
  }
  
  public void visit( Type exp, int level ) {
    if (exp.type == Type.INT)
      System.out.println("Type: INTEGER");
    else 
      System.out.println("Type: VOID");
  }
  

  // Variable
  public void visit( Var exp, int level ) {
    System.out.println("Var: " + exp);
  }

  // Variable Declaration
  public void visit( VarDec exp, int level ) {
    System.out.println("VARIABLE DEC");
    if(exp instanceof SimpleDec) {
      visit((SimpleDec)exp, level);
    } else if(exp instanceof ArrayDec) {
      visit((ArrayDec)exp, level);
    } else {
      indent(level);
      System.out.println("Illegal expression. Row: " + exp.row + " Col: " + exp.col);
    }
  }

  // Variable Declaration List
  public void visit( VarDecList exp, int level ) {
    System.out.println("VarDecList: " + exp);

  }

  // Variable Expression
  public void visit( VarExp exp, int level ) {
    System.out.println("VarExp: " + exp);

  }

  // While Expression
  public void visit( WhileExp exp, int level ) {
    System.out.println("WhileExp: " + exp);

  }
}
