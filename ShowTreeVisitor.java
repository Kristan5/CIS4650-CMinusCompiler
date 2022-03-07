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

  // Assign Expression
  public void visit( AssignExp exp, int level ) {
    indent( level );
    System.out.println( "AssignExp:" );
    level++;
    exp.lhs.accept( this, level );
    exp.rhs.accept( this, level );
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
      case OpExp.OVER:
        System.out.println( " / " );
        break;
      case OpExp.EQ:
        System.out.println( " = " );
        break;
      case OpExp.LT:
        System.out.println( " < " );
        break;
      case OpExp.GT:
        System.out.println( " > " );
        break;
      default:
        System.out.println( "Unrecognized operator at line " + exp.row + " and column " + exp.col);
    }
    level++;
    exp.left.accept( this, level );
    exp.right.accept( this, level );
  }

  public void visit( CallExp exp, int level ) {

  }

  // Declaration List Expression
  // public void visit ( DecList decList, int level) {
  //   while(decList != null) {
  //     if(decList.head != null){
  //       decList.head.accept(this, level); 
  //     }
  //     decList = decList.tail;
  //   }
  // }
  
  // Declaration
  // public void visit ( Dec decl, int level) {
  //   indent(level);
  //   System.out.println("Declaration: " + decl);
  //   level++;
  //   decl.lhs.accept(this, level);
  //   decl.rhs.accept(this, level);
  // }

  public void visit( Exp exp, int level ) {

  }

  public void visit( FunctionExp exp, int level ) {

  }

  // Index Variable
  public void visit( IndexVar exp, int level ) {
    indent(level);
    System.out.println("Index var: " + exp.name);
    level++;
    exp.index.accept(this, level);
  }
  
  public void visit( NilExp exp, int level ) {

  }

  public void visit( ReturnExp exp, int level ) {

  }

  public void visit( SimpleDec exp, int level ) {

  }

  public void visit( SimpleVar exp, int level ) {

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

  public void visit( WhileExp exp, int level ) {

  }
}
