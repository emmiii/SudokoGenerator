import java.util.Scanner;

/*
* create a sudoko game upon request from user
*/
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Creating sudoko...");
        var matrix = new Matrix(9);

        //print the matrix
        for(int column = 0; column < matrix.GetSize(); column++) {
            for(int row = 0; row < matrix.GetSize(); row++) {
                System.out.print("["+matrix.GetMatrix()[column][row]+"]");
            }
            System.out.println();
        }
    }
}