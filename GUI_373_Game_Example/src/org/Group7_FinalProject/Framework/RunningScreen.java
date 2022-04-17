package org.Group7_FinalProject.Framework;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.Group7_FinalProject.Runner.*;

/*
 * The RunningScreen class includes all fields and methods related to a RunningScreen
 */
public class RunningScreen extends GameScreen implements ActionListener, KeyListener {

	//Fields for a RunningScreen
    private Timer gameTimer;
    private Timer difficultyTimer;
    private Runner runner;
    private Ceiling ceiling;
	private List<PlaneRight> planesRight;
	private List<PlaneLeft> planesLeft;
	private List<Obstacle> obstacles;
	private List<Halt> haltPowerups;
	private List<Invincibility> invinPowerups;
	private Random rand;
	private boolean runnerDead;
	private boolean runnerPaused;
	private long gameDelay;
    
	private final int[][] posPlnsRt = { // spawn positions for right planes
	        {450, 150}, {450, 550}
    };
    private final int[][] posPlnsLft = { // spawn positions for left planes
            {0, 350}, {0, 750}
    };
    private final int[][] posObst = { // spawn positions for obstacles
    		{80, 300}, {120, 300}, {500, 100}, {580, 500}, {40, 700}, {110, 700} 
    };
    private final int[][] posHalt = { // spawn positions for halt powerups
            {80, 290}, {250, 690}, {470, 90}, {780, 90}, {780, 490}
    };
    private final int[][] posInvin = { // spawn positions invincibility powerups
            {10, 290}, {50, 690}, {550, 90}, {750, 90}, {760, 490}
    };
    

    //Default no-arg constructor
    public RunningScreen() {
    	
    	this(new Window());
    }

    //Constructor that takes one argument
    public RunningScreen(Window w) {
    	
    	//Load the background image
    	super(w, new ImageIcon("src/resources/background.jpeg"));

    	//Initialize objects
        runner = new Runner(0, 0, this);
        ceiling = new Ceiling(0, 0);
        planesRight = new ArrayList<>();
        planesLeft = new ArrayList<>();
        obstacles = new ArrayList<>();
        haltPowerups = new ArrayList<>();
        invinPowerups = new ArrayList<>();
		rand = new Random();
		rand.setSeed(System.currentTimeMillis());
        gameDelay = 0;
        
        //The timer handles animation with ActionListener, the KeyListener handles keyboard input
        gameTimer = new Timer(15, this); //every 15 ms, actionPerformed() executed
        difficultyTimer = new Timer(20000, new Difficulty());  //every 5 seconds, difficulty increased
        addKeyListener(this);
        
    }
    
    /*
     * Method that draws everything on the screen, including background image and sprites
     * This method is called at the end of actionPerformed() via repaint()
     * actionPerformed() is tied to a 15ms timer, so this method will execute every 15ms
     */
    @Override
    public void paintComponent(Graphics g) {
    	
    	//Draw the background image
        super.paintComponent(g);
        
        //Draw all the sprites as long as the player is not dead
        if (!runnerDead)
            drawObjects(g);
        else
            drawGameOver(g);

        Toolkit.getDefaultToolkit().sync();
        
    }

    /*
     * Method that starts the running game
     * Add all method calls necessary to initialize the game here
     * Set all variables to their initial values here
     */
    public void startRunning() {
    	
    	//Request focus so that the KeyListener responds
    	requestFocus();
    	
    	//Initialize the sprites
    	initSprites();
    	runner.setDepth(0);
        runnerDead = false;
        runnerPaused = false;
        Sprite.resetDifficulty();
        gameTimer.start();
        difficultyTimer.start();
		
    }
    
    /*
     * Method that stops the running game
     * Add all method calls necessary to stop the game here
     * Set all variables to their final values here
     */
	private void stopRunning() {
		
		//Stop the animations, cleanup the sprites
		gameTimer.stop();
		difficultyTimer.stop();
		endSprites();
		
	}
	
	//Method that initializes the sprites in their starting locations
	private void initSprites() {

        for (int[] p : posPlnsRt) {
            planesRight.add(new PlaneRight(p[0], p[1], this));
        }
        for (int[] p : posPlnsLft) {
            planesLeft.add(new PlaneLeft(p[0], p[1], this));
        }
        for (int[] p : posObst) {
        	obstacles.add(new Obstacle(p[0], p[1]));
        }
        for (int[] p : posHalt) {
        	haltPowerups.add(new Halt(p[0], p[1]));
        }
        for (int[] p : posInvin) {
            invinPowerups.add(new Invincibility(p[0], p[1]));
        }
        
        //Place the runner in the upper left hand corner
        runner.setPosition(40, 60);
        runner.setVisible(true);
        
    }
	
	//Method that hides or destroys the sprites at the end
	private void endSprites() {
		
		//Obstacles should simply be destroyed, new obstacles will be used in next game
		planesRight.clear();
		planesLeft.clear();
		obstacles.clear();
		haltPowerups.clear();
    	invinPowerups.clear();
		
		//The runner should only be hidden, will be used again in next game
		runner.setVisible(false);
		
	}

