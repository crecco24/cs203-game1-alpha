package game1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javalib.funworld.World;
import javalib.worldimages.*;
import java.awt.Color;
import javalib.colors.*;

public class Field extends World {

    int counter;
    int leftBound;
    int rightBound;
    int upperBound;
    int lowerBound;
    int ballsPerRow = 14;
    int ballsPerColumn = 5;
    //Ball[][] balls = new Ball[ballsPerRow][ballsPerColumn];
    ArrayList<Ball> balls = new ArrayList();
    int radius = 25;

    public Field(int l, int r, int u, int lo) {
        this.leftBound = l;
        this.rightBound = r;
        this.upperBound = u;
        this.lowerBound = lo;

        for (int i = 0; i < ballsPerRow; i++) {
            for (int j = 0; j < ballsPerColumn; j++) {
                int posX = (this.leftBound + radius) + ((2 * i) * radius) + 5;
                int posY = (this.lowerBound + radius) + ((2 * j) * radius);
                this.balls.add(new Ball(new Posn(posX, posY), randInt(1, 3), true));
            }
        }
        this.balls.add(new Ball(new Posn(this.leftBound + radius + 5, (this.upperBound - this.radius)),
                randInt(1, 3),
                false));
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    } 
    
    public WorldImage makeImage() {
        WorldImage background = new RectangleImage(new Posn(0,0), this.rightBound, this.upperBound, new White());
        WorldImage result = background;
        for ( int i = 0; i < balls.size(); i++ ) {
            Ball b = this.balls.get(i);
            result = new OverlayImages(result, b.makeImage());
        }
        return result;
    }

    public WorldImage makeImageB() {
        OverlayImages img = new OverlayImages(new DiskImage(this.balls.get(0).position, this.balls.get(0).radius, this.balls.get(0).randColor(this.balls.get(0).type)),
                new DiskImage(this.balls.get(1).position, this.balls.get(1).radius, this.balls.get(1).randColor(this.balls.get(1).type)));

        for (int i = 0; i < balls.size(); i++) {
            img = new OverlayImages(img, new DiskImage(this.balls.get(i).position, this.balls.get(i).radius, this.balls.get(i).randColor(this.balls.get(i).type)));
        }

        img = new OverlayImages(img, new DiskImage(this.balls.get(balls.size() - 1).position,
                this.balls.get(balls.size() - 1).radius,
                this.balls.get(balls.size() - 1).randColor(this.balls.get(balls.size() - 1).type)));
        return img;
    }

    public Field launch(Posn p, Ball b, Posn pp) {
        

        int deltaY = 10;

        Posn nuevoPosn = new Posn(p.x , p.y - deltaY);
        Ball nuevoBall = new Ball(nuevoPosn, b.type, true);
        System.out.println("x = " + nuevoPosn.x + " y = " + nuevoPosn.y);
        if (this.stopHuh(nuevoBall) != true) {
            this.balls.remove(b);
            return launch(nuevoPosn,
                    nuevoBall,
                    pp);
        } else {
            System.out.println("something");
            Field field = new Field(this.leftBound, this.rightBound, this.upperBound, this.lowerBound);
            field.balls = this.balls;
            field.balls.add(nuevoBall);
            
            Ball nuenBall = new Ball(new Posn(this.leftBound + radius + 5, (this.upperBound - this.radius)),
                randInt(1, 3),
                false);
            field.balls.add(nuenBall);
            return field;
        }
    }

    public Boolean stopHuh(Ball b) {
        for (int i = 0; i < balls.size()-1; i++) {
            if( b.touching(balls.get(i)) || b.touchingWall(this)){
                return true;
            }
            System.out.println("Checked ball " + i);
        }
        return false;
    }

    public Field breakChains(int counter) {
        for (int i = 0; i < balls.size(); i++) {
            if (this.balls.get(balls.size() - 1).touching(balls.get(i))) {
            }
        }
        return this;
    }

    public World onMousePressed(Posn mouse) {
        return launch(this.balls.get(balls.size() - 1).position,
                this.balls.get(balls.size() - 1),
                mouse);
    }
    
    public World onKeyEvent(String key){
        if(key.equals("left")){
            this.balls.get(balls.size()-1).position.x = this.balls.get(balls.size()-1).position.x - (2 * radius);
            return this; 
        } else if(key.equals("right")){
            this.balls.get(balls.size() -1).position.x = this.balls.get(balls.size()-1).position.x + (2 * radius);
            return this;
        } else if(key.equals("up")){
            return launch(this.balls.get(balls.size()-1).position,
                    this.balls.get(balls.size()-1),
                    new Posn(10,10));
        } else return this;
    } 
}
