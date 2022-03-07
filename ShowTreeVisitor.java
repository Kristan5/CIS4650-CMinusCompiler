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
    // indent( level );
    // if (expList.size != null)
    //   System.out.println("ArrayDec: " + expList.);
    
  }

  // Assign Expression
  public void visit( AssignExp exp, int level ) {
    indent( level );
    System.out.println( "AssignExp:" );
    level++;
    // NEED TO FIX THIS 'ShowTreeVisitor.java:24: error: cannot find symbol' on lhs.accept (Add accept to var)
    // exp.lhs.accept( this, level );
    // exp.rhs.accept( this, level );
  }

  // If Expression
  public void visit( IfExp exp, int level ) {
    indent( level );
    System.out.println( "IfExp:" );
    level++;
    exp.test.accept( this, level );
    exp.thenpart.accept( this, level );
    if (exp.elsepart != null )
       exp.elsepart.accept( this, level );
  }
  
  // Int Expression
  public void visit( IntExp exp, int level ) {
    indent( level );
    System.out.println( "IntExp: " + exp.value ); 
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
    System.out.println("CallExp: " + exp.function);
    level ++; 
    if (exp.args != null)
      exp.args.accept(this, level);
  }

  // Declaration List Expression
  public void visit( DecList decList, int level) {
    while(decList != null) {
      if(decList.head != null){
        decList.head.accept(this, level); 
      }
      decList = decList.tail;
    }
  }
  
  // Declaration
  public void visit( Dec decl, int level) {
    // indent(level);
    // System.out.println("Declaration: " + decl);
    // level++;
    // decl.lhs.accept(this, level);
    // decl.rhs.accept(this, level);
  }

  // Expression
  public void visit( Exp exp, int level ) {

  }

  // Function Expression
  public void visit( FunctionDec exp, int level ) {

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

  }
  
  // Simple Variable
  public void visit( SimpleVar exp, int level ) {
    indent( level );
    System.out.println( "SimpleVar: " + exp.name ); 
  }
  
  public void visit( Type exp, int level ) {
    System.out.println("TYPE: ");
    
    if (exp.type == Type.INT)
      System.out.println("Type: INTEGER");
    else 
      System.out.println("Type: VOID");
  }
  

  // Variable
  public void visit( Var exp, int level ) {

  }

  // Variable Declaration
  public void visit( VarDec exp, int level ) {

  }

  // Variable Declaration List
  public void visit( VarDecList exp, int level ) {

  }

  // Variable Expression
  public void visit( VarExp exp, int level ) {

  }

  // While Expression
  public void visit( WhileExp exp, int level ) {

  }
}
