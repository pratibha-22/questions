package model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private char[][] board;
    private List<Player> observers = new ArrayList<>();

    public Board(int size) {
        board = new char[size][size];
    }

    public void addObserver(Player player) {
        observers.add(player);
    }

    private void notifyObservers() {
        for (Player player : observers) {
            player.update(this);
        }
    }

    public void updateBoard(int x, int y, char symbol) {
        board[x][y] = symbol;
        notifyObservers();
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
