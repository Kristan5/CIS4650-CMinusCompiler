import absyn.*; 
import symbol.*; 

public class SymbolTable {
    private ArrayList<HashMap<Integer, Symbol>> symbolTable; 

    public SymbolTable() {
        symbolTable = new ArrayList<HashMap<Integer, Symbol>>();

    }
}