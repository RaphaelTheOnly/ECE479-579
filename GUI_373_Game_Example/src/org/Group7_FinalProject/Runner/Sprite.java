package org.Group7_FinalProject.Runner;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import org.Group7_FinalProject.Framework.RunningScreen;

//Sprite class contains all methods and fields related to a Sprite
public abstract class Sprite {

	//Fields for a Sprite
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;
    protected static int DIFFICULTY = 0;
    public static enum CollisionPosition { TOP, BOTTOM, LEFT, RIGHT, NONE };

    //Constructor that requires two arguments
    public Sprite(int x, int y) {

        this.x = x;
        this.y = y;
        visible = true;
        
    }
    
    //Method that resets the speed at which a sprite moves
    public static void resetDifficulty() {
    	DIFFICULTY = 0;
    }
    
    //Method that increases the speed at which a sprite moves
    public static void changeDifficulty() {
    	DIFFICULTY += 1;
    }
    
    //Method that checks if two sprites overlap each other on the screen
    public static boolean isCollided(Sprite s1, Sprite s2) {
    	Rectangle s1bounds = s1.getBounds();
    	Rectangle s2bounds = s2.getBounds();
    	
    	if (s1bounds.intersects(s2bounds)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    /*
     * Method that determines the relative location of two sprites when they collide
     * Note that this method assumes that a collision actually exists, so it should
     * only be called after verifying a collision using the isCollided() method
     * Otherwise, the method will return NONE
     */
    public static CollisionPosition getCollisionPosition(Sprite s1, Sprite s2) {
    	
    	if ((s1.getY() + s1.getHeight() > s2.getY() + 10) && (s1.getY() < s2.getY() + s2.getHeight() - 10)) {
    		if (s1.getX() < s2.getX()) {
	    		return CollisionPosition.LEFT;
    		}
    		else {
    			return CollisionPosition.RIGHT;
    		}
    	}
    	else if ((s1.getX() + s1.getWidth() > s2.getX() + 10) && (s1.getX() < s2.getX() + s2.getWidth() - 10)) {
    		if (s1.getY() < s2.getY()) {
    			return CollisionPosition.TOP;
    		}
    		else {
    			return CollisionPosition.BOTTOM;
    		}
    	}
    	else {
    		return CollisionPosition.NONE;
    	}
    	
    }
    
    protected abstract void move();

    protected void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
        
    }

    public Image getImage() {
        return image;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setPosition(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
}