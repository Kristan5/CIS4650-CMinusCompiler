package absyn;

public class WhileExp extends Exp {
  public Exp test;
  public Exp block;

  public WhileExp( int row, int col, Exp test, Exp block) {
    this.row = row;
    this.col = col;
    this.test = test;
    this.block = block;
  }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
