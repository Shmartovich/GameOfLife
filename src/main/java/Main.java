import Entities.GameField;
import Utilities.Calculator;
import Utilities.Drawer;

public class Main {

    static void main() throws InterruptedException {
//        Random rand= new Random();
//        rand.nextInt(15)
        ; // random configuration and then show it in console

        int    rows             = 30;
        int    cols             = 30;
        double percentOfNotDead = 30;

        GameField   gf        = new GameField(rows, cols, percentOfNotDead);
        boolean[][] gameField = gf.getGameField();
        while (true) {
            Drawer.drawField(gameField);
            gameField = Calculator.createNextGeneration(gameField);
            Thread.sleep(10);
        }
    }
}
