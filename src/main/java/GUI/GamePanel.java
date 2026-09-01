package GUI;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {

    private boolean[][] gameField;
    private final int cellSize;

    public GamePanel(boolean[][] gameField, int cellSize) {
        this.gameField = gameField;
        this.cellSize = cellSize;

        updatePreferredSize();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int col = e.getX() / cellSize;
                int row = e.getY() / cellSize;

                if (row >= 0 && row < GamePanel.this.gameField.length
                        && col >= 0 && col < GamePanel.this.gameField[row].length) {

                    GamePanel.this.gameField[row][col] =
                            !GamePanel.this.gameField[row][col];

                    repaint();
                }
            }
        });
    }

    public void setGameField(boolean[][] gameField) {
        this.gameField = gameField;
        updatePreferredSize();
        revalidate();
        repaint();
    }

    private void updatePreferredSize() {
        if (gameField.length == 0) {
            return;
        }

        setPreferredSize(new Dimension(
                gameField[0].length * cellSize,
                gameField.length * cellSize
        ));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < gameField.length; row++) {
            for (int col = 0; col < gameField[row].length; col++) {

                int x = col * cellSize;
                int y = row * cellSize;

                if (gameField[row][col]) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(x, y, cellSize, cellSize);

                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(x, y, cellSize, cellSize);
            }
        }
    }
}
