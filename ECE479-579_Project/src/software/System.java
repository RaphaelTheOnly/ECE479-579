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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

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
	private final int B_WIDTH = 1037;
    private final int B_HEIGHT = 850;
    private final int DELAY = 15; //10 or 15 or
    
    private JButton smallBtn;
    private JButton mediumBtn;
    private JButton largeBtn;
    private JButton stopBtn;
    private JButton almostEmtyBtn;
    private JButton emtyBtn;
    
	
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
        
        String text = "Start";
        smallBtn = new JButton(text);
        smallBtn.setFont(new Font("Arial", Font.BOLD, 18));
        smallBtn.setBounds(162,396,100,50); 
        smallBtn.setContentAreaFilled(true);
        smallBtn.setBorderPainted(false);
        smallBtn.setBorder(new LineBorder(Color.black));
        smallBtn.setForeground(Color.black);
        smallBtn.addMouseListener(new MouseAdapter(){
        	@Override
		    public void mousePressed(MouseEvent e) {
        		smallBtn.setBorderPainted(true);
		    }
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	smallBtn.setBorderPainted(false);
		    }
		});
        
    }
		
	public void ImagePanel() {
        ImageIcon ii = new ImageIcon("src/resources/ThirstAid2000.JPG");
        image = ii.getImage(); 
    } 
	
	
	  @Override public void paintComponent(Graphics g) { super.paintComponent(g);
	  g.drawImage(image, 0, 0, this); // image background
	  

	  //drawObjects(g);
	  
	  
	  Toolkit.getDefaultToolkit().sync(); 
	  
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
