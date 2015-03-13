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
    int leftBound = 0;
    int rightBound = 720;
    int upperBound = 720;
    int lowerBound = 0;
    int ballsPerRow = 14;
    int ballsPerColumn = 5;
    ArrayList<Ball> balls = new ArrayList();
    int radius = 25;
    int ballLimit = 720 - (2 * (radius + 1));

    public Field() {

        for (int i = 0; i < ballsPerRow; i++) {
            for (int j = 0; j < ballsPerColumn; j++) {
                int posX = (this.leftBound + radius) + ((2 * i) * (radius - 1)) + 5;
                int posY = (this.lowerBound + radius) + ((2 * j) * (radius - 1));
                this.balls.add(new Ball(new Posn(posX, posY), randInt(1, 4)));
            }
        }
        this.balls.add(new Ball(new Posn(this.leftBound + radius + 5, (this.upperBound - this.radius)),
                randInt(1, 4)));
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public WorldImage makeImage() {
        WorldImage background = new RectangleImage(new Posn(0, 0), this.rightBound, this.upperBound, new White());
        WorldImage line = new LineImage(new Posn(leftBound, ballLimit),
                new Posn(rightBound, ballLimit),
                java.awt.Color.RED);
        WorldImage result = new OverlayImages(background, line);
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
        Ball nuevoBall = new Ball(nuevoPosn, b.type);
        this.balls.remove(b);
        if (this.stopHuh(nuevoBall) != true) {
            return launch(nuevoPosn,
                    nuevoBall,
                    pp);
        } else {
            Field field = new Field();
            field.balls = this.balls;
            field.balls.add(nuevoBall);

            counter++;

            field.counter = counter;

            breakChains(balls.get(balls.size() - 1));

            Ball nuenBall = new Ball(new Posn(this.leftBound + radius + 5, (this.upperBound - this.radius)),
                    randInt(1, 4));
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
        } else {
            return this;
        }
    }

    public ArrayList nextChain(ArrayList<Ball> toTest, ArrayList<Ball> tested) {

        ArrayList<Ball> testerList = (ArrayList<Ball>) balls.clone();

        if (toTest.isEmpty()) {
            return tested;
        } else {
            System.out.println("toTest is non-empty");
            if (!tested.isEmpty()) {
                System.out.println("Tested is non-empty");
                for (int h = 0; h < tested.size(); h++) {
                    testerList.remove(tested.get(h));
                    System.out.println("successfully removed " + h);
                }
            }
            for (int i = 0; i < toTest.size(); i++) {
                for (int j = 0; j < testerList.size() - 1; j++) {
                    if (toTest.get(i).touching(testerList.get(j))) {
                        System.out.println("Successfully tested " + i + "against index " + j);
                        if (toTest.get(i).equalType(testerList.get(j))) {
                            System.out.println("Same type adding to toTest");
                            toTest.add(testerList.get(j));
                            testerList.remove(testerList.get(j));
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
            Ball nuevoBall = new Ball(new Posn(nuevoX, refBall.position.y), refBall.type);
            balls.remove(refBall);
            balls.add(nuevoBall);
            return this;
        } else if (key.equals("right")
                && refBall.position.x + (2 * radius) <= this.rightBound - radius) {
            int nuevoX = refBall.position.x + (2 * (radius - 1));
            Ball nuevoBall = new Ball(new Posn(nuevoX, refBall.position.y), refBall.type);
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

    public WorldImage lastImage(String s) {
        return new TextImage(new Posn((rightBound - leftBound) / 2, (upperBound - lowerBound) / 2),
                s,
                30,
                java.awt.Color.BLACK);
    }

    public WorldEnd worldEnds() {
        if (balls.size() <= 1) {
            return new WorldEnd(true, this.lastImage("Game over! Score is " + counter));
        } else if (balls.get(balls.size() - 2).position.y + radius >= ballLimit) {
            return new WorldEnd(true, this.lastImage("Game over! You lose"));
        } else {
            return new WorldEnd(false, this.makeImage());
        }
    }
}
