
package game1;

import javalib.worldimages.Posn;

public interface Object {
    
    Posn getPosn();
    
    Boolean inPlay();
    
    int ballType();
    
    Boolean equalType(Ball b);
    
    Boolean touching(Ball b);
    
    Ball move(Posn p);
    
}
