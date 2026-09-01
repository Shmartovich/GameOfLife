package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameField {
    private final boolean[][] gameField;
    private final int         rows;
    private final int         cols;
    private final double      percentOfNotDead;

    public GameField(int rows, int cols, double percentOfNotDead) {
        this.rows = rows;
        this.cols = cols;
        this.percentOfNotDead = percentOfNotDead;

        gameField = new boolean[rows][cols];
        int           allCells  = rows * cols;
        List<Integer> positions = new ArrayList<Integer>();
        for (int i = 0; i < allCells; i++) {
            positions.add(i);
        }
        Collections.shuffle(positions);

        int liveCellsToBorn = (int) Math.floor(allCells * percentOfNotDead / 100);

        for (int i = 0; i < liveCellsToBorn; i++) {
            int row = positions.get(i) / cols;
            int col = positions.get(i) % cols;

            gameField[row][col] = true;
        }
    }

    public boolean[][] getGameField() {
        return gameField;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double getPercentOfNotDead() {
        return percentOfNotDead;
    }
}
