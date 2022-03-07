package absyn;

public class SimpleDec extends VarDec {
    public int pos;
    public SimpleDec(int pos, Type type, String name){
		this.pos = pos;
		this.type = type;
		this.name = name;
	}
	public void accept( AbsynVisitor visitor, int level ) {
		visitor.visit( this, level );
	}
}
