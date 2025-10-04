package model;

import java.util.Scanner;

public class HumanPlayer extends Player {

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
        board.updateBoard(x, y, symbol);
    }
}
