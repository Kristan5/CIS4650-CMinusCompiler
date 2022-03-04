package absyn;

public class FunctionExp extends Exp {


    public FunctionExp( int row, int col, Exp test, ExpList thenpart, ExpList elsepart ) {
      this.row = row;
      this.col = col;
      this.test = test;
    }
  
    public void accept( AbsynVisitor visitor, int level ) {
      visitor.visit( this, level );
    }
}
