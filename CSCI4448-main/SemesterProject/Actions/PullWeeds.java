
package Actions;

import java.util.*;
import Items.*;
import Friends.*;

public class PullWeeds implements actionType {
    
    @Override
    public void doAction(Friend f) {
        System.out.println("\n==> "+f.getName() + " has pulled weeds from the plot!");
        return;
    }
     @Override
     public String toString(){
         return "Pull Weeds";
     }
}
