package game1;

import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javalib.worldimages.Posn;

public class Game1 {

    public static void main(String[] args) {
        Field field = new Field();
        field.bigBang(field.rightBound, field.upperBound, 1);
        System.out.println("UpperBound is " + field.upperBound);
        System.out.println("Ball limit is " + field.ballLimit);
    }

}
