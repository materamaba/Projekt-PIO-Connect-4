package org.projekt;

public class BotTest {

    static class TestGame extends Game {
        public int[][] manualBoard = new int[6][7];

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
        if (result == 5) {
            System.out.println("[OK] testEmptyColumn: The disk fell to the bottom.");
        } else {
            System.out.println("[ERROR] testEmptyColumn: Expected 5, but got " + result);
        }
    }

    static void testFullColumn() {
        Bot bot = new Bot();
        TestGame game = new TestGame();
        for (int row = 0; row < 6; row++) {
            game.manualBoard[row][2] = 1;
        }
        int result = bot.getDropRow(game, 2);
        if (result == -1) {
            System.out.println("[OK] testFullColumn: The bot correctly noticed that the column is full.");
        } else {
            System.out.println("[ERROR] testFullColumn: Expected -1, but got " + result);
        }
    }

    static void testInvalidColumn() {
        Bot bot = new Bot();
        TestGame game = new TestGame();
        int result = bot.getDropRow(game, 10);
        if (result == -1) {
            System.out.println("[OK] testInvalidColumn: The bot prevented going out of bounds.");
        } else {
            System.out.println("[ERROR] testInvalidColumn: Expected -1, but got " + result);
        }
    }

    static void testHorizontalTrio() {
        Bot bot = new Bot();
        TestGame game = new TestGame();
        game.manualBoard[5][0] = 2;
        game.manualBoard[5][1] = 2;
        game.manualBoard[5][2] = 2;
        int result = bot.checkTrioInRow(game, 2);
        if (result == 3) {
            System.out.println("[OK] testHorizontalTrio: Bot found the winning spot in a row.");
        } else {
            System.out.println("[ERROR] testHorizontalTrio: Expected column 3, but got " + result);
        }
    }

    static void testVerticalTrio() {
        Bot bot = new Bot();
        TestGame game = new TestGame();
        game.manualBoard[5][0] = 2;
        game.manualBoard[4][0] = 2;
        game.manualBoard[3][0] = 2;
        int result = bot.checkTrioInCol(game, 2);
        if (result == 0) {
            System.out.println("[OK] testVerticalTrio: Bot found the winning spot on top of the column.");
        } else {
            System.out.println("[ERROR] testVerticalTrio: Expected column 0, but got " + result);
        }
    }

    static void testGravityTrapAvoidance() {
        Bot bot = new Bot();
        TestGame game = new TestGame();
        game.manualBoard[3][0] = 2;
        game.manualBoard[3][1] = 2;
        game.manualBoard[3][2] = 2;
        int result = bot.checkTrioInRow(game, 2);
        if (result == -1) {
            System.out.println("[OK] testGravityTrapAvoidance: Bot correctly ignored a gravity trap.");
        } else {
            System.out.println("[ERROR] testGravityTrapAvoidance: Expected -1 (trap), but got " + result);
        }
    }

    static void testDiagonalTrioDescending() {
        Bot bot = new Bot();
        TestGame game = new TestGame();

        game.manualBoard[2][0] = 2;
        game.manualBoard[3][1] = 2;
        game.manualBoard[4][2] = 2;

        int result = bot.checkTrioInDiag(game, 2);

        if (result == 3) {
            System.out.println("[OK] testDiagonalTrioDescending: Bot completed a descending (\\) diagonal.");
        } else {
            System.out.println("[ERROR] testDiagonalTrioDescending: Expected column 3, but got " + result);
        }
    }

    static void testDiagonalTrioAscending() {
        Bot bot = new Bot();
        TestGame game = new TestGame();

        game.manualBoard[5][0] = 2;
        game.manualBoard[4][1] = 2;
        game.manualBoard[3][2] = 2;

        game.manualBoard[5][3] = 1;
        game.manualBoard[4][3] = 1;
        game.manualBoard[3][3] = 1;

        int result = bot.checkTrioInDiag(game, 2);

        if (result == 3) {
            System.out.println("[OK] testDiagonalTrioAscending: Bot completed an ascending (/) diagonal with gravity support.");
        } else {
            System.out.println("[ERROR] testDiagonalTrioAscending: Expected column 3, but got " + result);
        }
    }
}