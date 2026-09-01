package Utilities;

public class Calculator {
    public static boolean[][] createNextGeneration(boolean[][] gameField) {
        boolean[][] newGameField = new boolean[gameField.length][];

        for (int row = 0; row < gameField.length; row++) {
            for (int col = 0; col < gameField[row].length; col++) {
                if (newGameField[row] == null) {
                    newGameField[row] = new boolean[gameField[row].length];
                }

                if (gameField[row][col] == true) {
                    int liveNeighbors = findLiveNeighbors(gameField, row, col);
                    newGameField[row][col] = liveNeighbors == 2 || liveNeighbors == 3 ? true : false;
                } else {
                    int liveNeighbors = findLiveNeighbors(gameField, row, col);
                    newGameField[row][col] = liveNeighbors == 3 ? true : false;
                }

            }
        }
        return newGameField;
    }

    // Option - outside is nothing = dead
    private static int findLiveNeighbors(boolean[][] gameField, int row, int col) {
        int result = 0;

        int[][] directions = {
                // left = row, right = col

                {-1, 0},
                {-1, -1},
                {-1, 1},

                {0, -1},
                {0, 1},

                {1, 0},
                {1, -1},
                {1, 1}
        };

        for (int[] dirs : directions) {
            int newRow = row + dirs[0];
            int newCol = col + dirs[1];

            if (newRow < gameField.length && newRow >= 0 && newCol < gameField[row].length && newCol >= 0) {
                if (gameField[newRow][newCol]) {
                    result++;
                }
            }
        }
        return result;
    }

    // Option - infinity via torus
    // private static int calculateLiveNeighborsOfThisCell(boolean[][] gameField, int rows, int cols, int row, int col) {

}
