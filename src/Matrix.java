import java.util.*;

public class Matrix {
    private int size;
    private int[][] matrix;

    public Matrix(int size) {
        this.size = size;
        GenerateMatrix();
    }

    public int GetSize() {
        return size;
    }

    public int[][] GetMatrix() {
        return matrix;
    }

    public void GenerateMatrix() {
        matrix = new int[size][size];
        FillMatrix();
    }

    public void ClearMatrix() { matrix = new int[size][size]; }

    private void FillMatrix() {
        var solved = false;
        while(!solved)
        {
            //fill box 1
            FillIndependentBox(0,0);
            //fill box 5
            FillIndependentBox(3,3);
            //fill box 9
            FillIndependentBox(6,6);
            //fill box 3
            //FillDependentBox(0, 6);
            //fill box 7
            //FillDependentBox(6, 0);
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
                        //something is wrong, start over!
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
        return true;
    }

    private void FillColumn(int column) {
        //note possible numbers for the column
        ArrayList<Integer> possibleNumbers = new ArrayList(Arrays.asList(1,2,3,4,5,6,7,8,9));
        while(!possibleNumbers.isEmpty()) {
            var emptyRows = new ArrayList<Integer>();
            //remove all existing numbers in the column
            for(int row = 0; row < size; row++) {
                if(matrix[row][column] != 0) {
                    var numberIndex = possibleNumbers.indexOf(matrix[row][column]);
                    if(numberIndex >= 0) {
                        possibleNumbers.remove(numberIndex);
                    }
                }
                else {
                    emptyRows.add(row);
                }
            }
            if(emptyRows.isEmpty())
            {
                //there are no empty rows in the column
                return;
            }

            //go through the row to check for ok numbers
            for(var emptyRow : emptyRows)
            {
                GetPossibleNumbersForCell(emptyRow, column);
            }

            //randomize an ok number to put in the cell
            //var numberIndex = new Random().nextInt(possibleNumbers.size());
            //var suggestedNumber = (int) possibleNumbers.toArray()[numberIndex];
            //check row
            //var addToMatrix = true;
            //for(int c = 0; c < size; c++) {
                //if(matrix[emptyRows][c] == suggestedNumber) {
                    //var index = possibleNumbers.indexOf(suggestedNumber);
                    //if(index >= 0) {
                        //possibleNumbers.remove(index);
                        //addToMatrix = false;
                    //}
                //}
            //}
            //if(addToMatrix) {
                //matrix[emptyRows][column] = (int) possibleNumbers.toArray()[numberIndex];
            //}
        }
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
        ArrayList<Integer> possibleNumbers = new ArrayList(Arrays.asList(1,2,3,4,5,6,7,8,9));

        for(int c = column; c < column+3; c++) {
            for(int r = row; r < row+3; r++) {
                var random = new Random();
                var numberIndex = random.nextInt(possibleNumbers.size());
                matrix[r][c] = (int) possibleNumbers.toArray()[numberIndex];
                possibleNumbers.remove(numberIndex);
            }
        }
    }

    private void FillDependentBox(int row, int column) {
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

        var boxCompleted = false;
        while(!boxCompleted)
        {
            var numbersForBox = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
            for(int c = column; c < column+3; c++) {
                for(int r = row; r < row+3; r++) {
                    ArrayList<Integer> possibleNumbersForCell = GetPossibleNumbersForCell(c, r);
                    var okPossibleNumbers = GetOkPossibleNumbers(numbersForBox, possibleNumbersForCell);
                    if(!okPossibleNumbers.isEmpty())
                    {
                        var random = new Random();
                        var numberIndex = random.nextInt(okPossibleNumbers.size());
                        matrix[r][c] = (int) okPossibleNumbers.toArray()[numberIndex];
                        var indexToRemove = numbersForBox.indexOf(matrix[r][c]);
                        if (indexToRemove >= 0)
                        {
                            numbersForBox.remove(indexToRemove);
                        }
                    }
                }
            }
            if(numbersForBox.isEmpty())
            {
                boxCompleted = true;
            }
        }
    }

    private HashSet<Integer> GetOkPossibleNumbers(ArrayList<Integer> numbers1, ArrayList<Integer> numbers2)
    {
        var okPossibleNumbers = new HashSet<Integer>();
        for (int number : numbers1)
        {
            if(numbers2.contains(number))
            {
                okPossibleNumbers.add(number);
            }
        }
        return okPossibleNumbers;
    }

    private ArrayList<Integer> GetPossibleNumbersForCell(int row, int column) {
        var possibleNumbers = new ArrayList(Arrays.asList(1,2,3,4,5,6,7,8,9));
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
        return possibleNumbers;
    }
    private boolean IsOkForMatrix(int number, int column, int row) {
        //check column
        for(int i = 0; i < size; i++) {
            if(matrix[column][i] == number) {
                return false;
            }
        }
        //check row
        for(int j = 0; j < size; j++) {
            if(matrix[j][row] == number) {
                return false;
            }
        }
        //check box
        return true;
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
