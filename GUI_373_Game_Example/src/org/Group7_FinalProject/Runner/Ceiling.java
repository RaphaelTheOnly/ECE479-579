package org.Group7_FinalProject.Runner;

public class Ceiling extends Sprite {
    
    public Ceiling(int x, int y) {
    	
    	super(x, y);
        loadImage("src/resources/ceiling.png");
        
    }
	
    @Override
	public void move() {
    	//Ceiling doesn't move, but implementing method is necessary as a subclass
    }
	
}