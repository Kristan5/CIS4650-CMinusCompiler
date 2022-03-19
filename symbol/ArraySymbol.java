package symbol; 

public class ArraySymbol extends Symbol {
  public int size;

  public ArraySymbol() {

  }

  public ArraySymbol(int type, String id, int size) {
    this.size = size;
    this.id = id;
    this.type = type;
  }
}