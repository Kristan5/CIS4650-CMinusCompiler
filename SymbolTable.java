import absyn.*; 
import symbol.*; 

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {
    private ArrayList<HashMap<Integer, Symbol>> symbolTable;
    // private boolean scope;

    final static int SPACES = 4;

    public SymbolTable() {
        symbolTable = new ArrayList<HashMap<Integer, Symbol>>();

    }

    // Make indentation
    private String indent(int level) {
        for( int i = 0; i < level * SPACES; i++ ) {
            System.out.print( " " );   
        }
    }

    // Get the type
    // private String getType(int type) {
    //     if(type == Type.VOID) {
    //         return "VOID";
    //     } else {
    //         return "INT";
    //     }
    // }

    // Add a new scope by making a new hashmap
    // Add it to the arraylist
    public void newScope() {
        symbolTable.add(new HashMap<Integer, Symbol>());
    }

    // Delete and exit the scope you're in
    public void delCurrScope() {
        int tableLength = symbolTable.size();

        // Make sure there's items in list
        if(tableLength > 0) {
            symbolTable.remove(tableLength - 1);
        }
    }

    // Add a symbol to the hashmap
    public void addSymbol(int id, Symbol symb) {
        int length = symbolTable.size();
        symbolTable.get(length - 1).put(id, symb);
    }

    // Retrieve the symbol from the scope its in
    public Symbol getSymbol(String symbol) {
        int length = symbolTable.size();
        int retVal = null;

        for(int i = length - 1; i >= 0; i--) {
            if(symbolTable(i).containsKey(symbol)) {
                retVal = symbolTable.get(i).get(symbol);
            }
        }
        
        return retVal;
    }


}