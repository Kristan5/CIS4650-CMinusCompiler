package absyn;

public class WhileExp extends Exp {
  public Exp test;
  public ExpList thenpart;
  public ExpList elsepart;

  public WhileExp( int row, int col, Exp test, ExpList thenpart, ExpList elsepart ) {
    this.row = row;
    this.col = col;
    this.test = test;
  }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
