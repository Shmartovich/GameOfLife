package GUI;

import javax.swing.*;
import java.awt.*;

public class GameOfLifeGUI extends JFrame {

    private static final int ROWS      = 20;
    private static final int COLS      = 20;
    private static final int CELL_SIZE = 25;

    private final boolean[][] cells = new boolean[ROWS][COLS];

    private final GamePanel gamePanel;
    private final Timer     timer;

    public GameOfLifeGUI() {
        setTitle("Conway's Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        gamePanel = new GamePanel();

        JButton startButton = new JButton("Start");
        JButton stopButton  = new JButton("Stop");
        JButton nextButton  = new JButton("Next");
        JButton clearButton = new JButton("Clear");

        timer = new Timer(300, e -> nextGeneration());

        startButton.addActionListener(e -> timer.start());

        stopButton.addActionListener(e -> timer.stop());

        nextButton.addActionListener(e -> nextGeneration());

        clearButton.addActionListener(e -> {
            timer.stop();

            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    cells[row][col] = false;
                }
            }

            gamePanel.repaint();
        });

        JPanel controls = new JPanel();
        controls.add(startButton);
        controls.add(stopButton);
        controls.add(nextButton);
        controls.add(clearButton);

        add(gamePanel, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void nextGeneration() {
        boolean[][] next = new boolean[ROWS][COLS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {

                int neighbors = countNeighbors(row, col);

                if (cells[row][col]) {
                    next[row][col] = neighbors == 2 || neighbors == 3;
                } else {
                    next[row][col] = neighbors == 3;
                }
            }
        }

        for (int row = 0; row < ROWS; row++) {
            System.arraycopy(next[row], 0, cells[row], 0, COLS);
        }

        gamePanel.repaint();
    }

    private int countNeighbors(int row, int col) {
        int count = 0;

        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {

                if (rowOffset == 0 && colOffset == 0) {
                    continue;
                }

                int neighborRow = row + rowOffset;
                int neighborCol = col + colOffset;

                if (neighborRow >= 0
                        && neighborRow < ROWS
                        && neighborCol >= 0
                        && neighborCol < COLS
                        && cells[neighborRow][neighborCol]) {

                    count++;
                }
            }
        }

        return count;
    }

    private class GamePanel extends JPanel {

        public GamePanel() {
            setPreferredSize(
                    new Dimension(
                            COLS * CELL_SIZE,
                            ROWS * CELL_SIZE
                    )
            );

            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    int col = e.getX() / CELL_SIZE;
                    int row = e.getY() / CELL_SIZE;

                    if (row >= 0 && row < ROWS
                            && col >= 0 && col < COLS) {

                        cells[row][col] = !cells[row][col];
                        repaint();
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {

                    int x = col * CELL_SIZE;
                    int y = row * CELL_SIZE;

                    if (cells[row][col]) {
                        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    }

                    g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameOfLifeGUI::new);
    }
}
