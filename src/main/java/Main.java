import GUI.GameOfLifeFrame;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        int rows = 30;
        int cols = 30;
        double percentOfNotDead = 30;

        SwingUtilities.invokeLater(() -> {
            GameOfLifeFrame frame =
                    new GameOfLifeFrame(rows, cols, percentOfNotDead);

            frame.setVisible(true);
        });
    }
}
