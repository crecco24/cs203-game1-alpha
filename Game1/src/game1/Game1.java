package game1;

import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javalib.worldimages.Posn;

public class Game1 {

    public static void main(String[] args) {
        Field field = new Field(0, 720, 720, 0);
        field.bigBang(field.rightBound, field.upperBound, 1);
        Boolean bool = field.balls.get(0).touching(field.balls.get(1));
        Boolean bool1 = field.balls.get(0).touching(field.balls.get(14));
        Boolean bool2 = field.balls.get(0).touching(field.balls.get(5));
        System.out.println("Are they touching? " + bool);
        System.out.println("Are they touching? " + bool1);
        System.out.println("Are they touching? " + bool2);

    }

}
