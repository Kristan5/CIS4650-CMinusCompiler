package absyn;

public class Var extends Absyn{

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
