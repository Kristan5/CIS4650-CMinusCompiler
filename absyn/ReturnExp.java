package absyn;

public class ReturnExp extends Exp {
    public Exp test;
  
    public ReturnExp( int row, int col, Exp test ) {
      this.row = row;
      this.col = col;
      this.test = test;
    }
  
    public void accept( AbsynVisitor visitor, int level ) {
      visitor.visit( this, level );
    }
}
