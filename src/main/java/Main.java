import entities.GameField;
import gui.GameOfLifeFrame;
import utilities.Calculator;
import utilities.Drawer;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) throws InterruptedException {
// Console output

//        GameField   game           = new GameField(30, 30, 30);
//        boolean[][] gameField      = game.getGameField();
//        int         FRAME_DELAY_MS = 100;
//
//        clearConsole();
//
//        while (true) {
//            Drawer.drawField(gameField);
//
//            gameField = Calculator.createNextGeneration(gameField);
//
//            Thread.sleep(FRAME_DELAY_MS);
//        }
//    }
//
//    private static void clearConsole() {
//        System.out.print("\033[2J\033[H");
//        System.out.flush();
//    }

// GUI output
        int    rows             = 50;
        int    cols             = 50;
        double percentOfNotDead = 30;

        SwingUtilities.invokeLater(() -> {
            GameOfLifeFrame frame =
                    new GameOfLifeFrame(rows, cols, percentOfNotDead);

            frame.setVisible(true);
        });
    }
}

