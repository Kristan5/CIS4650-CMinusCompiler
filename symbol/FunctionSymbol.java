package symbol; 
import java.util.ArrayList;

public class FunctionSymbol extends Symbol {
  
  public ArrayList<Symbol> params;
  
  public FunctionSymbol() {
    
  }

  public FunctionSymbol(int type, String id, ArrayList<Symbol> params) {
    this.params = params;
    this.id = id;
    this.type = type;
  }
}