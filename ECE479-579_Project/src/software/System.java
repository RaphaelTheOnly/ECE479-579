package software;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import hardware.*;

/*
 * The System class controls all primary behavior related to the AI.
 */
public class System extends JPanel implements ActionListener {
	private Timer timer;
	private Bottle dispBottle;
	private List<Bottle> fullBottles;
	private List<Bottle> emtyBottles;
	private Image image; //for background image
	private final int B_WIDTH = 400;
    private final int B_HEIGHT = 300;
    private final int DELAY = 15; //10 or 15 or
    
	
	//Default no-arg constructor
	public System() {
		initSystem();
	}
	
	private void initSystem() {

        setFocusable(true);
        setBackground(Color.BLACK);
        
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		
        timer = new Timer(DELAY, this);
        timer.start();
        
        ImagePanel();
    }
		
	public void ImagePanel() {
        ImageIcon ii = new ImageIcon("src/resources/background.jpeg");
        image = ii.getImage(); 
    } 
	
	
	  @Override public void paintComponent(Graphics g) { super.paintComponent(g);
	  g.drawImage(image, 0, 0, this); // image background
	  

	  drawObjects(g);
	  
	  
	  Toolkit.getDefaultToolkit().sync(); }
	  
	  private void drawObjects(Graphics g) {
	  }
	  
	  //g.setColor(Color.WHITE); g.drawString("Depth: " + runner.getY(), 5, 15); }
	  
	  private void drawGameOver(Graphics g) {
	  
	  String msg = "Game Over"; Font small = new Font("Helvetica", Font.BOLD, 14);
	  FontMetrics fm = getFontMetrics(small);
	  
	  g.setColor(Color.white); g.setFont(small); g.drawString(msg, (B_WIDTH -
	  fm.stringWidth(msg)) / 2, B_HEIGHT / 2); }
	 
	
	@Override
    public void actionPerformed(ActionEvent e) {


        repaint();
        
    }
	
	
	

}