	//Method that draws all visible sprite objects on the screen
    private void drawObjects(Graphics g) {

        if (runner.isVisible()) {
            g.drawImage(runner.getImage(), runner.getX(), runner.getY(), this);
        }
        if (ceiling.isVisible()) {
            g.drawImage(ceiling.getImage(), ceiling.getX(), ceiling.getY(), this);
        }
        
        for (PlaneRight plnRt : planesRight) {
            if (plnRt.isVisible()) {
                g.drawImage(plnRt.getImage(), plnRt.getX(), plnRt.getY(), this);
            }
        }
        
        for (PlaneLeft plnLft : planesLeft) {
            if (plnLft.isVisible()) {
                g.drawImage(plnLft.getImage(), plnLft.getX(), plnLft.getY(), this);
            }
        }
        
    	for (Obstacle obst : obstacles) {
            if (obst.isVisible()) {
                g.drawImage(obst.getImage(), obst.getX(), obst.getY(), this);
            }
        }
        
    	for(Halt hpu : haltPowerups) {
        	if(hpu.isVisible()) {
        		g.drawImage(hpu.getImage(), hpu.getX(), hpu.getY(), this);
        	}
    	}
        
    	for(Invincibility invin : invinPowerups) {
        	if(invin.isVisible()) {
        		g.drawImage(invin.getImage(), invin.getX(), invin.getY(), this);
        	}
    	}
        
        g.setColor(Color.WHITE);
        g.drawString("Depth: " + runner.getDepth().toString(), 10, 80);
        
    }

