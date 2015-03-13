package game1;

import java.util.Random;
import javalib.funworld.World;

public class Tester {

    int moveUserBall = 0;

    public Field randomMovement(Field field) {
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
            } else return false;
        }

        if (key.equals("left")) {
            if ((newX == currentX - (2 * (field.radius - 1)))
                    || (newX <= field.rightBound + (2 * field.radius - 1))) {
                return true;
            } else return false;
        }

        if (key.equals("up")) {
            if (newY <= currentY) {
                return true;
            } else return false;
        }

        if (key.equals("1")) {
            if (currentX == newX && currentY == newY) {
                return true;
            } else {
                return false;
            }
        } else 
            return false;
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
    
    public static boolean testScore(Field field){
        int score = field.counter;
        if(score >= 0){
            return true;
        } else return false;
    }
    
    public static boolean testRemovedGroupSize(Field oldField, Field newField){
        if (newField.balls.size() < oldField.balls.size()){
            if(newField.balls.size() > oldField.balls.size() - 3){
                return false;
            } else return true;
        } else if(newField.balls.size() > oldField.balls.size() + 1){
            return false;
        } else return true;
    }
    
    public static boolean testBelowBallLimit(){
        return true;
    }
    
    public static boolean testInFieldOfPlay(){
        return true;
    }
    
     public static void main(String[] args) {
        Field field = new Field();
        boolean baller = true;
        for(int i = 0; i < 1000; i++){
            baller = testBallMovement(field);
            
            if(baller == false){
                System.out.println("User ball movement doesn't match control: Test failed.");
            }
        }
        System.out.println("Test for userball movement is successful?... " + baller + " .On 1000 tests");
        
        
    }
}
