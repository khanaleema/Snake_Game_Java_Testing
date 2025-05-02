package com.zetcode;

import static org.junit.Assert.*;
import org.junit.Test;

public class SnakeGameUnitTests {

    @Test
    public void testSnakeMovementToRight() {
        Board board = new Board();
        int initialX = board.getHeadX();
        board.setRightDirection(true);
        board.setLeftDirection(false);
        board.triggerMove();
        assertEquals(initialX + 10, board.getHeadX()); // DOT_SIZE = 10
    }

    @Test
    public void testSnakeLengthIncreasesAfterEating() {
        Board board = new Board();
        int originalLength = board.getDots();
        board.setApplePosition(board.getHeadX(), board.getHeadY());
        board.triggerCheckApple();
        assertEquals(originalLength + 1, board.getDots());
    }

    @Test
    public void testInvalidKeyPressDoesNotMoveSnake() {
        Board board = new Board();
        int initialX = board.getHeadX();

        board.setRightDirection(true); 
        board.triggerMove();

        assertEquals("Snake moved on invalid input!", initialX, board.getHeadX());
    }
}
