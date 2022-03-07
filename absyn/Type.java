package absyn;

public class Type extends Absyn {
    public static int VOID = 0; 
    public static int INT = 1; 
    
    public int pos;
    public int type; 

    public Type(int type, int pos) {
        this.type = type;
        this.pos = pos;
    }

    public void accept( AbsynVisitor visitor, int level ) {
		visitor.visit( this, level );
	}
}
