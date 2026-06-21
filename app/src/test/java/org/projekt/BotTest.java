package org.projekt;

public class BotTest {

    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final int BOT_ID = 2;
    private static final int HUMAN_ID = 1;
    private static final int NOT_FOUND = -1;

    static class TestGame extends Game {
        public int[][] manualBoard = new int[ROWS][COLS];

        @Override
        public int checkDisk(int row, int col, int playerId) {
            if (manualBoard[row][col] == playerId) {
                return 1;
            }
            return 0;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- STARTING BOT TESTS ---");
        
        testEmptyColumn();
        testFullColumn();
        testInvalidColumn();
        
        testHorizontalTrio();
        testVerticalTrio();
        testGravityTrapAvoidance();
        
        testDiagonalTrioDescending();
        testDiagonalTrioAscending();
        
        System.out.println("--- END OF TESTS ---");
    }

    static void testEmptyColumn() {
        Bot bot = new Bot();
        TestGame game = new TestGame(); 
        int result = bot.getDropRow(game, 3);
        int expectedRow = ROWS - 1;
        
        if (result == expectedRow) {
            System.out.println("[OK] testEmptyColumn: The disk fell to the bottom.");
        } else {
            System.out.println("[ERROR] testEmptyColumn: Expected " + expectedRow + ", but got " + result);
        }
    }

    static void testFullColumn() {
        Bot bot = new Bot();
        TestGame game = new TestGame();
        for (int row = 0; row < ROWS; row++) {
            game.manualBoard[row][2] = HUMAN_ID;
        }
        int result = bot.getDropRow(game, 2);
        if (result == NOT_FOUND) {
            System.out.println("[OK] testFullColumn: The bot correctly noticed that the column is full.");
        } else {
            System.out.println("[ERROR] testFullColumn: Expected NOT_FOUND (-1), but got " + result);
        }
    }

    static void testInvalidColumn() {
        Bot bot = new Bot();
        TestGame game = new TestGame();
        int result = bot.getDropRow(game, 10);
        if (result == NOT_FOUND) {
            System.out.println("[OK] testInvalidColumn: The bot prevented going out of bounds.");
        } else {
            System.out.println("[ERROR] testInvalidColumn: Expected NOT_FOUND (-1), but got " + result);
        }
    }

    static void testHorizontalTrio() {
        Bot bot = new Bot();
        TestGame game = new TestGame();
        game.manualBoard[5][0] = BOT_ID;
        game.manualBoard[5][1] = BOT_ID;
        game.manualBoard[5][2] = BOT_ID;
        int result = bot.checkTrioInRow(game, BOT_ID);
        if (result == 3) {
            System.out.println("[OK] testHorizontalTrio: Bot found the winning spot in a row.");
        } else {
            System.out.println("[ERROR] testHorizontalTrio: Expected column 3, but got " + result);
        }
    }

    static void testVerticalTrio() {
        Bot bot = new Bot();
        TestGame game = new TestGame();
        game.manualBoard[5][0] = BOT_ID;
        game.manualBoard[4][0] = BOT_ID;
        game.manualBoard[3][0] = BOT_ID;
        int result = bot.checkTrioInCol(game, BOT_ID);
        if (result == 0) {
            System.out.println("[OK] testVerticalTrio: Bot found the winning spot on top of the column.");
        } else {
            System.out.println("[ERROR] testVerticalTrio: Expected column 0, but got " + result);
        }
    }

    static void testGravityTrapAvoidance() {
        Bot bot = new Bot();
        TestGame game = new TestGame();
        game.manualBoard[3][0] = BOT_ID;
        game.manualBoard[3][1] = BOT_ID;
        game.manualBoard[3][2] = BOT_ID;
        int result = bot.checkTrioInRow(game, BOT_ID);
        if (result == NOT_FOUND) {
            System.out.println("[OK] testGravityTrapAvoidance: Bot correctly ignored a gravity trap.");
        } else {
            System.out.println("[ERROR] testGravityTrapAvoidance: Expected NOT_FOUND (-1), but got " + result);
        }
    }

    static void testDiagonalTrioDescending() {
        Bot bot = new Bot();
        TestGame game = new TestGame();

        game.manualBoard[2][0] = BOT_ID;
        game.manualBoard[3][1] = BOT_ID;
        game.manualBoard[4][2] = BOT_ID;

        int result = bot.checkTrioInDiag(game, BOT_ID);

        if (result == 3) {
            System.out.println("[OK] testDiagonalTrioDescending: Bot completed a descending (\\) diagonal.");
        } else {
            System.out.println("[ERROR] testDiagonalTrioDescending: Expected column 3, but got " + result);
        }
    }

    static void testDiagonalTrioAscending() {
        Bot bot = new Bot();
        TestGame game = new TestGame();

        game.manualBoard[5][0] = BOT_ID;
        game.manualBoard[4][1] = BOT_ID;
        game.manualBoard[3][2] = BOT_ID;

        game.manualBoard[5][3] = HUMAN_ID;
        game.manualBoard[4][3] = HUMAN_ID;
        game.manualBoard[3][3] = HUMAN_ID;

        int result = bot.checkTrioInDiag(game, BOT_ID);

        if (result == 3) {
            System.out.println("[OK] testDiagonalTrioAscending: Bot completed an ascending (/) diagonal with gravity support.");
        } else {
            System.out.println("[ERROR] testDiagonalTrioAscending: Expected column 3, but got " + result);
        }
    }
}