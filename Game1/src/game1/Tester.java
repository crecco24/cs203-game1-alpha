package game1;

import java.util.ArrayList;
import java.util.Random;
import javalib.funworld.World;

public class Tester {

    int moveUserBall = 0;

    public static Field randomMovement(Field field) {
        String key = randomKey();
        return (Field) field.onKeyEvent(key);
    }

    public static boolean testBallMovement(Field field) {
        String key = randomKey();
        int currentX = field.balls.get(field.balls.size() - 1).position.x;
        int currentY = field.balls.get(field.balls.size() - 1).position.y;

        Field newField = (Field) field.onKeyEvent(key);

        int newX = newField.balls.get(newField.balls.size() - 1).position.x;
        int newY = newField.balls.get(newField.balls.size() - 1).position.y;

        if (key.equals("right")) {
            if ((newX == currentX + (2 * (field.radius - 1)))
                    || (newX >= field.rightBound - (2 * field.radius - 1))) {
                return true;
            } else {
                return false;
            }
        }

        if (key.equals("left")) {
            if ((newX == currentX - (2 * (field.radius - 1)))
                    || (newX <= field.rightBound + (2 * field.radius - 1))) {
                return true;
            } else {
                return false;
            }
        }

        if (key.equals("up")) {
            if (newY <= currentY) {
                return true;
            } else {
                return false;
            }
        }

        if (key.equals("1")) {
            if (currentX == newX && currentY == newY) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String randomKey() {
        Random rand = new Random();
        int nextInt = Math.abs(rand.nextInt());
        if (nextInt % 4 == 0) {
            return "up";
        } else if (nextInt % 4 == 1) {
            return "right";
        } else if (nextInt % 4 == 2) {
            return "left";
        } else {
            return "1";
        }
    }

    public static boolean testScore(Field field) {
        int score = field.counter;
        if (score >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean testRemovedGroupSize(Field oldField, Field newField) {
        if (newField.balls.size() < oldField.balls.size()) {
            if (newField.balls.size() > oldField.balls.size() - 3) {
                return false;
            } else {
                return true;
            }
        } else if (newField.balls.size() > oldField.balls.size() + 1) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean testBelowBallLimit(Field field) {
        ArrayList<Ball> balls = field.balls;
        int ballLimit = field.ballLimit;

        for (int i = 0; i < balls.size() - 1; i++) {
            if (balls.get(i).position.y >= ballLimit) {
                return false;
            }
        }
        return true;
    }

    public static boolean testInFieldOfPlay(Field field) {
        ArrayList<Ball> balls = field.balls;
        int lowerBound = field.lowerBound;
        int upperBound = field.upperBound;
        int leftBound = field.leftBound;
        int rightBound = field.rightBound;
        int radius = field.radius;

        for (int i = 0; i < balls.size(); i++) {
            int xvalue = balls.get(i).position.x;
            int yvalue = balls.get(i).position.y;

            if (xvalue - radius < leftBound) {
                return false;
            }

            if (xvalue + radius > rightBound) {
                return false;
            }

            if (yvalue - radius < lowerBound) {
                return false;
            }

            if (yvalue + radius > upperBound) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Field field = randomMovement(new Field());

        boolean testBallMovementTester = true;

        for (int i = 0; i < 1000; i++) {
            testBallMovementTester = testBallMovement(field);

            if (testBallMovementTester == false) {
                System.out.println("User ball movement doesn't match control: Test failed.");
            }
        }
        System.out.println("Test for userball movement is successful?... " + testBallMovementTester + " .On 1000 tests");

        boolean testScoreTester = true;
        Field field1 = new Field();
        for (int i = 0; i < 1000; i++) {
            field1 = randomMovement(field1);
            testScoreTester = testScore(field1);

            if (testScoreTester == false) {
                System.out.println("Score is negative. Test on scoring system fails.");
            }
        }
        System.out.println("Test for a non-negative score is successful?... " + testScoreTester + " .On 1000 tests");

        boolean testBounds = true;
        Field field3 = new Field();
        for (int i = 0;
                i < 1000; i++) {
            field3 = randomMovement(field3);

            testBounds = testInFieldOfPlay(field3);
            if (testBounds == false) {
                System.out.println("A ball lies outside the bounds of the game. Test Fails");
            }
        }

        System.out.println(
                "Test to see if all balls lie within the field of play is successful?... " + testBounds + " . on 1000 tests");

        boolean testGroupSize = true;
        Field field4 = new Field();
        for (int i = 0; i < 1000; i++) {
            field4 = randomMovement(field4);
            Field field5 = randomMovement(field4);

            testGroupSize = testRemovedGroupSize(field4, field5);

            if (testGroupSize == false) {
                System.out.println("The difference of field ball sizes is illegal. Test fails");
            }
        }
        System.out.println("Test to see if ball size change is legal is successful?... " + testGroupSize + " . on 1000 tests");

        boolean testBallLimit = true;
        Field field2 = new Field();
        for (int i = 0; i < 1000; i++) {
            field2 = randomMovement(field2);

            testBallLimit = testBelowBallLimit(field2);
            if (testBallLimit == false) {
                System.out.println("A ball is above the ball limit. Test on limit has failed");
                field2 = new Field();
            }
        }
        System.out.println("Testing for all balls being below ballLimit is successful?... " + testBallLimit + " . on 1000 tests");
    }

}
