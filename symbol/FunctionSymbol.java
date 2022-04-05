package symbol; 
import java.util.ArrayList;

public class FunctionSymbol extends Symbol {
  public ArrayList<Symbol> params;
  public int address;

  public FunctionSymbol(int type, String id, ArrayList<Symbol> params) {
    this.type = type;
    this.id = id;
    this.params = params;
  }

  public FunctionSymbol(int type, String id, ArrayList<Symbol> params, int address) {
    this.type = type;
    this.id = id;
    this.params = params;
    this.address = address;
  }
}