package utilities;

public class Drawer {
    public static void drawField(boolean[][] gameField) {
        StringBuilder frame = new StringBuilder();
        for (int row = 0; row < gameField.length; row++) {
            for (int col = 0; col < gameField[row].length; col++) {
                boolean cell = gameField[row][col];
                frame.append(cell ? "■ " : "· ");
            }
            frame.append("\n");
        }
        for (int i = 0; i < gameField[0].length; i++) {
            frame.append("==");
        }
        frame.append("\n");

        System.out.print("\033[H");
        System.out.print(frame);
        System.out.print("\033[J");
        System.out.flush();
    }
}
