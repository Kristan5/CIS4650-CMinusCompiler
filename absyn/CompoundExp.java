package absyn;

public class CompoundExp extends Exp {
    public VarDecList decList; 
    public ExpList expList; 

    public CompoundExp(int row, int col, VarDecList decList, ExpList expList) {
        this.row = row;
        this.col = col;
        this.decList = decList; 
        this.expList = expList; 
    }

    public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
    }
}
