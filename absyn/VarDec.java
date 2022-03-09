package absyn;

public abstract class VarDec extends Dec {
    public Type type; 
    public String name; 

    public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
}
}
