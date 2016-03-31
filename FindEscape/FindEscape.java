package FindEscape;

public class FindEscape {
  Symbol.Table escEnv = new Symbol.Table(); // escEnv maps Symbol to Escape

  public FindEscape(Absyn.Exp e) { traverseExp(0, e);  }

  void traverseVar(int depth, Absyn.Var v) {
  }

  void traverseExp(int depth, Absyn.Exp e) {
    //oh boy instanceof! My favorite!
    if (e instanceof Absyn.ArrayExp) {
      traverseExp(depth, (Absyn.ArrayExp) e);
    }
    else if (e instanceof Absyn.AssignExp) {
      traverseExp(depth, (Absyn.AssignExp) e);
    }
    else if (e instanceof Absyn.CallExp) {
      traverseExp(depth, (Absyn.CallExp) e);
    }
    else if (e instanceof Absyn.ForExp) {
      traverseExp(depth, (Absyn.ForExp) e);
    }
    else if (e instanceof Absyn.IfExp) {
      traverseExp(depth, (Absyn.IfExp) e);
    }
    else if (e instanceof Absyn.IntExp) {
      traverseExp(depth, (Absyn.IntExp) e);
    }
    else if (e instanceof Absyn.LetExp) {
      traverseExp(depth, (Absyn.LetExp) e);
    }
    else if (e instanceof Absyn.NilExp) {
       //kek
    }
    else if (e instanceof Absyn.OpExp) {
      traverseExp(depth, (Absyn.OpExp) e);
    }
    else if (e instanceof Absyn.RecordExp) {
      traverseExp(depth, (Absyn.RecordExp) e);
    }
    else if (e instanceof Absyn.SeqExp) {
      traverseExp(depth, (Absyn.SeqExp) e);
    }
    else if (e instanceof Absyn.StringExp) {
      traverseExp(depth, (Absyn.StringExp) e);
    }
    else if (e instanceof Absyn.VarExp) {
      traverseExp(depth, (Absyn.VarExp) e);
    }
    else if (e instanceof Absyn.WhileExp) {
      traverseExp(depth, (Absyn.WhileExp) e);
    }
    else return;
  }

  void traverseDec(int depth, Absyn.Dec d) {
  }

  //General format for these: recurse into Exp property
  void traverseExp(int depth, Absyn.ArrayExp e) {
    traverseExp(depth, e.size);
    traverseExp(depth, e.init);
  }

  void traverseExp(int depth, Absyn.AssignExp e) {
    traverseVar(depth, e.var);
    traverseExp(depth, e.exp);
  }

  void traverseExp(int depth, Absyn.CallExp e) {
    Absyn.ExpList iterator = e.args;
    while (iterator != null) {
      traverseExp(iterator.head);
      iterator = iterator.tail;
    }
  }

  void traverseExp(int depth, Absyn.ForExp e) {
    //nevermind there's no return value
    traverseExp(depth, e.var.init);
    //entering scope for For loop
    escEnv.beginScope();
    //add iterator to this scope
    escEnv.put(e.var.name, new VarEscape(depth, e.var));

    //Go through the for loop terminal and body
    traverseExp(depth, e.hi);
    //Inside the for loop we've gone down the rabbit hole more (right?)
    traverseExp(depth+1, e.body);
    escEnv.endScope();
  }

  void traverseExp(int depth, Absyn.IfExp e) {
    traverseExp(depth, e.test);
    traverseExp(depth, e.then);
    if(e.elseclause != null) 
      traverseExp(depth, e.elseclause);
  }

}
