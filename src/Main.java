import java.util.Random;
import java.util.Scanner;

/*
* create a sudoko game upon request from user
*/
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Creating sudoko...");
        var size = 9;
        var matrix = new Matrix(size);

        //print partial matrix to allow user to solve it
        for(int column = 0; column < size; column++) {
            for(int row = 0; row < size; row++) {
                //show roughly 1/4 of the numbers
                var showNumber = new Random().nextInt(4) == 0;
                if(showNumber) {
                    System.out.print("["+matrix.GetMatrix()[column][row]+"]");
                }
                else {
                    System.out.print("[ ]");
                }

            }
            System.out.println();
        }
        // Scanner definition
        Scanner scn = new Scanner(System.in);

        System.out.println("Would you like to see the answer?");
        String showAnswer = scn.next();
        //take input from user
        System.out.println();

        if(showAnswer.equalsIgnoreCase("yes")) {
            //print the full matrix
            for(int column = 0; column < size; column++) {
                for(int row = 0; row < size; row++) {
                    System.out.print("["+matrix.GetMatrix()[column][row]+"]");
                }
                System.out.println();
            }
        }
    }
}