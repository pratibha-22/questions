package strategy;

import model.Board;

public interface MoveStrategy {
    boolean isValidMove(Board board, int x, int y);
}
