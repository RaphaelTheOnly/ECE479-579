package org.Group7_FinalProject.Runner;


import java.awt.event.KeyEvent;

import org.Group7_FinalProject.Framework.RunningScreen;

/*
 * The Runner class includes all fields and methods related to a Runner
 */
public class Runner extends Sprite {

	//Fields for a Runner
    private int dx;
    private int dy;
    private final int JUMP_SPEED = 6;
    private final int VERT_SPEED = 3;
    private final int HORIZ_SPEED = 3;
    private boolean jumping;
    private long jumpStart;
    private Integer depth;
    private RunningScreen runningScreen;

    //Constructor that requires three arguments
    public Runner(int x, int y, RunningScreen runningScreen) {
    	
        super(x, y);
        loadImage("src/resources/runner.png");
        this.depth = 0;
        this.runningScreen = runningScreen;
        
    }
	
	@Override
    public void move() {
     	
		if (System.currentTimeMillis() - jumpStart > 250)
			jumping = false;
		
		//Default movement is gravity/free-fall
		if (!jumping) {
			dy = DIFFICULTY + VERT_SPEED;
		}
        
		/*
		 * Now, run a whole bunch of checks to determine if and where the runner
		 * has collided with other objects on the screen. Then set dx and dy appropriately
		 */
     	for (PlaneRight plnRight : runningScreen.getPlanesRight()) {       
            if (Sprite.isCollided(this, plnRight)) {
            	switch(Sprite.getCollisionPosition(this, plnRight)) {
	            	case TOP:
	            		if (dy > 0)
	            			dy = -DIFFICULTY;
	            		break;
	            	case BOTTOM:
	            		if (dy < 0)
	            			dy = 0;
	            		break;
	            	case LEFT:
	            		if (dx > 0)
	            			dx = 0;
	            		break;
	            	case RIGHT:
	            		if (dx < 0)
	            			dx = 0;
	            		break;
	            	default:
	            		//Nothing goes in default, just here to be proper
            	}
            }
        }
        for (PlaneLeft plnLeft : runningScreen.getPlanesLeft()) {         
            if (Sprite.isCollided(this, plnLeft)) {
            	switch(Sprite.getCollisionPosition(this, plnLeft)) {
	            	case TOP:
	            		if (dy > 0)
	            			dy = -DIFFICULTY;
	            		break;
	            	case BOTTOM:
	            		if (dy < 0)
	            			dy = 0;
	            		break;
	            	case LEFT:
	            		if (dx > 0)
	            			dx = 0;
	            		break;
	            	case RIGHT:
	            		if (dx < 0)
	            			dx = 0;
	            		break;
	            	default:
	            		//Nothing goes in default, just here to be proper
            	}
            }
        }
        for (Obstacle obst : runningScreen.getObstacles()) {
            if (Sprite.isCollided(this, obst) && obst.isVisible()) {
            	boolean invincible = false;
                for (Invincibility invin : runningScreen.getInvinPowerups()) {             	
                	if (invin.isActive()) {
                		invincible = true;
                		obst.setVisible(false);
                		break;
                	}
                }
                if (!invincible) {
	            	switch(Sprite.getCollisionPosition(this, obst)) {
		            	case TOP:
		            		if (dy > 0)
		            			dy = -DIFFICULTY;
		            		break;
		            	case BOTTOM:
		            		if (dy < 0)
		            			dy = 0;
		            		break;
		            	case LEFT:
		            		if (dx > 0)
		            			dx = 0;
		            		break;
		            	case RIGHT:
		            		if (dx < 0)
		            			dx = 0;
		            		break;
		            	default:
		            		//Nothing goes in default, just here to be proper
		        	}
                }
            }
        }
        for (Halt hpu : runningScreen.getHaltPowerups()) {
        	if (Sprite.isCollided(this, hpu) && hpu.isVisible()) {
				if(hpu.isVisible()) {
					hpu.setVisible(false);
					hpu.setActive(true);
					runningScreen.setGameDelay(System.currentTimeMillis());
				}
    		}
        }
        for (Invincibility invin : runningScreen.getInvinPowerups()) {    	
        	if (Sprite.isCollided(this, invin) && invin.isVisible()) {
        		if(invin.isVisible()) {
        			invin.setVisible(false);
        			invin.setActive(true);
				}
        	}
        }
        
        if (dy != -DIFFICULTY)
        	depth = depth + (dy + DIFFICULTY);
        
        //dx and dy have been set to their proper values at this point, update the position of the runner
        x += dx;
        y += dy;
        
        //Implement the "wrapping" behaviour. Should this be removed in final product?
		if (x < 1) {
            x = runningScreen.getWindow().getWidth();
        }
		else if (x > runningScreen.getWindow().getWidth()) {
			x = 0;
		}
		
     	if (y > runningScreen.getWindow().getHeight()) {
        	y = runningScreen.getWindow().getHeight();
        }
     	
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        
        //If the player tries to jump, make sure they are actually standing on something to jump from
        if (key == KeyEvent.VK_SPACE) {
        	boolean standing = false;
         	for (PlaneRight plnRight : runningScreen.getPlanesRight()) {
         		if (Sprite.isCollided(this, plnRight)) {
	                if (Sprite.getCollisionPosition(this, plnRight) == CollisionPosition.TOP) {
	                	standing = true;
	                	break;
	                }
         		}
            }
            for (PlaneLeft plnLeft : runningScreen.getPlanesLeft()) {
            	if (Sprite.isCollided(this, plnLeft)) {
	                if (Sprite.getCollisionPosition(this, plnLeft) == CollisionPosition.TOP) {
	                	standing = true;
	                	break;
	                }
            	}
            }
            for (Obstacle obst : runningScreen.getObstacles()) {
            	if (Sprite.isCollided(this, obst) && obst.isVisible()) {
	                if (Sprite.getCollisionPosition(this, obst) == CollisionPosition.TOP) {
	                	standing = true;
	                	break;
	                }
            	}
            }
            if (standing) {
	        	jumping = true;
	        	jumpStart = System.currentTimeMillis();
	        	dy = -DIFFICULTY - JUMP_SPEED;
            }
        }

        //Move left
        if (key == KeyEvent.VK_LEFT) {
        	dx = -HORIZ_SPEED;
        	if ((DIFFICULTY * 3) > HORIZ_SPEED) {
        		dx = -DIFFICULTY * 3;
        	}
        }
        
        //Move right
        if (key == KeyEvent.VK_RIGHT) {
        	dx = HORIZ_SPEED;
        	if ((DIFFICULTY * 3) > HORIZ_SPEED) {
        		dx = DIFFICULTY * 3;
        	}
        }
        
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_SPACE) {
        	jumping = false;
        }
        
        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
        
    }

	/**
	 * @return the dx
	 */
	public int getDx() {
		return dx;
	}

	/**
	 * @param dx the dx to set
	 */
	public void setDx(int dx) {
		this.dx = dx;
	}

	/**
	 * @return the dy
	 */
	public int getDy() {
		return dy;
	}

	/**
	 * @param dy the dy to set
	 */
	public void setDy(int dy) {
		this.dy = dy;
	}

	/**
	 * @return the jumping
	 */
	public boolean isJumping() {
		return jumping;
	}

	/**
	 * @param jumping the jumping to set
	 */
	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	/**
	 * @return the jumpStart
	 */
	public long getJumpStart() {
		return jumpStart;
	}

	/**
	 * @param jumpStart the jumpStart to set
	 */
	public void setJumpStart(long jumpStart) {
		this.jumpStart = jumpStart;
	}

	/**
	 * @return the depth
	 */
	public Integer getDepth() {
		return depth;
	}

	/**
	 * @param depth the depth to set
	 */
	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	/**
	 * @return the runningScreen
	 */
	public RunningScreen getRunningScreen() {
		return runningScreen;
	}

	/**
	 * @param runningScreen the runningScreen to set
	 */
	public void setRunningScreen(RunningScreen runningScreen) {
		this.runningScreen = runningScreen;
	}

	/**
	 * @return the jUMP_SPEED
	 */
	public int getJUMP_SPEED() {
		return JUMP_SPEED;
	}

	/**
	 * @return the vERT_SPEED
	 */
	public int getVERT_SPEED() {
		return VERT_SPEED;
	}

	/**
	 * @return the hORIZ_SPEED
	 */
	public int getHORIZ_SPEED() {
		return HORIZ_SPEED;
	}
    
}
