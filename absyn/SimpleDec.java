package absyn;

public class SimpleDec extends VarDec {
    public SimpleDec(int row, int col, Type type, String name){
		this.row = row;
		this.col = col;
		this.type = type;
		this.name = name;
	}
	public void accept( AbsynVisitor visitor, int level ) {
		visitor.visit( this, level );
	}
}
