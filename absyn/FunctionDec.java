package absyn;

public class FunctionDec extends Dec {
  public Type type; 
  public String function; 
  public VarDecList param_list; 
  public Exp test; 

  public FunctionDec( int row, int col, Type type, String function, VarDecList param_list, Exp test ) {
    this.row = row;
    this.col = col;
    this.type = type;
    this.function = function; 
    this.param_list = param_list;
    this.test = test;
  }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
