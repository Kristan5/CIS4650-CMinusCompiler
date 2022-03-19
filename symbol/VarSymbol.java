package symbol; 

public class VarSymbol extends Symbol {
  public VarSymbol() {

  }

  public VarSymbol(int type, String id) {
    this.type = type;
    this.id = id;
  }
}