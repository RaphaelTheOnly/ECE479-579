package org.Group7_FinalProject.Runner;

import org.Group7_FinalProject.Framework.RunningScreen;

public class Powerup extends Sprite {
	
	//Fields for a powerup
    protected final int INITIAL_Y = 850;
	private boolean isActive;
	
	//Constructor requires two arguments
	public Powerup(int x, int y) {
		
		super(x, y);
		visible = false;
		isActive = false;
		
	}
	
	@Override
	public void move() {

        if (y < 0) {
        	this.setVisible(false);
        	isActive = false;
        }

        y -= DIFFICULTY;
    }
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}
