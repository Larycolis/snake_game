package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<com.javarush.games.snake.GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC32";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private com.javarush.games.snake.Direction direction = com.javarush.games.snake.Direction.LEFT;

    public Snake(int x, int y) {
        snakeParts.add(new com.javarush.games.snake.GameObject(x, y));
        snakeParts.add(new com.javarush.games.snake.GameObject(x + 1, y));
        snakeParts.add(new com.javarush.games.snake.GameObject(x + 2, y));
    }

    public void draw(Game game) {
        for (com.javarush.games.snake.GameObject part : snakeParts) {
            if (snakeParts.indexOf(part) == 0) {
                if (isAlive) {
                    game.setCellValueEx(part.x, part.y, Color.NONE, HEAD_SIGN, Color.BLACK, 75);
                } else {
                    game.setCellValueEx(part.x, part.y, Color.NONE, HEAD_SIGN, Color.RED, 75);
                }
            } else {
                if (isAlive) {
                    game.setCellValueEx(part.x, part.y, Color.NONE, BODY_SIGN, Color.BLACK, 75);
                } else {
                    game.setCellValueEx(part.x, part.y, Color.NONE, BODY_SIGN, Color.RED, 75);
                }
            }
        }
    }

    public void setDirection(com.javarush.games.snake.Direction direction) {
        if (direction == com.javarush.games.snake.Direction.UP && this.direction == com.javarush.games.snake.Direction.DOWN) {
            return;
        } else if (direction == com.javarush.games.snake.Direction.DOWN && this.direction == com.javarush.games.snake.Direction.UP) {
            return;
        } else if (direction == com.javarush.games.snake.Direction.RIGHT && this.direction == com.javarush.games.snake.Direction.LEFT) {
            return;
        } else if (direction == com.javarush.games.snake.Direction.LEFT && this.direction == com.javarush.games.snake.Direction.RIGHT) {
            return;
        }
        if (this.direction == com.javarush.games.snake.Direction.UP && snakeParts.get(0).y == snakeParts.get(1).y) {
            return;
        } else if (this.direction == com.javarush.games.snake.Direction.DOWN && snakeParts.get(0).y == snakeParts.get(1).y) {
            return;
        } else if (this.direction == com.javarush.games.snake.Direction.RIGHT && snakeParts.get(0).x == snakeParts.get(1).x) {
            return;
        } else if (this.direction == com.javarush.games.snake.Direction.LEFT && snakeParts.get(0).x == snakeParts.get(1).x) {
            return;
        } else this.direction = direction;
    }

    public void move(com.javarush.games.snake.Apple apple) {
        com.javarush.games.snake.GameObject head = createNewHead();
        if (head.x == apple.x && head.y == apple.y) {
            if (head.x >= 0 && head.x < SnakeGame.WIDTH && head.y >= 0 && head.y < SnakeGame.HEIGHT) {
                apple.isAlive = false;
                if (checkCollision(head)) {
                    this.isAlive = false;
                    return;
                }
                snakeParts.add(0, head);
            } else {
                this.isAlive = false;
            }
        } else {
            if (head.x >= 0 && head.x < SnakeGame.WIDTH && head.y >= 0 && head.y < SnakeGame.HEIGHT) {
                if (checkCollision(head)) {
                    this.isAlive = false;
                    return;
                }
                snakeParts.add(0, head);
                removeTail();
            } else {
                this.isAlive = false;
            }
        }
    }

    public com.javarush.games.snake.GameObject createNewHead() {
        com.javarush.games.snake.GameObject head = snakeParts.get(0);
        if (direction == com.javarush.games.snake.Direction.LEFT) {
            head = new com.javarush.games.snake.GameObject(head.x - 1, head.y);
        } else if (direction == com.javarush.games.snake.Direction.RIGHT) {
            head = new com.javarush.games.snake.GameObject(head.x + 1, head.y);
        } else if (direction == com.javarush.games.snake.Direction.UP) {
            head = new com.javarush.games.snake.GameObject(head.x, head.y - 1);
        } else if (direction == com.javarush.games.snake.Direction.DOWN) {
            head = new com.javarush.games.snake.GameObject(head.x, head.y + 1);
        }
        return head;
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public boolean checkCollision(com.javarush.games.snake.GameObject gameObject) {
        for (int i = 0; i < snakeParts.size(); i++) {
            if (gameObject.x == snakeParts.get(i).x && gameObject.y == snakeParts.get(i).y) {
                return true;
            }
        }
        return false;
    }

    public int getLength() {
        return snakeParts.size();
    }
}

