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
        if (SHOW_SYM) {
            int level = symbolTable.size();
            indent(level - 1);
            System.out.println("Existing Scope: " + level);
            printScope(level); 
        }

        int tableLength = symbolTable.size();

        // Make sure there's items in list
        if(tableLength > 0) {
            symbolTable.remove(tableLength - 1);
        }
    }

    // Add a symbol to the hashmap
    public void addSymbol(String id, Symbol symb) {
        int length = symbolTable.size() -1;
        getScope(length).put(id, symb);
    }

    // Retrieve the symbol from the scope its in
    public Symbol getSymbol(String symbol) {
        int length = symbolTable.size() -1;

        for(int i = length; i >= 0; i--) {
            if(getScope(i).containsKey(symbol)) {
                return getScope(i).get(symbol);
            }
        }
        
        return null; 
    }

    // Get functions (on tree level 0) 
    public Symbol getFunction(String symbol) {
        Symbol result = null; 
        try {
            result = getScope(0).get(symbol);
        }
        catch (Exception e) {
            
        }
        return result;
    }

    public int getFunctionParamCount(String symbol) {
        FunctionSymbol function = (FunctionSymbol) getFunction(symbol);
        
        if (function != null) return function.params.size(); 

        return 0;
    }

    // check if a symbol is in the same scope as another symbol being called 
    public boolean isSameScope(String symbol) {
        int length = symbolTable.size() -1;
        return getScope(length).containsKey(symbol);
    }

    // Print the scope
    public void printScope(int level) {
        int length = symbolTable.size();
        ArrayList<String> result = new ArrayList<String>(); 

        for(String k : getScope(length - 1).keySet()) {
            Symbol symb = getScope(length - 1).get(k);

            if(symb instanceof ArraySymbol) {
                ArraySymbol arrSymb = (ArraySymbol)symb;
                indent(level);
                result.add("Array is " + getType(symb.type) + " " 
                    + k + "[" + arrSymb.size + "]");
            } else if(symb instanceof VarSymbol) {
                indent(level);
                result.add("Variable is " + getType(symb.type) + " " + k);
            } else if(symb instanceof FunctionSymbol) {
                indent(level);
                result.add("Function is " + getType(symb.type) + " " + k 
                    + " (");

                for(Symbol s : ((FunctionSymbol)symb).params) {
                    if(s instanceof ArraySymbol) {
                        result.add(getType(s.type) + "[]");
                    }
                    else if(s instanceof VarSymbol){
                        result.add(getType(s.type));
                    }
                    result.add(",");
                }
                result.add(")");
            }
        }
        for (int i = result.size(); i > 0; i--) {
            System.out.println(result.get(i));
        }
    }

    private HashMap<String, Symbol> getScope(int id) {
        return symbolTable.get(id);
    }
}