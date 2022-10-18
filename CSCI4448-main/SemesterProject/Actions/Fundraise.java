package Actions;

import java.util.*;
import Items.*;
import Friends.*;

public class Fundraise implements actionType {
    @Override
    public void doAction(Friend f) {
        System.out.println("\n==> "+f.getName() + " has raised funds for the farm!");
        return;
    }
    @Override
    public String toString(){
        return "Fundraise";
    }
}

