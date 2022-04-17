package org.Group7_FinalProject.Runner;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Runner runner;
    private Ceiling ceiling;
	private List<Obstacle> obstacles;
	private List<ObstacleLeft> obstaclesLeft;
	private boolean ingame;
	private int prevStuck;
	private final int IRUNNER_X = 40;
    private final int IRUNNER_Y = 60;
    private final int B_WIDTH = 400;
    private final int B_HEIGHT = 300;
    private final int DELAY = 15; //10 or 15 or
    private Image image; //for background image
    
    private final int[][] pos = { // spawn positions
        {450, 250}, {450, 750}
    };
    private final int[][] posLeft = { // spawn positions
            {0, 500} //, {0, 0}
        };

    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        ingame = true;
        
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        runner = new Runner(IRUNNER_X, IRUNNER_Y);
        ceiling = new Ceiling(0, 0);

		initObstacles();
		
        timer = new Timer(DELAY, this);
        timer.start();
        
        ImagePanel();
    }
	
	public void initObstacles() {
        
        obstacles = new ArrayList<>();
        obstaclesLeft = new ArrayList<>();

        for (int[] p : pos) {
            obstacles.add(new Obstacle(p[0], p[1]));
        }
        for (int[] p : posLeft) {
            obstaclesLeft.add(new ObstacleLeft(p[0], p[1]));
        }
    }
    
    public void ImagePanel() {
        ImageIcon ii = new ImageIcon("src/resources/background.jpeg");
        image = ii.getImage(); 
    } 
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // image background
        
        if (ingame) {

            drawObjects(g);

        } else {

            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {

        if (runner.isVisible()) {
            g.drawImage(runner.getImage(), runner.getX(), runner.getY(), this);
        }
        if (ceiling.isVisible()) {
            g.drawImage(ceiling.getImage(), ceiling.getX(), ceiling.getY(), this);
        }
        
        for (Obstacle obstacle : obstacles) {
            if (obstacle.isVisible()) {
                g.drawImage(obstacle.getImage(), obstacle.getX(), obstacle.getY(), this);
            }
        }
        
        for (ObstacleLeft obstacleLeft : obstaclesLeft) {
            if (obstacleLeft.isVisible()) {
                g.drawImage(obstacleLeft.getImage(), obstacleLeft.getX(), obstacleLeft.getY(), this);
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Depth: " + runner.getY(), 5, 15);
    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }
	
    
    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        updateRunner();
        updateCeiling();
        updateObstacles();
        updateObstaclesLeft();

        checkCollisions();

        repaint();
        
        prevStuck -= 1;
    }
	
	private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

    private void updateRunner() {

        if (runner.isVisible()) {
            
            runner.move();
        }
    }
    
    private void updateCeiling() {

        if (ceiling.isVisible()) {
            
            ceiling.move();
        }
    }

	private void updateObstacles() {

        if (obstacles.isEmpty()) {

            ingame = false;
            return;
        }

        for (int i = 0; i < obstacles.size(); i++) {

            Obstacle a = obstacles.get(i);
            
            if (a.isVisible()) {
                a.move();
            } else {
                obstacles.remove(i);
            }
        }
    }
	
	private void updateObstaclesLeft() {

        if (obstaclesLeft.isEmpty()) {

            ingame = false;
            return;
        }

        for (int i = 0; i < obstaclesLeft.size(); i++) {

            ObstacleLeft a = obstaclesLeft.get(i);
            
            if (a.isVisible()) {
                a.move();
            } else {
                obstaclesLeft.remove(i);
            }
        }
    }

    public void checkCollisions() {

        Rectangle r3 = runner.getBounds();
        Rectangle r4 = ceiling.getBounds();
        
        if (r3.intersects(r4)) {
        	
        	runner.setVisible(false);
        	ingame = false;
        }
        
        for (Obstacle obstacle : obstacles) {
            
            Rectangle r2 = obstacle.getBounds();

            if (r3.intersects(r2)) {
            	
            	runner.setMovement(false);
            	prevStuck = 2;
            	//obstacle.setVisible(false);
            }
            else if (prevStuck < 1) {
            	runner.setMovement(true);
            }
        }
        
        for (ObstacleLeft obstacleLeft : obstaclesLeft) {
            
            Rectangle r2 = obstacleLeft.getBounds();

            if (r3.intersects(r2)) {
            	
            	runner.setMovement(false);
                prevStuck = 2;
            	//obstacle.setVisible(false);
            }
            else if (prevStuck < 1) {
            	runner.setMovement(true);
            }
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            runner.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            runner.keyPressed(e);
        }
    }
}