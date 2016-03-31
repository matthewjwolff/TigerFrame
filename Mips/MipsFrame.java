package Mips;
import java.util.Hashtable;
import Symbol.Symbol;
import Temp.Temp;
import Temp.Label;
import Frame.Frame;
import Frame.Access;
import Frame.AccessList;

public class MipsFrame extends Frame {

  private int count = 0;
  public Frame newFrame(Symbol name, Util.BoolList formals) {
    Label label;
    if (name == null)
      label = new Label();
    else if (this.name != null)
      label = new Label(this.name + "." + name + "." + count++);
    else
      label = new Label(name);
    return new MipsFrame(label, formals);
  }

  public MipsFrame() {}
  private MipsFrame(Label n, Util.BoolList f) {
    name = n;
  }

  private static final int wordSize = 4;
  public int wordSize() { return wordSize; }

  public Access allocLocal(boolean escape) { 
      //if it does NOT escape, it can go InReg
      if(!escape) {
          //NOTE temp keeps track of the number of allocated things in its static variable
          return new InReg(new Temp());
      } else {
          //I guess offset?
          return new InFrame(wordSize);
      }
  }
  
  //rational rose says to put this. Dont' know what it does
  private void allocFormals() {
      
  }
}
