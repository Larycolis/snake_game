package com.javarush.games.snake;

import com.javarush.engine.cell.*;
import com.javarush.engine.cell.Game;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private static final int GOAL = 28;
    private com.javarush.games.snake.Snake snake;
    private com.javarush.games.snake.Apple apple;
    private int turnDelay;
    private boolean isGameStopped;
    private int score;

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (!apple.isAlive) {
            createNewApple();
            score += 5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
        }
        if (!snake.isAlive) {
            gameOver();
        }
        if (snake.getLength() > GOAL) {
            win();
        }
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.LEFT) {
            snake.setDirection(com.javarush.games.snake.Direction.LEFT);
        } else if (key == Key.RIGHT) {
            snake.setDirection(com.javarush.games.snake.Direction.RIGHT);
        } else if (key == Key.UP) {
            snake.setDirection(com.javarush.games.snake.Direction.UP);
        } else if (key == Key.DOWN) {
            snake.setDirection(com.javarush.games.snake.Direction.DOWN);
        }
        if (isGameStopped && key == Key.SPACE) {
            createGame();
        }
    }

    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void drawScene() {
        for (int y = 0; y < WIDTH; y++) {
            for (int x = 0; x < HEIGHT; x++) {
                setCellValueEx(x, y, Color.DARKGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    private void createGame() {
        turnDelay = 300;
        isGameStopped = false;
        score = 0;
        com.javarush.games.snake.Snake snakeNew = new com.javarush.games.snake.Snake(WIDTH / 2, HEIGHT / 2);
        this.snake = snakeNew;
        createNewApple();
        setScore(score);
        drawScene();
        setTurnTimer(turnDelay);
    }

    private void createNewApple() {
        com.javarush.games.snake.Apple appleNew = new com.javarush.games.snake.Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        while (snake.checkCollision(appleNew)) {
            appleNew = new com.javarush.games.snake.Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        }
        this.apple = appleNew;

    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "GAME OVER", Color.RED, 70);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.WHITE, "YOU WIN", Color.RED, 70);
    }
}
