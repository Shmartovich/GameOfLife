package Utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CalculatorTest {

    @Test
    void blockStillLifeRemainsUnchanged() {
        boolean[][] block = {
                {false, false, false, false},
                {false, true, true, false},
                {false, true, true, false},
                {false, false, false, false}
        };

        assertArrayEquals(block, Calculator.createNextGeneration(block));
    }

    @Test
    void blinkerOscillatorRotatesEveryGeneration() {
        boolean[][] vertical = {
                {false, false, false, false, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, false, false, false, false}
        };
        boolean[][] horizontal = {
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, true, true, true, false},
                {false, false, false, false, false},
                {false, false, false, false, false}
        };

        boolean[][] next = Calculator.createNextGeneration(vertical);
        boolean[][] afterTwoGenerations = Calculator.createNextGeneration(next);

        assertArrayEquals(horizontal, next);
        assertArrayEquals(vertical, afterTwoGenerations);
    }

    @Test
    void livingCellsDieFromUnderpopulationAndOverpopulation() {
        boolean[][] underpopulated = {
                {false, false, false},
                {false, true, false},
                {false, false, false}
        };
        boolean[][] overpopulated = {
                {true, true, true},
                {true, true, true},
                {true, true, true}
        };

        assertArrayEquals(new boolean[][] {
                {false, false, false},
                {false, false, false},
                {false, false, false}
        }, Calculator.createNextGeneration(underpopulated));
        assertArrayEquals(new boolean[][] {
                {true, false, true},
                {false, false, false},
                {true, false, true}
        }, Calculator.createNextGeneration(overpopulated));
    }

    @Test
    void deadCellWithExactlyThreeLiveNeighborsBecomesAliveAtTheBoundary() {
        boolean[][] field = {
                {false, true},
                {true, true}
        };

        assertArrayEquals(new boolean[][] {
                {true, true},
                {true, true}
        }, Calculator.createNextGeneration(field));
    }
}
