package gui;

import entities.GameField;
import utilities.Calculator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class GameOfLifeFrame extends JFrame {

    private final int    rows;
    private final int    cols;
    private final double percentOfNotDead;

    private boolean[][] gameField;

    private final GamePanel gamePanel;
    private final Timer     timer;
    private final JLabel    generationLabel;

    private int generation = 0;

    public GameOfLifeFrame(int rows, int cols, double percentOfNotDead) {
        this.rows = rows;
        this.cols = cols;
        this.percentOfNotDead = percentOfNotDead;

        GameField initialField = new GameField(rows, cols, percentOfNotDead);
        gameField = initialField.getGameField();

        gamePanel = new GamePanel(gameField, 18);

        setTitle("Conway's Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(gamePanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton startButton  = new JButton("Start");
        JButton pauseButton  = new JButton("Pause");
        JButton nextButton   = new JButton("Next");
        JButton clearButton  = new JButton("Clear");
        JButton randomButton = new JButton("Random");

        generationLabel = new JLabel("Generation: 0");

        JSlider speedSlider = new JSlider(50, 1000, 250);

        timer = new Timer(speedSlider.getValue(), e -> nextGeneration());
        speedSlider.addChangeListener(e -> {
            timer.setDelay(speedSlider.getValue());

            speedSlider.setToolTipText(
                    "Delay: " + speedSlider.getValue() + " milliseconds"
            );
        });

        startButton.addActionListener(e -> timer.start());

        pauseButton.addActionListener(e -> timer.stop());

        nextButton.addActionListener(e -> {
            timer.stop();
            nextGeneration();
        });

        clearButton.addActionListener(e -> {
            timer.stop();

            gameField = new boolean[rows][cols];
            generation = 0;

            gamePanel.setGameField(gameField);
            updateGenerationLabel();
        });

        randomButton.addActionListener(e -> {
            timer.stop();

            GameField randomField = new GameField(rows, cols, percentOfNotDead);
            gameField = randomField.getGameField();
            generation = 0;

            gamePanel.setGameField(gameField);
            updateGenerationLabel();
        });

        speedSlider.addChangeListener(e ->
                timer.setDelay(speedSlider.getValue())
        );

        controls.add(startButton);
        controls.add(pauseButton);
        controls.add(nextButton);
        controls.add(clearButton);
        controls.add(randomButton);
        controls.add(new JLabel("Next generation delay:"));
        controls.add(speedSlider);
        controls.add(generationLabel);

        add(controls, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void nextGeneration() {
        gameField = Calculator.createNextGeneration(gameField);
        generation++;

        gamePanel.setGameField(gameField);
        updateGenerationLabel();
    }

    private void updateGenerationLabel() {
        generationLabel.setText("Generation: " + generation);
    }
}
