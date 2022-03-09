package absyn;

public class Type extends Absyn {
    public static int VOID = 0; 
    public static int INT = 1; 
    
    public int type; 

    public Type(int row, int col, int type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public void accept( AbsynVisitor visitor, int level ) {
		visitor.visit( this, level );
	}
}
