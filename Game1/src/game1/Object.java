
package game1;

import javalib.worldimages.Posn;

public interface Object {
    
    Posn getPosn();
    
    int ballType();
    
    Boolean equalType(Ball b);
    
    Boolean touching(Ball b);
    
    
}
