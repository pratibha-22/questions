package game;

import controller.GameController;
import model.*;
import strategy.*;

public class TicTacToe {
    private Board board;
    private Player currentPlayer;
    private Player player1;
    private Player player2;
    private MoveStrategy moveStrategy;
    private WinStrategy winStrategy;

    public TicTacToe() {
        GameController controller = GameController.getInstance();
        board = new Board(3);
        player1 = PlayerFactory.createPlayer('X');
        player2 = PlayerFactory.createPlayer('O');
        currentPlayer = player1;
        moveStrategy = new DefaultMoveStrategy();
        winStrategy = new DefaultWinStrategy();
        board.addObserver(player1);
        board.addObserver(player2);
    }

    public void play() {
        while (true) {
            board.printBoard();
            System.out.println("Player " + currentPlayer.getSymbol() + "'s turn:");
            currentPlayer.makeMove(board);
            if (winStrategy.checkWin(board, currentPlayer.getSymbol())) {
                board.printBoard();
                System.out.println("Player " + currentPlayer.getSymbol() + " wins!");
                break;
            }
            if (board.isFull()) {
                board.printBoard();
                System.out.println("It's a draw!");
                break;
            }
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
    }
}
