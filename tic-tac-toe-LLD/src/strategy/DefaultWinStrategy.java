package strategy;

import model.Board;

public class DefaultWinStrategy implements WinStrategy {
    @Override
    public boolean checkWin(Board board, char symbol) {
        int size = board.getSize();
        for (int i = 0; i < size; i++) {
            if (checkRow(board, symbol, i) || checkCol(board, symbol, i)) return true;
        }
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
