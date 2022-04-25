package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;

import javax.swing.*;
import javax.swing.border.LineBorder;

import people.Technician;
import software.AISystem;

public class UserInterface extends JPanel implements ActionListener {

	private AISystem system;
	private Image image; //for background image
	private final int B_WIDTH = 1037;
    private final int B_HEIGHT = 850;
    private Timer bottChangeTimer;
    private Timer bottDeliverTimer;
    
    private JLabel fillLabel;
    private JLabel fullBLabel;
    private JLabel mtyBLabel;
    private JLabel technLabel;
    private JButton disp500Btn;
    private JButton emtyBtn;
    private JButton smallBtn;
    private JButton mediumBtn;
    private JButton largeBtn;
    private JButton stopBtn;
    
    public UserInterface() {
    	this(new AISystem());
    }
    
	public UserInterface(AISystem s1) {
		this.system = s1;
		bottChangeTimer = new Timer(1000, this);
		bottDeliverTimer = new Timer(2000, new Technician(this, system));
		initUI();
	}

    private void initUI() {

        setFocusable(true);
        setBackground(Color.BLACK);
        
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		
        fullBLabel = new JLabel("# of full bottles: " + system.getNumFull());
        fullBLabel.setFont(new Font("Arial", Font.BOLD, 18));
        fullBLabel.setBounds(60,210,300,50); 
        fullBLabel.setForeground(Color.black);
		add(fullBLabel);
		
		mtyBLabel = new JLabel("# of empty bottles: " + system.getNumEmpty());
		mtyBLabel.setFont(new Font("Arial", Font.BOLD, 18));
		mtyBLabel.setBounds(60,250,300,50); 
        mtyBLabel.setForeground(Color.black);
		add(mtyBLabel);
		
        fillLabel = new JLabel("Current fill: " + system.getDispBFill() + " oz.");
        fillLabel.setFont(new Font("Arial", Font.BOLD, 18));
        fillLabel.setBounds(60,320,300,50); 
        fillLabel.setForeground(Color.black);
		add(fillLabel);
        
		disp500Btn = new JButton("Dispense 500 oz.");
		disp500Btn.setBounds(60,390,160,32); 
		disp500Btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				system.dispense(500);
				checkFillNBottles();			
				repaint();
			}
		});
        this.add(disp500Btn);
        
        emtyBtn = new JButton("Empty Current Bottle");
        emtyBtn.setBounds(60,430,160,32); 
        emtyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				system.dispense(800);
				checkFillNBottles();		
				repaint();
			}
		});
        this.add(emtyBtn);
        
        smallBtn = new JButton();
        smallBtn.setFont(new Font("Arial", Font.BOLD, 18));
        smallBtn.setBounds(420,337,23,32); 
        smallBtn.setContentAreaFilled(false);
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
        smallBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				system.dispense(16);
				checkFillNBottles();			
				repaint();
			}
		});
        this.add(smallBtn);
        
        mediumBtn = new JButton();
        mediumBtn.setFont(new Font("Arial", Font.BOLD, 18));
        mediumBtn.setBounds(484,337,23,32); 
        mediumBtn.setContentAreaFilled(false);
        mediumBtn.setBorderPainted(false);
        mediumBtn.setBorder(new LineBorder(Color.black));
        mediumBtn.setForeground(Color.black);
        mediumBtn.addMouseListener(new MouseAdapter(){
        	@Override
		    public void mousePressed(MouseEvent e) {
        		mediumBtn.setBorderPainted(true);
		    }
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	mediumBtn.setBorderPainted(false);
		    }
		});
        mediumBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				system.dispense(32);
				checkFillNBottles();		
				repaint();
			}
		});
        this.add(mediumBtn);
        
        largeBtn = new JButton();
        largeBtn.setFont(new Font("Arial", Font.BOLD, 18));
        largeBtn.setBounds(546,335,25,35); 
        largeBtn.setContentAreaFilled(false);
        largeBtn.setBorderPainted(false);
        largeBtn.setBorder(new LineBorder(Color.black));
        largeBtn.setForeground(Color.black);
        largeBtn.addMouseListener(new MouseAdapter(){
        	@Override
		    public void mousePressed(MouseEvent e) {
        		largeBtn.setBorderPainted(true);
		    }
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	largeBtn.setBorderPainted(false);
		    }
		});
        largeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				system.dispense(64);
				checkFillNBottles();		
				repaint();
			}
		});
        this.add(largeBtn);
        
        stopBtn = new JButton();
        stopBtn.setFont(new Font("Arial", Font.BOLD, 18));
        stopBtn.setBounds(592,335,23,32); 
        stopBtn.setContentAreaFilled(false);
        stopBtn.setBorderPainted(false);
        stopBtn.setBorder(new LineBorder(Color.black));
        stopBtn.setForeground(Color.black);
        stopBtn.addMouseListener(new MouseAdapter(){
        	@Override
		    public void mousePressed(MouseEvent e) {
        		stopBtn.setBorderPainted(true);
		    }
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	stopBtn.setBorderPainted(false);
		    }
		});
        this.add(stopBtn);
        
        technLabel = new JLabel("New bottles are on their way.");
        technLabel.setFont(new Font("Arial", Font.BOLD, 14));
        technLabel.setBounds(40,470,220,32); 
        technLabel.setForeground(Color.red);
        technLabel.setVisible(false);
		add(technLabel);

        this.setLayout(null);
       
        ImagePanel();
    }
    
    public void checkFillNBottles() {
    	System.out.println("# of empty bottles: " + system.getNumEmpty());
    	if (system.getNumEmpty() == 3) {
    		System.out.println("in 3 empty");
    		technLabel.setVisible(true);
    		bottDeliverTimer.start();
    		repaint();
    	}
    	
    	if (system.getDispBFill() == 0) {
			System.out.println("DispFill = " +system.getDispBFill());
			bottChangeTimer.start();
		}
    }
		
	public void ImagePanel() {
        ImageIcon ii = new ImageIcon("src/resources/ThirstAid2000.JPG");
        image = ii.getImage(); 
    } 
	
	
	@Override 
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this); // image background
		
		fullBLabel.setText("# of full bottles: " + system.getNumFull());
		mtyBLabel.setText("# of empty bottles: " + system.getNumEmpty());
		fillLabel.setText("Current fill: " + system.getDispBFill() + " oz.");
		if (system.getNumFull() > 1) {
			technLabel.setVisible(false);
		}
		//drawObjects(g);
		
		Toolkit.getDefaultToolkit().sync();
	}
	  
	  
	  
	  //g.setColor(Color.WHITE); g.drawString("Depth: " + runner.getY(), 5, 15); }
	  
	  private void drawGameOver(Graphics g) {
	  
	  String msg = "Game Over"; Font small = new Font("Helvetica", Font.BOLD, 14);
	  FontMetrics fm = getFontMetrics(small);
	  
	  g.setColor(Color.white); g.setFont(small); g.drawString(msg, (B_WIDTH -
	  fm.stringWidth(msg)) / 2, B_HEIGHT / 2); 
	  
	  }
	
	@Override
    public void actionPerformed(ActionEvent e) {

		//checkFill();
		/*
		 * if (system.getDispBFill() == 0) { System.out.println("DispFill = "
		 * +system.getDispBFill()); system.changeBottles(); }
		 */
		system.changeBottles();
		checkFillNBottles();
		System.out.println("b4 repaint()");
        repaint();
        bottChangeTimer.restart();
        bottChangeTimer.stop();
        
    }
}