    //Method that draws the "Game Over" message on the screen
    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 40);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.BLACK);
        g.setFont(small);
        g.drawString(msg, (window.getWidth() - fm.stringWidth(msg)) / 2, (window.getHeight() / 2) - 120);
        
    }
	
    /*
     * Implement the actionPerformed() method as part of the ActionListener
     * actionPerformed() is tied to a 15ms timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	//Run all methods that should occur every timer period
        checkRunnerDead();
        checkRunnerPaused();
        updateRunner();
              
        //loop to see if have to update planes, obstacles, runner, etc. based on Halt power up
        boolean update = true;
        for(Halt hpu : haltPowerups) {
        	if(hpu.isActive() == true) {
        		update = false;
        		//Will stop the game timer for 3 seconds
        		if((System.currentTimeMillis() - gameDelay) >= 3000){
        			hpu.setActive(false);
        			update = true;
        		}
        	}
        }
        if(update == true) {
	        updatePlanesRight();
	        updatePlanesLeft();
	        updateObstacles();
	        generatePowerUp(); //also has generate Obstacle
	        updatePowerUps();
        }
        
        repaint();
        
    }
    
    /*
     * Implement all three methods that are part of the KeyListener
     * The appropriate method is fired whenever the game receives keyboard input
     */
	@Override
	public void keyTyped(KeyEvent e) {
		//TODO //Need this??
	}
	@Override
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		
        if(key == KeyEvent.VK_ESCAPE) {
        	runnerPaused = true;
        }

        runner.keyPressed(e);
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		runner.keyReleased(e);
	}
	
	private void checkRunnerDead() {
		
        if (Sprite.isCollided(runner, ceiling)) {
        	runnerDead = true;
        }
        
        if (runnerDead)
        	stopRunning(); 
        
    }
	
	private void checkRunnerPaused() {
		if(runnerPaused) {
			gameTimer.stop();
			difficultyTimer.stop();
			int result = JOptionPane.showConfirmDialog(null, "Game is paused would you like to resume (yes to resume) (no to go back to the menu)", "Pause Menu", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				gameTimer.start();
				difficultyTimer.start();
				runnerPaused = false;
			}
			else {
				runnerDead = true;
			}
		}
	}
	
    private void updateRunner() {
        if (runner.isVisible())
            runner.move();
    }

	private void updatePlanesRight() {
        for (PlaneRight p : planesRight) {
        	p.move();
        }  
    }
	
	private void updatePlanesLeft() {
        for (PlaneLeft p : planesLeft) {
        	p.move();
        }  
    }
	
	private void updatePowerUps() {
		for(Halt hpu : haltPowerups) {		
			hpu.move();
		}
		for(Invincibility invin : invinPowerups) {	
			invin.move();
		}
	}
	
	private void updateObstacles() {
		for (Obstacle obst : obstacles) {
            obst.move();
        }
    }
	
	//TO DO: Turn this method into a static method of the obstacles and powerups classes
	private void generatePowerUp() { //Will show a power up n% of the time, will have a 50-50 chance of what power up will be selected
		
		double occurance = 1; //This means 5% of the time a power up will be shown (1% is a lot btw)
		double rand_double = rand.nextDouble(); //Generates a float between 0.0 -> 1.0
		if(rand_double <= (occurance / 100)) {
			//set visible an obstacle or power up
			if (rand_double > 0.004) {
				
				int i = rand.nextInt(posObst.length); 
				obstacles.get(i).setVisible(true);
			}
			else if (rand_double > 0.003) {
				
				int i = rand.nextInt(posHalt.length); 
				haltPowerups.get(i).setVisible(true);
			}
			else {
				
				int i = rand.nextInt(posInvin.length); 
				invinPowerups.get(i).setVisible(true);
			}
		}
		//else does not show a power up
	}
    
    //This private class increases the difficulty of the game at a scheduled interval
    private class Difficulty implements ActionListener {

    	@Override
    	public void actionPerformed(ActionEvent e) {
    		Sprite.changeDifficulty();
    	}
        
    }

	/**
	 * @return the gameTimer
	 */
	public Timer getGameTimer() {
		return gameTimer;
	}

	/**
	 * @param gameTimer the gameTimer to set
	 */
	public void setGameTimer(Timer gameTimer) {
		this.gameTimer = gameTimer;
	}

	/**
	 * @return the difficultyTimer
	 */
	public Timer getDifficultyTimer() {
		return difficultyTimer;
	}

	/**
	 * @param difficultyTimer the difficultyTimer to set
	 */
	public void setDifficultyTimer(Timer difficultyTimer) {
		this.difficultyTimer = difficultyTimer;
	}

	/**
	 * @return the runner
	 */
	public Runner getRunner() {
		return runner;
	}

	/**
	 * @param runner the runner to set
	 */
	public void setRunner(Runner runner) {
		this.runner = runner;
	}

	/**
	 * @return the ceiling
	 */
	public Ceiling getCeiling() {
		return ceiling;
	}

	/**
	 * @param ceiling the ceiling to set
	 */
	public void setCeiling(Ceiling ceiling) {
		this.ceiling = ceiling;
	}

	/**
	 * @return the planesRight
	 */
	public List<PlaneRight> getPlanesRight() {
		return planesRight;
	}

	/**
	 * @param planesRight the planesRight to set
	 */
	public void setPlanesRight(List<PlaneRight> planesRight) {
		this.planesRight = planesRight;
	}

	/**
	 * @return the planesLeft
	 */
	public List<PlaneLeft> getPlanesLeft() {
		return planesLeft;
	}

	/**
	 * @param planesLeft the planesLeft to set
	 */
	public void setPlanesLeft(List<PlaneLeft> planesLeft) {
		this.planesLeft = planesLeft;
	}

	/**
	 * @return the obstacles
	 */
	public List<Obstacle> getObstacles() {
		return obstacles;
	}

	/**
	 * @param obstacles the obstacles to set
	 */
	public void setObstacles(List<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}

	/**
	 * @return the haltPowerups
	 */
	public List<Halt> getHaltPowerups() {
		return haltPowerups;
	}

	/**
	 * @param haltPowerups the haltPowerups to set
	 */
	public void setHaltPowerups(List<Halt> haltPowerups) {
		this.haltPowerups = haltPowerups;
	}

	/**
	 * @return the invinPowerups
	 */
	public List<Invincibility> getInvinPowerups() {
		return invinPowerups;
	}

	/**
	 * @param invinPowerups the invinPowerups to set
	 */
	public void setInvinPowerups(List<Invincibility> invinPowerups) {
		this.invinPowerups = invinPowerups;
	}

	/**
	 * @return the rand
	 */
	public Random getRand() {
		return rand;
	}

	/**
	 * @param rand the rand to set
	 */
	public void setRand(Random rand) {
		this.rand = rand;
	}

	/**
	 * @return the runnerDead
	 */
	public boolean isRunnerDead() {
		return runnerDead;
	}

	/**
	 * @param runnerDead the runnerDead to set
	 */
	public void setRunnerDead(boolean runnerDead) {
		this.runnerDead = runnerDead;
	}

	/**
	 * @return the runnerPaused
	 */
	public boolean isRunnerPaused() {
		return runnerPaused;
	}

	/**
	 * @param runnerPaused the runnerPaused to set
	 */
	public void setRunnerPaused(boolean runnerPaused) {
		this.runnerPaused = runnerPaused;
	}

	/**
	 * @return the gameDelay
	 */
	public long getGameDelay() {
		return gameDelay;
	}

	/**
	 * @param gameDelay the gameDelay to set
	 */
	public void setGameDelay(long gameDelay) {
		this.gameDelay = gameDelay;
	}

	/**
	 * @return the posPlnsRt
	 */
	public int[][] getPosPlnsRt() {
		return posPlnsRt;
	}

	/**
	 * @return the posPlnsLft
	 */
	public int[][] getPosPlnsLft() {
		return posPlnsLft;
	}

	/**
	 * @return the posObst
	 */
	public int[][] getPosObst() {
		return posObst;
	}

	/**
	 * @return the posHalt
	 */
	public int[][] getPosHalt() {
		return posHalt;
	}

	/**
	 * @return the posInvin
	 */
	public int[][] getPosInvin() {
		return posInvin;
	}

}
