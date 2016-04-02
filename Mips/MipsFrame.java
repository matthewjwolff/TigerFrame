package Mips;
import java.util.Hashtable;
import Symbol.Symbol;
import Temp.Temp;
import Temp.Label;
import Frame.Frame;
import Frame.Access;
import Frame.AccessList;

public class MipsFrame extends Frame {

  //number of locals allocated so far
  private int count = 0;
  //the offset of this frame for esacped local variables. does not affect formals
  private int offset = 0;
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
    //allocate the formal parameters in the frame
    //we start our offset at 0
    this.formals = allocFormals(0,f);
  }

  private static final int wordSize = 4;
  public int wordSize() { return wordSize; }

  @Override
  public Access allocLocal(boolean escape) { 
      //if it does NOT escape, it can go InReg
      if(escape) {
          offset -= wordSize;
          return new InFrame(offset);
      } else {
          //NOTE temp keeps track of the number of allocated things in its static variable.
          //we don't have to worry about it here.
          //temp represents WHICH register we're using.
          return new InReg(new Temp());
      }
  }
  
  //parameters: 0,4,8..
  //locals: -4,-8,-12
  
  //Implemented non-recursively, could possibly be done better recursively?
  //nvm has to be done recursively
  private AccessList allocFormals(int offset, Util.BoolList args) {
      if (args == null)
          return null;
      else {
          Access access;
          if(args.head) {
              access = new InFrame(offset);
              offset += wordSize;
          } else access = new InReg(new Temp());
          return new AccessList(access, allocFormals(offset, args.tail));
      }
  }
}
