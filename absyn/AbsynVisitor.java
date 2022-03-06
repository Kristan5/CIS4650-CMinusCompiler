package absyn;

public interface AbsynVisitor {

  // public void visit( AssignExp exp, int level );

  public void visit( CallExp exp, int level );

  public void visit ( DecList decList, int level);
  
  public void visit ( Dec decl, int level);

  // public void visit( ExpList exp, int level );
  
  public void visit( Exp exp, int level );

  public void visit( FunctionExp exp, int level );

  // public void visit( IfExp exp, int level );

  // public void visit( IntExp exp, int level );

  public void visit( IndexVar exp, int level );
  
  public void visit( NilExp exp, int level );

  // public void visit( OpExp exp, int level );

  public void visit( ReturnExp exp, int level );

  public void visit( SimpleDec exp, int level );

  public void visit( SimpleVar exp, int level );

  public void visit( Var exp, int level );
  
  public void visit( VarDec exp, int level );
  
  public void visit( VarDecList exp, int level );
  
  public void visit( VarExp exp, int level );

  public void visit( WhileExp exp, int level );

}
