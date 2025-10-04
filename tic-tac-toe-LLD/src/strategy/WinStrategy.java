package strategy;

import model.Board;

public interface WinStrategy {
    boolean checkWin(Board board, char symbol);
}
