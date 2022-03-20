package symbol; 

public class ArraySymbol extends Symbol {
  public int size;
  public int offset;

  public ArraySymbol(int type, String id, int size) {
    this.size = size;
    this.id = id;
    this.type = type;
  }
  
  public ArraySymbol(int type, String id, int size, int offset) {
    this.size = size;
    this.id = id;
    this.type = type;
    this.offset = offset;
  }
}