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
    ArrayList<Ball> balls = new ArrayList();
    int radius = 25;

    public Field(int l, int r, int u, int lo) {
        this.leftBound = l;
        this.rightBound = r;
        this.upperBound = u;
        this.lowerBound = lo;

        for (int i = 0; i < ballsPerRow; i++) {
            for (int j = 0; j < ballsPerColumn; j++) {
                int posX = (this.leftBound + radius) + ((2 * i) * (radius - 1)) + 5;
                int posY = (this.lowerBound + radius) + ((2 * j) * (radius - 1));
                this.balls.add(new Ball(new Posn(posX, posY), randInt(1, 4), true));
            }
        }
        this.balls.add(new Ball(new Posn(this.leftBound + radius + 5, (this.upperBound - this.radius)),
                randInt(1, 4),
                false));
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public WorldImage makeImage() {
        WorldImage background = new RectangleImage(new Posn(0, 0), this.rightBound, this.upperBound, new White());
        WorldImage result = background;
        for (int i = 0; i < balls.size(); i++) {
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

        int deltaY = 1;

        Posn nuevoPosn = new Posn(p.x, p.y - deltaY);
        Ball nuevoBall = new Ball(nuevoPosn, b.type, true);
        this.balls.remove(b);
        if (this.stopHuh(nuevoBall) != true) {
            return launch(nuevoPosn,
                    nuevoBall,
                    pp);
        } else {
            Field field = new Field(this.leftBound, this.rightBound, this.upperBound, this.lowerBound);
            field.balls = this.balls;
            field.balls.add(nuevoBall);

            breakChains(balls.get(balls.size() - 1));

            Ball nuenBall = new Ball(new Posn(this.leftBound + radius + 5, (this.upperBound - this.radius)),
                    randInt(1, 4),
                    false);
            field.balls.add(nuenBall);
            return field;
        }
    }

    public Boolean stopHuh(Ball b) {
        for (int i = 0; i < balls.size(); i++) {
            if (b.touching(balls.get(i)) || b.touchingWall(this)) {
                return true;
            }
        }
        return false;
    }

    public Field breakChains(Ball b) {
        ArrayList<Ball> toTest = new ArrayList();
        toTest.add(b);
        ArrayList<Ball> tested = new ArrayList();

        ArrayList<Ball> nextChain = nextChain(toTest, tested);

        if (!nextChain.isEmpty() && nextChain.size() >= 3) {
            for (int i = 0; i < nextChain.size(); i++) {
                System.out.println("Removed indexes " + i);
                balls.remove(nextChain.get(i));
            }
            return this;
        } else return this;
    }

    public ArrayList nextChain(ArrayList<Ball> toTest, ArrayList<Ball> tested) {
        if (toTest.isEmpty()) {
            return tested;
        } else {
            System.out.println("toTest is non-empty");
            if (!tested.isEmpty()) {
                System.out.println("Tested is non-empty");
                for (int h = 0; h < tested.size(); h++) {
                    balls.remove(tested.get(h));
                    System.out.println("successfully removed " + h);
                }
            }
            for (int i = 0; i < toTest.size(); i++) {
                for (int j = 0; j < balls.size() - 1; j++) {
                    if (toTest.get(i).touching(balls.get(j))) {
                        System.out.println("Successfully tested " + i + "against index " + j);
                        if (toTest.get(i).equalType(balls.get(j))) {
                            System.out.println("Same type adding to toTest");
                            toTest.add(balls.get(j));
                            balls.remove(balls.get(j));
                        }
                    }
                }
                tested.add(toTest.get(i));
                toTest.remove(i);
            }
            System.out.println("Recursive case");
            return nextChain(toTest, tested);
        }
    }

    public World onMousePressed(Posn mouse) {
        return launch(this.balls.get(balls.size() - 1).position,
                this.balls.get(balls.size() - 1),
                mouse);
    }

    public World onKeyEvent(String key) {
        Ball refBall = balls.get(balls.size() - 1);
        if (key.equals("left")
                && refBall.position.x - (2 * radius) >= this.leftBound + radius) {
            int nuevoX = refBall.position.x - (2 * (radius - 1));
            Ball nuevoBall = new Ball(new Posn(nuevoX, refBall.position.y), refBall.type, true);
            balls.remove(refBall);
            balls.add(nuevoBall);
            return this;
        } else if (key.equals("right")
                && refBall.position.x + (2 * radius) <= this.rightBound - radius) {
            int nuevoX = refBall.position.x + (2 * (radius - 1));
            Ball nuevoBall = new Ball(new Posn(nuevoX, refBall.position.y), refBall.type, true);
            balls.remove(refBall);
            balls.add(nuevoBall);
            return this;
        } else if (key.equals("up")) {
            return launch(this.balls.get(balls.size() - 1).position,
                    this.balls.get(balls.size() - 1),
                    new Posn(10, 10));
        } else {
            return this;
        }
    }
}
