package absyn;

public class IndexVar extends Var {
    public String index;
    public String name;

    public IndexVar(int row, ind col, String name, Exp index) {
        this.row = row;
        this.col = col;
        this.name = name;
        this.index = index;
    }

    public void accept(AbsynVisitor visitor, int level) {
        visitor.visit(this, level);
    }
}
