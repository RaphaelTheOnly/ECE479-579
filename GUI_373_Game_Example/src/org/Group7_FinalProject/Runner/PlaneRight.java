package org.Group7_FinalProject.Runner;

import org.Group7_FinalProject.Framework.RunningScreen;

public class PlaneRight extends Sprite {

    private RunningScreen runningScreen;
    
    public PlaneRight(int x, int y, RunningScreen runningScreen) {
        
    	super(x, y);
    	this.runningScreen = runningScreen;
        loadImage("src/resources/crystal_right.png");
        
    }

    @Override
    public void move() {

        if (y < 0) {
            y = runningScreen.getWindow().getHeight() + 130;
        }

        y -= DIFFICULTY;
        
    }
}