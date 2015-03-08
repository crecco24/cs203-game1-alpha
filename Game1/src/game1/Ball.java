package game1;

import java.awt.Color;
import static java.lang.Math.sqrt;
import java.util.List;
import java.util.Random;
import javalib.funworld.World;
import javalib.worldimages.*;

public class Ball extends World implements Object {

    Posn position;
    int type;
    boolean inPlay;
    int radius = 25;
    public DiskImage circ;

    Ball(Posn position, int type, Boolean inPlay) {
        this.position = position;
        this.type = type;
        this.inPlay = inPlay;
        this.circ = new DiskImage(position, this.radius, randColor(randInt()));
    }

    public Posn getPosn() {
        return this.position;
    }

    public Boolean inPlay() {
        return this.inPlay;
    }

    public int ballType() {
        return this.type;
    }

    public Boolean equalType(Ball b) {
        return this.type == b.type;
    }

    public Boolean touching(Ball b) {
        int d = this.position.x - b.position.x;
        int c = this.position.y - b.position.y;
        
        int dSquare = d * d;
        int cSquare = c * c;
        
        int a = (int) sqrt(dSquare + cSquare);
        
        if(a <= (2* this.radius)){
            return true;
        } else {
            return false;
        }
    }
    
    public Boolean touchingWall(Field f){
        if(this.position.x - radius <= f.leftBound){
            return true;
        } else if(this.position.x + radius >= f.rightBound){
            return true;
        } else if(this.position.y - radius <= f.lowerBound){
            return true;
        } else return false;
    }

    public Ball move(Posn p) {
        return new Ball(p, this.type, true);
    }
    

    public java.awt.Color randColor(int i) {
        if (i == 1) {
            return java.awt.Color.BLUE;
        }
        if (i == 2) {
            return java.awt.Color.RED;
        }
        if (i == 3) {
            return java.awt.Color.GREEN;
        }
        return java.awt.Color.MAGENTA;
    }

    public static int randInt() {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 1) + 1) + 1;

        return randomNum;
    }

    @Override
    public WorldImage makeImage() {
        return this.circ;
    }
    


}
