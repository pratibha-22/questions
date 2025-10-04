# Tic Tac Toe LLD (Machine Coding Practice)

## Overview
This is a **Low-Level Design (LLD)** implementation of the Tic Tac Toe game in Java.  
The design demonstrates multiple design patterns:
- **Singleton**: Ensures only one GameController instance exists.
- **Factory**: Centralizes Player creation.
- **Observer**: Board notifies Players on state changes.
- **Strategy**: Encapsulates Move Validation & Win Checking logic.

## Flow
1. GameController initializes the game (Singleton).
2. PlayerFactory creates two players (Factory).
3. Board maintains the state and notifies observers (Observer).
4. Strategies validate moves & check for wins (Strategy).
5. TicTacToe class runs the game loop.

## Example Run
```
- - -
- - -
- - -
Player X's turn:
Enter row: 0
Enter col: 1

- X -
- - -
- - -
Player O's turn:
...
```

## Interview Explanation
- Clear separation of concerns.
- Extendable for **AI player**, **different board sizes**, or **network play**.
- Demonstrates practical OOP + design patterns in action.
