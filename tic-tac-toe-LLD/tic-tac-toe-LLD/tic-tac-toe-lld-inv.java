// Perfect 👍
// I’ll create a **single Java file** version that has the **entire implementation + inline comments + explanation** (so you can copy-paste into one file and run directly).

// ---

// # 📂 Folder Structure (for interviewer explanation)

// ```
// tic-tac-toe-LLD/
// │── Main.java
// │── controller/
// │   └── GameController.java   (Singleton)
// │── model/
// │   ├── Board.java            (Observer subject)
// │   ├── Player.java           (Abstract class)
// │   ├── HumanPlayer.java      (Concrete player)
// │   └── PlayerFactory.java    (Factory)
// │── strategy/
// │   ├── MoveStrategy.java     (Strategy interface)
// │   ├── WinStrategy.java      (Strategy interface)
// │   ├── DefaultMoveStrategy.java
// │   └── DefaultWinStrategy.java
// │── game/
// │   └── TicTacToe.java        (Game loop + controller)
// ```

// # ✅ Complete **One File Implementation**

import java.util.*;

// ======================= CONTROLLER (Singleton) =======================
/**
 * GameController follows Singleton pattern
 * → ensures only one instance of controller exists across the game.
 */
class GameController {
    private static GameController instance;

    private GameController() {} // private constructor prevents direct instantiation

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }
}

// ======================= MODEL CLASSES =======================
/**
 * Abstract Player class
 * → base for Human or AI players
 */
abstract class Player {
    protected char symbol; // X or O

    public char getSymbol() {
        return symbol;
    }

    // Every player must implement how they make a move
    public abstract void makeMove(Board board);

    // Observer pattern hook: board notifies players on update
    public void update(Board board) {
        // Default: do nothing
    }
}

/**
 * HumanPlayer class
 * → Player controlled via console input
 */
class HumanPlayer extends Player {
    public HumanPlayer(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public void makeMove(Board board) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Player " + symbol + " enter row (0-2): ");
        int x = sc.nextInt();
        System.out.println("Player " + symbol + " enter col (0-2): ");
        int y = sc.nextInt();
        board.updateBoard(x, y, symbol); // ask board to update cell
    }
}

/**
 * PlayerFactory
 * → Factory pattern to create players (can extend later for AIPlayer)
 */
class PlayerFactory {
    public static Player createPlayer(char symbol) {
        return new HumanPlayer(symbol);
    }
}

/**
 * Board class
 * → Maintains 2D grid state
 * → Subject in Observer pattern (notifies players on changes)
 */
class Board {
    private char[][] board;
    private List<Player> observers = new ArrayList<>();

    public Board(int size) {
        board = new char[size][size]; // initialize empty board
    }

    public void addObserver(Player player) {
        observers.add(player);
    }

    private void notifyObservers() {
        for (Player player : observers) {
            player.update(this);
        }
    }

    // Update a cell with symbol
    public void updateBoard(int x, int y, char symbol) {
        if (board[x][y] == '\0') {
            board[x][y] = symbol;
            notifyObservers();
        } else {
            System.out.println("Cell already occupied! Move invalid.");
        }
    }

    public char getCell(int x, int y) {
        return board[x][y];
    }

    public int getSize() {
        return board.length;
    }

    public boolean isFull() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == '\0') return false;
            }
        }
        return true;
    }

    // Print the board in console
    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] == '\0' ? "-" : board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}

// ======================= STRATEGY PATTERN =======================
/**
 * MoveStrategy: Strategy pattern interface for validating moves
 */
interface MoveStrategy {
    boolean isValidMove(Board board, int x, int y);
}

/**
 * WinStrategy: Strategy pattern interface for win condition
 */
interface WinStrategy {
    boolean checkWin(Board board, char symbol);
}

/**
 * Default move validation strategy:
 * → A move is valid only if the cell is empty
 */
class DefaultMoveStrategy implements MoveStrategy {
    @Override
    public boolean isValidMove(Board board, int x, int y) {
        return board.getCell(x, y) == '\0';
    }
}

