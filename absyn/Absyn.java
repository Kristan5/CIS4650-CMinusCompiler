package absyn;

abstract public class Absyn {
  public int row, col;

  abstract public void accept( AbsynVisitor visitor, int level );
  
  // final static int SPACES = 4;

  // private static void indent( int level ) {
  //   for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
  // }

  // // Expression List
  // public static void showTree( ExpList expList, int level ) {
  //   while( expList != null ) {
  //     // expList.head.accept( this, level );
  //     expList = expList.tail;
  //   } 
  // }

  // // ArrayDec
  // public static void showTree( ArrayDec expList, int level ) {
  //   // indent( level );
  //   // if (expList.size != null)
  //   System.out.println("ArrayDec: " + expList.name);
    
  // }

  // // Assign Expression
  // public static void showTree( AssignExp exp, int level ) {
  //   indent( level );
  //   System.out.println( "AssignExp:");
  //   level++;
  //   // NEED TO FIX THIS 'ShowTreeshowTreeor.java:24: error: cannot find symbol' on lhs.accept (Add accept to var)
  //   // exp.lhs.accept( this, level );
  //   // exp.rhs.accept( this, level );
  // }

  // // If Expression
  // public static void showTree( IfExp exp, int level ) {
  //   indent( level );
  //   System.out.println( "IfExp:" );
  //   level++;
  //   // exp.test.accept( this, level );
  //   // exp.thenpart.accept( this, level );
  //   // if (exp.elsepart != null )
  //     //  exp.elsepart.accept( this, level );
  // }
  
  // // Int Expression
  // public static void showTree( IntExp exp, int level ) {
  //   indent( level );
  //   System.out.println( "IntExp: " + exp.value ); 
  // }

  // // Operation Expression
  // public static void showTree( OpExp exp, int level ) {
  //   indent( level );
  //   System.out.print( "OpExp:" ); 
  //   switch( exp.op ) {
  //     case OpExp.PLUS:
  //       System.out.println( " + " );
  //       break;
  //     case OpExp.MINUS:
  //       System.out.println( " - " );
  //       break;
  //     case OpExp.TIMES:
  //       System.out.println( " * " );
  //       break;
  //     case OpExp.DIVIDE:
  //       System.out.println( " / " );
  //       break;
  //     case OpExp.EQ:
  //       System.out.println( " = " );
  //       break;
  //     case OpExp.LT:
  //       System.out.println( " < " );
  //       break;
  //     case OpExp.EQEQ:
  //       System.out.println( " == " );
  //       break;
  //     case OpExp.NOTEQ:
  //       System.out.println( " != " );
  //       break;
  //     case OpExp.LTE:
  //       System.out.println( " <= " );
  //       break;
  //     case OpExp.GTE:
  //       System.out.println( " >= " );
  //       break;
  //     default:
  //       System.out.println( "Unrecognized operator at line " + exp.row + " and column " + exp.col);
  //   }
  //   level++;
  //   // exp.left.accept( this, level );
  //   // exp.right.accept( this, level );
  // }

  // // Call Expression
  // public static void showTree( CallExp exp, int level ) {
  //   indent( level ); 
  //   System.out.println("CallExp: " + exp.function);
  //   level ++; 
  //   // if (exp.args != null)
  //     // exp.args.accept(this, level);
  // }

  // // Declaration List Expression
  // public static void showTree( DecList decList, int level) {
  //   System.out.println("CALLS SHOW TREE");
  //   // while(decList != null) {
  //   //   if(decList.head != null){
  //   //     decList.head.accept(this, level); 
  //   //   }
  //   //   decList = decList.tail;
  //   // }
  // }
  
  // // Declaration
  // public static void showTree( Dec decl, int level) {
  //   // indent(level);
  //   System.out.println("Declaration: " + decl);
  //   // level++;
  //   // decl.lhs.accept(this, level);
  //   // decl.rhs.accept(this, level);
  // }

  // // Expression
  // public static void showTree( Exp exp, int level ) {
  //   System.out.println("Exp: " + exp);
  // }

  // // Function Expression
  // public static void showTree( FunctionDec exp, int level ) {
  //   System.out.println("FunctionDec: " + exp.function);
  // }

  // // Index Variable
  // public static void showTree( IndexVar exp, int level ) {
  //   indent(level);
  //   System.out.println("Index var: " + exp.name);
  //   level++;
  //   // exp.index.accept(this, level);
  // }
  
  // // Nil Expression
  // public static void showTree( NilExp exp, int level ) {
  //   indent( level );
  //   System.out.println( "NilExp" ); 
  // }

  // // Return Expression
  // public static void showTree( ReturnExp exp, int level ) {
  //   indent( level );
  //   System.out.println("ReturnExp: ");
  //   level ++; 
  //   // if (exp.test != null)
  //     // exp.test.accept(this, level);
  // }

  // // Simple Declaration
  // public static void showTree( SimpleDec exp, int level ) {
  //   System.out.println("SimpleDec: " + exp);
  // }
  
  // // Simple Variable
  // public static void showTree( SimpleVar exp, int level ) {
  //   indent( level );
  //   System.out.println( "SimpleVar: " + exp.name ); 
  // }
  
  // public static void showTree( Type exp, int level ) {
  //   System.out.println("TYPE: ");
    
  //   if (exp.type == Type.INT)
  //     System.out.println("Type: INTEGER");
  //   else 
  //     System.out.println("Type: VOID");
  // }
  

  // // Variable
  // public static void showTree( Var exp, int level ) {
  //   System.out.println("Var: " + exp);
  // }

  // // Variable Declaration
  // public static void showTree( VarDec exp, int level ) {
  //   System.out.println("VarDec: " + exp);

  // }

  // // Variable Declaration List
  // public static void showTree( VarDecList exp, int level ) {
  //   System.out.println("VarDecList: " + exp);

  // }

  // // Variable Expression
  // public static void showTree( VarExp exp, int level ) {
  //   System.out.println("VarExp: " + exp);

  // }

  // // While Expression
  // public static void showTree( WhileExp exp, int level ) {
  //   System.out.println("WhileExp: " + exp);

  // }
}
