package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;

import software.AISystem;
import people.Technician;


public class UserInterface extends JPanel implements ActionListener {

	private AISystem aiSystem;
	public Technician technician;
	private Image image; //for background image
	private final int B_WIDTH = 1037;
    private final int B_HEIGHT = 850;
    
    public JLabel leakLabel;
    public JLabel fillLabel;
    public JLabel fullBLabel;
    public JLabel mtyBLabel;
    public JLabel technLabel;
    public JLabel secHouseLabel;
    public JLabel thiHouseLabel;
    public JLabel fouHouseLabel;
    public JLabel fifHouseLabel;
    public JButton disp500Btn;
    public JButton emtyBtn;
    public JButton smallBtn;
    public JButton mediumBtn;
    public JButton largeBtn;
    public JButton stopBtn;
    
    private Timer bottChangeTimer;
	private Timer bottDeliveryTimer;
    
    public UserInterface() {
    	this(new AISystem());
    }
    
	public UserInterface(AISystem s1) {
		aiSystem = s1;
		technician = new Technician(this);
		bottChangeTimer = new Timer(1000, this);
		bottDeliveryTimer = new Timer(2000, technician);
		initUI();
	}

    private void initUI() {
    	
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		
        leakLabel = new JLabel("<html> <font color=red> ThirstAid detected a leak, a technician is on their way!</font></html>");
        leakLabel.setFont(new Font("Arial", Font.BOLD, 45));
        leakLabel.setBounds(280,80,600,300);
        leakLabel.setVisible(false);
		add(leakLabel);
		
        fullBLabel = new JLabel("Full bottles: " + aiSystem.getNumFull());
        fullBLabel.setFont(new Font("Arial", Font.BOLD, 18));
        fullBLabel.setBounds(60,210,300,50); 
        fullBLabel.setForeground(Color.black);
		add(fullBLabel);
		
		mtyBLabel = new JLabel("Empty bottles: " + aiSystem.getNumEmpty());
		mtyBLabel.setFont(new Font("Arial", Font.BOLD, 18));
		mtyBLabel.setBounds(60,250,300,50); 
        mtyBLabel.setForeground(Color.black);
		add(mtyBLabel);
		
        fillLabel = new JLabel("Current fill: " + aiSystem.getDispBFill() + " oz.");
        fillLabel.setFont(new Font("Arial", Font.BOLD, 18));
        fillLabel.setBounds(60,320,300,50); 
        fillLabel.setForeground(Color.black);
		add(fillLabel);
        
		disp500Btn = new JButton("Dispense 500 oz.");
		disp500Btn.setBounds(60,390,160,32); 
		disp500Btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aiSystem.dispense(500);
				aiSystem.checkFillNBottles();			
				repaint();
			}
		});
        this.add(disp500Btn);
        
        emtyBtn = new JButton("Empty Current Bottle");
        emtyBtn.setBounds(60,430,160,32); 
        emtyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aiSystem.dispense(800);
				aiSystem.checkFillNBottles();		
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
				aiSystem.dispense(16);
				aiSystem.checkFillNBottles();			
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
				aiSystem.dispense(32);
				aiSystem.checkFillNBottles();		
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
				aiSystem.dispense(64);
				aiSystem.checkFillNBottles();		
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

		secHouseLabel = new JLabel();
        secHouseLabel.setFont(new Font("Arial", Font.BOLD, 18));
        secHouseLabel.setBounds(20,550,300,80); 
        secHouseLabel.setForeground(Color.black);
		add(secHouseLabel);
		
		thiHouseLabel = new JLabel();
		thiHouseLabel.setFont(new Font("Arial", Font.BOLD, 18));
        thiHouseLabel.setBounds(170,550,300,80); 
        thiHouseLabel.setForeground(Color.black);
		add(thiHouseLabel);
		
		fouHouseLabel = new JLabel();
		fouHouseLabel.setFont(new Font("Arial", Font.BOLD, 18));
        fouHouseLabel.setBounds(20,650,300,80); 
        fouHouseLabel.setForeground(Color.black);
		add(fouHouseLabel);
		
		fifHouseLabel = new JLabel();
		fifHouseLabel.setFont(new Font("Arial", Font.BOLD, 18));
        fifHouseLabel.setBounds(170,650,300,80); 
        fifHouseLabel.setForeground(Color.black);
		add(fifHouseLabel);
		
        this.setLayout(null);
       
        ImagePanel();
    }
    
	public void ImagePanel() {
        ImageIcon ii = new ImageIcon("src/resources/ThirstAid2000.JPG");
        image = ii.getImage(); 
    } 
	
	public void startBottChangeTimer() {
		bottChangeTimer.start();
	}
	
	public void startBottDelTimer() {
		bottDeliveryTimer.start();
	}
	
	public void restartAISystem() {
		aiSystem.initSystem();
		aiSystem.randNum = 5;
		repaint();
		bottDeliveryTimer.restart();
		bottDeliveryTimer.stop();
	}
	
	public void leakFixed() {
		leakLabel.setVisible(false);
		repaint();
		bottDeliveryTimer.restart();
		bottDeliveryTimer.stop();
	}
	
	public boolean getTechLeakDetected() {
		return technician.leakDetected;
	}
	
	public void setTechLeakDetected() {
		technician.leakDetected = true;
	}
	
	public int getNumFullBott() {
		return aiSystem.getNumFull();
	}
	
	@Override 
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this); // image background
		
		//checkLeaks();
		
		fullBLabel.setText("Full bottles: " + aiSystem.getNumFull());
		mtyBLabel.setText("Empty bottles: " + aiSystem.getNumEmpty());
		fillLabel.setText("Current fill: " + aiSystem.getDispBFill() + " oz.");
		aiSystem.checkRandNum4Houses();
		
		//drawObjects(g);
		
		Toolkit.getDefaultToolkit().sync();
	}
	  
	@Override
	  public void actionPerformed(ActionEvent e) {

			/*
			 * if (system.getDispBFill() == 0) { System.out.println("DispFill = "
			 * +system.getDispBFill()); system.changeBottles(); }
			 */
			aiSystem.changeBottles();
			aiSystem.checkFillNBottles();
			aiSystem.checkLeaks();
			//System.out.println("b4 repaint()");
	        repaint();
	        bottChangeTimer.restart();
	        bottChangeTimer.stop();
	        
	  }
	
}