/**
 * Default win condition strategy:
 * → checks rows, columns, diagonals
 */
class DefaultWinStrategy implements WinStrategy {
    @Override
    public boolean checkWin(Board board, char symbol) {
        int size = board.getSize();

        // check rows & columns
        for (int i = 0; i < size; i++) {
            if (checkRow(board, symbol, i) || checkCol(board, symbol, i)) return true;
        }

        // check diagonals
        return checkDiag(board, symbol) || checkAntiDiag(board, symbol);
    }

    private boolean checkRow(Board board, char symbol, int row) {
        for (int i = 0; i < board.getSize(); i++) {
            if (board.getCell(row, i) != symbol) return false;
        }
        return true;
    }

    private boolean checkCol(Board board, char symbol, int col) {
        for (int i = 0; i < board.getSize(); i++) {
            if (board.getCell(i, col) != symbol) return false;
        }
        return true;
    }

    private boolean checkDiag(Board board, char symbol) {
        for (int i = 0; i < board.getSize(); i++) {
            if (board.getCell(i, i) != symbol) return false;
        }
        return true;
    }

    private boolean checkAntiDiag(Board board, char symbol) {
        int size = board.getSize();
        for (int i = 0; i < size; i++) {
            if (board.getCell(i, size - i - 1) != symbol) return false;
        }
        return true;
    }
}

// ======================= GAME LOOP =======================
/**
 * TicTacToe class
 * → orchestrates the game
 * → holds board, players, and strategies
 */
class TicTacToe {
    private Board board;
    private Player currentPlayer;
    private Player player1;
    private Player player2;
    private MoveStrategy moveStrategy;
    private WinStrategy winStrategy;

    public TicTacToe() {
        // Singleton GameController (though unused here, demonstrates pattern)
        GameController controller = GameController.getInstance();

        // Initialize 3x3 board
        board = new Board(3);

        // Create players using Factory pattern
        player1 = PlayerFactory.createPlayer('X');
        player2 = PlayerFactory.createPlayer('O');
        currentPlayer = player1;

        // Assign strategies
        moveStrategy = new DefaultMoveStrategy();
        winStrategy = new DefaultWinStrategy();

        // Register players as observers
        board.addObserver(player1);
        board.addObserver(player2);
    }

    public void play() {
        while (true) {
            board.printBoard();
            System.out.println("Player " + currentPlayer.getSymbol() + "'s turn:");

            // Player attempts to make move
            currentPlayer.makeMove(board);

            // Win condition check
            if (winStrategy.checkWin(board, currentPlayer.getSymbol())) {
                board.printBoard();
                System.out.println("Player " + currentPlayer.getSymbol() + " wins!");
                break;
            }

            // Draw condition check
            if (board.isFull()) {
                board.printBoard();
                System.out.println("It's a draw!");
                break;
            }

            // Switch player
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
    }
}

// ======================= MAIN =======================
public class Main {
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.play();
    }
}
```

---

// # 📖 How to Explain to Interviewer

// * **Singleton** → `GameController` ensures only one controller instance.
// * **Factory** → `PlayerFactory` creates players (easy to extend for AI).
// * **Observer** → `Board` notifies players when updated.
// * **Strategy** → Move & Win conditions are pluggable algorithms.
// * **Separation of Concerns** →

//   * `Board` manages state.
//   * `Player` only makes moves.
//   * `Strategy` checks rules.
//   * `TicTacToe` runs the game loop.

// This design shows **clean extensibility**:

// * Can add **AIPlayer** by extending `Player`.
// * Can change **WinStrategy** for 4x4 Tic Tac Toe.
// * Can support **networked multiplayer** without touching Board/Strategy.

// ---

// 👉 Now you can just **copy-paste this single file** into your IDE as `Main.java` and run directly.

// Would you like me to also **annotate a one-page cheatsheet** (bullet points only, no code) that you can read quickly right before the interview?
