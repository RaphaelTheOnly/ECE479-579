package org.Group7_FinalProject.Runner;

import org.Group7_FinalProject.Framework.RunningScreen;

public class Obstacle extends Sprite {
	
    protected final int INITIAL_Y = 850;
    
    public Obstacle(int x, int y) {
    	
        super(x, y);
        visible = false;
        loadImage("src/resources/obstacle.png");
        
    }

    @Override
    public void move() {

        if (y < 0) {
            y = INITIAL_Y;
            visible = false;
        }
        
        else if (y > INITIAL_Y) {
        	y = 0;
        }

        y -= DIFFICULTY;
    }
}