
// Import the Scanner class from the java.util package
import java.util.Scanner;

// Define an interface InputScanner with a single method nextInt()
interface InputScanner {
    int nextInt();
}

// Create a class ScannerAdapter that implements the InputScanner interface
class ScannerAdapter implements InputScanner {
    // Declare a private Scanner object
    private Scanner scanner;

    // Constructor that takes a Scanner object as an argument
    public ScannerAdapter(Scanner scanner) {
        // Initialize the scanner object
        this.scanner = scanner;
    }

    // Override the nextInt() method from the InputScanner interface
    @Override
    public int nextInt() {
        // Return the next integer from the scanner object
        return scanner.nextInt();
    }
}

// Create a class AdapterPattern
public class AddapterPattern {
    // Declare private variables for rows, columns, matrix, and inputScanner
    private int rows;
    private int columns;
    private int[][] matrix;
    private InputScanner inputScanner;

    // Constructor that takes rows, columns, and inputScanner as arguments
    public AddapterPattern(int rows, int columns, InputScanner inputScanner) {
        // Initialize the rows, columns, matrix, and inputScanner variables
        this.rows = rows;
        this.columns = columns;
        this.matrix = new int[rows][columns];
        this.inputScanner = inputScanner;
    }

    // Method to get input from the user
    public void getInput() {
        // Loop through the rows and columns of the matrix
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                try {
                    // Prompt the user to enter an element
                    System.out.print("Enter the element at position (" + (i + 1) + "," + (j + 1) + "): ");
                    // Read the integer using inputScanner and store it in the matrix
                    matrix[i][j] = inputScanner.nextInt();
                } catch (Exception e) {
                    // Handle invalid input
                    throw new IllegalArgumentException("Invalid input. Please enter an integer.");
                }
            }
        }
    }

    // Method to calculate the adjoint of the matrix
    public int[][] findAdjoint() {
        // Get the size of the matrix
        int n = matrix.length;
        // Create an empty adjoint matrix of the same size
        int[][] adjoint = new int[n][n];

        // Loop through the rows and columns of the matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Calculate the cofactor of the element
                int cofactor = (int) Math.pow(-1, i + j) * getMinor(i, j);
                // Store the cofactor in the adjoint matrix
                adjoint[j][i] = cofactor;
            }
        }

        // Return the adjoint matrix
        return adjoint;
    }

    // Method to calculate the minor of an element
    public int getMinor(int row, int col) {
        // Create a minor matrix by removing the row and column of the element
        int[][] minor = new int[rows - 1][columns - 1];
        int r = 0, c = 0;

        // Copy elements from the original matrix to the minor matrix, skipping the row
        // and column of the element
        for (int i = 0; i < rows; i++) {
            if (i == row)
                continue;
            for (int j = 0; j < columns; j++) {
                if (j == col)
                    continue;
                minor[r][c++] = matrix[i][j];
                if (c == columns - 1) {
                    c = 0;
                    r++;
                }
            }
        }

        // Calculate the determinant of the minor matrix
        return determinant(minor);
    }

    // Method to calculate the determinant of a matrix
    public int determinant(int[][] matrix) {
        int n = matrix.length;
        if (n == 1)
            return matrix[0][0];

        int det = 0;
        for (int i = 0; i < n; i++) {
            int sign = (int) Math.pow(-1, i);
            int cofactor = sign * matrix[0][i] * determinant(getSubMatrix(matrix, 0, i));
            det += cofactor;
        }

        return det;
    }

    // Method to get a submatrix by removing a specified row and column
    public int[][] getSubMatrix(int[][] matrix, int row, int col) {
        int n = matrix.length;
        int[][] subMatrix = new int[n - 1][n - 1];
        int r = 0, c = 0;

        // Copy elements from the original matrix to the submatrix, skipping the
        // specified row and column
        for (int i = 0; i < n; i++) {
            if (i == row)
                continue;
            for (int j = 0; j < n; j++) {
                if (j == col)
                    continue;
                subMatrix[r][c++] = matrix[i][j];
                if (c == n - 1) {
                    c = 0;
                    r++;
                }
            }
        }

        return subMatrix;
    }

    // Method to print the adjoint matrix
    public void output() {
        int[][] adjointMatrix = findAdjoint();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(adjointMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Main method
    public static void main(String[] args) {
        // Create a Scanner object to read input from the console
        Scanner scanner = new Scanner(System.in);
        // Create an InputScanner object using the ScannerAdapter
        InputScanner inputScanner = new ScannerAdapter(scanner);

        // Prompt the user to enter the number of rows and columns
        System.out.print("Enter the number of rows: ");
        int rows = inputScanner.nextInt();
        System.out.print("Enter the number of columns: ");
        int columns = inputScanner.nextInt();

        // Create an AdapterPattern object with the specified rows, columns, and
        // inputScanner
        AddapterPattern adjoint = new AddapterPattern(rows, columns, inputScanner);

        try {
            // Get input from the user and calculate the adjoint matrix
            adjoint.getInput();
            adjoint.output();
        } catch (Exception e) {
            // Handle exceptions
            System.out.println("Error: " + e.getMessage());
        }
    }
}
