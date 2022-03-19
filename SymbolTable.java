import absyn.*; 
import symbol.*; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SymbolTable {
    private ArrayList<HashMap<String, Symbol>> symbolTable;
    private boolean SHOW_SYM;

    final static int SPACES = 4;

    public SymbolTable(boolean SHOW_SYM) {
        symbolTable = new ArrayList<HashMap<String, Symbol>>();
        this.SHOW_SYM = SHOW_SYM;
    }

    // Make indentation
    private void indent(int level) {
        for( int i = 0; i < level * SPACES; i++ ) {
            System.out.print(" ");   
        }
    }

    // Get the type
    private String getType(int type) {
        if(type == Type.VOID) {
            return "VOID";
        } else {
            return "INT";
        }
    }

    // Add a new scope by making a new hashmap
    // Add it to the arraylist
    public void newScope() {
        symbolTable.add(new HashMap<String, Symbol>());
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
    public void addSymbol(String id, Symbol symb) {
        int length = symbolTable.size();
        symbolTable.get(length - 1).put(id, symb);
    }

    // Retrieve the symbol from the scope its in
    public Symbol getSymbol(String symbol) {
        // TODO: Should this be symbolTable.size() -1; ???
        int length = symbolTable.size();

        for(int i = length - 1; i >= 0; i--) {
            if(symbolTable.get(i).containsKey(symbol)) {
                return symbolTable.get(i).get(symbol);
            }
        }
        
        return null; 
    }

    // Print the scope
    public void printScope(int level) {
        int length = symbolTable.size();

        for(String k : symbolTable.get(length - 1).keySet()) {
            Symbol symb = symbolTable.get(length - 1).get(k);

            if(symb instanceof ArraySymbol) {
                ArraySymbol arrSymb = (ArraySymbol)symb;
                indent(level);
                System.out.println("Array is " + getType(symb.type) + " " 
                    + k + "[" + arrSymb.size + "]");
            } else if(symb instanceof VarSymbol) {
                indent(level);
                System.out.println("Variable is " + getType(symb.type) + " " + k);
            } else if(symb instanceof FunctionSymbol) {
                indent(level);
                System.out.print("Function is " + getType(symb.type) + " " + k 
                    + " (");

                for(Symbol s : ((FunctionSymbol)symb).params) {
                    if(s instanceof ArraySymbol) {
                        System.out.print(getType(s.type) + "[]");
                    }
                    else if(s instanceof VarSymbol){
                        System.out.print(getType(s.type));
                    }
                    System.out.print(",");
                }
                System.out.println(")");
            }
        }
    }
}