import absyn.*; 
import symbol.*; 

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {
    private ArrayList<HashMap<Integer, Symbol>> symbolTable; 

    public SymbolTable() {
        symbolTable = new ArrayList<HashMap<Integer, Symbol>>();

    }
}