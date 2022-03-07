package absyn;

public class SimpleVar extends Var{
    public String name; 
    public int row; 
    public int col; 

    public SimpleVar(int row, int col, String name) {
        this.row = row; 
        this.col = col; 
        this.name = name; 
    }
}
