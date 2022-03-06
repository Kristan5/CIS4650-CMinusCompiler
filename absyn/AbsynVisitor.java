package absyn;

public interface AbsynVisitor {

    public void visit( AssignExp exp, int level );

    public void visit( DecList decList, int level);

}
