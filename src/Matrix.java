import java.util.*;

public class Matrix {
    private final int size;
    private int[][] matrix;
    private int failureCount = 0;

    public Matrix(int size) {
        this.size = size;
        GenerateMatrix();
    }

    public int[][] GetMatrix() {
        return matrix;
    }

    public void GenerateMatrix() {
        matrix = new int[size][size];
        FillMatrix();
    }

    private void ClearMatrix() {
        matrix = new int[size][size];
    }

    private void FillMatrix() {
        var solved = false;
        while(!solved) {
            //fill box 1
            FillIndependentBox(0,0);
            //fill box 5
            FillIndependentBox(3,3);
            //fill box 9
            FillIndependentBox(6,6);
            //Fill in the rest
            solved = FillRemaining();
        }
    }


    private boolean FillRemaining() {
        //Fill remaining cells
        for(int column = 0; column < size; column++) {
            for(int row = 0; row < size; row++) {
                if(matrix[row][column] == 0) {
                    var possibleNumbers = GetPossibleNumbersForCell(row, column);
                    if(possibleNumbers.isEmpty()) {
                        failureCount++;
                        ClearMatrix();
                        return false;
                    }
                    else if(possibleNumbers.size() == 1) {
                        matrix[row][column] = possibleNumbers.get(0);
                    }
                    else {
                        //there are multiple possibilities, randomize it
                        var randomNumberIndex = new Random().nextInt(possibleNumbers.size());
                        matrix[row][column] = possibleNumbers.get(randomNumberIndex);
                    }
                }
            }
        }
        System.out.println("It only took me " + failureCount + " to create this for you!");
        return true;
    }

    private void FillIndependentBox(int row, int column) {
        if (row < 3) {
            row = 0;
        }
        else if (row < 6) {
            row = 3;
        }
        else {
            row = 6;
        }
        if (column < 3) {
            column = 0;
        }
        else if (column < 6) {
            column = 3;
        }
        else {
            column = 6;
        }
        ArrayList<Integer> possibleNumbers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));

        for(int c = column; c < column+3; c++) {
            for(int r = row; r < row+3; r++) {
                var random = new Random();
                var numberIndex = random.nextInt(possibleNumbers.size());
                matrix[r][c] = (int) possibleNumbers.toArray()[numberIndex];
                possibleNumbers.remove(numberIndex);
            }
        }
    }

    private ArrayList<Integer> GetPossibleNumbersForCell(int row, int column) {
        var possibleNumbers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        //check column
        for(int i = 0; i < size; i++) {
            if(matrix[row][i] != 0) {
                var index = possibleNumbers.indexOf(matrix[row][i]);
                if(index >= 0) {
                    possibleNumbers.remove(index);
                }
            }
        }
        //check row
        for(int j = 0; j < size; j++) {
            if(matrix[j][column] != 0) {
                var index = possibleNumbers.indexOf(matrix[j][column]);
                if(index >= 0) {
                    possibleNumbers.remove(index);
                }
            }
        }
        //check box
        if (row < 3) {
            row = 0;
        }
        else if (row < 6) {
            row = 3;
        }
        else {
            row = 6;
        }
        if (column < 3) {
            column = 0;
        }
        else if (column < 6) {
            column = 3;
        }
        else {
            column = 6;
        }
        for(int c = column; c < column+3; c++) {
            for(int r = row; r < row+3; r++) {
                if(matrix[r][column] != 0) {
                    var index = possibleNumbers.indexOf(matrix[r][column]);
                    if(index >= 0) {
                        possibleNumbers.remove(index);
                    }
                }
            }
        }
        return possibleNumbers;
    }


    public boolean Equals(Matrix compareMatrix) {
        if(size != compareMatrix.size) {
            return false;
        }
        for(int column = 0; column < size; column++) {
            for(int row = 0; row < size; row++) {
                if(matrix[column][row] != compareMatrix.matrix[column][row]) {
                    return false;
                }
            }
        }
        return true;
    }
}
