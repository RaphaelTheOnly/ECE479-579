package org.Group7_FinalProject.Runner;

public class Obstacle extends Sprite {

    private final int INITIAL_Y = 850;

    public Obstacle(int x, int y) {
        super(x, y);

        initObstacle();
    }

    private void initObstacle() {

        loadImage("src/resources/crystal7.png");
        getImageDimensions();
    }

    public void move() {

        if (y < 0) {
            y = INITIAL_Y;
        }
        
        else if (y > INITIAL_Y) {
        	y = 0;
        }

        y -= DIFFICULTY;
    }
}