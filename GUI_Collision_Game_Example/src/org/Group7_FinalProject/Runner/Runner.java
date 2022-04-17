package org.Group7_FinalProject.Runner;

import java.awt.event.KeyEvent;

public class Runner extends Sprite {

    private int dx;
    private int dy = DIFFICULTY * 2;
    private final int SPEED = 3;
    private int lastKey;

    public Runner(int x, int y) {
        super(x, y);
		initRunner();
    }

	private void initRunner() {
        loadImage("src/resources/runner3.png");
        getImageDimensions();
    }
	
	public void setMovement(boolean move) {	
		if (!move) {
			//dx = 0; //maybe need this to not get head stuck in obstacle...
			dy = -DIFFICULTY;
		}
		else {
			dy = DIFFICULTY * 2;
		}
	}
	
    public void move() {
        x += dx;
        y += dy;
		if (x < 1) {
            x = 1;
        }
		else if (x > 1400) {
			x = 1400;
		}
		
 
     	if (y > 700) {
        	y = 700;
        }
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        
        if ((key == KeyEvent.VK_SPACE) && (lastKey == KeyEvent.VK_LEFT)) {
        	dy = -4;
        	//dx = -SPEED;
        	dx = -DIFFICULTY * 3;
        }
        else if ((key == KeyEvent.VK_SPACE) && (lastKey == KeyEvent.VK_RIGHT)) {
        	dy = -4;
        	//dx = SPEED;
        	dx = DIFFICULTY * 3;
        }
        
        if (key == KeyEvent.VK_SPACE) {
        	dy = -4;
        }

        if (key == KeyEvent.VK_LEFT) {
            //dx = -SPEED;
        	dx = -DIFFICULTY * 3;
            lastKey = key;
        }

        if (key == KeyEvent.VK_RIGHT) {
            //dx = SPEED;
        	dx = DIFFICULTY * 3;
            lastKey = key;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_SPACE) && (lastKey == KeyEvent.VK_LEFT)) {
        	dy = DIFFICULTY * 2;
        	//dx = -SPEED;
        	dx = -DIFFICULTY * 3;
        }
        
        else if ((key == KeyEvent.VK_SPACE) && (lastKey == KeyEvent.VK_RIGHT)) {
        	dy = DIFFICULTY * 2;
        	//dx = SPEED;
        	dx = DIFFICULTY * 3;
        }
        
        /* if (key == KeyEvent.VK_SPACE) {
            dx = 0;
            dy = DIFFICULTY * 2;
        } */ 
        
        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }
    
    public int getLastKey() {
    	return lastKey;
    }
}