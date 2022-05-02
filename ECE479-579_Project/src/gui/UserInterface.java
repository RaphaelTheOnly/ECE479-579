package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;

import hardware.RobotArm;
import software.AISystem;
import people.Technician;

// The JPanel is subordinate to the JFrame, ActionListener is needed for the Timers
public class UserInterface extends JPanel implements ActionListener {

	private AISystem aiSystem;
	private Technician technician;
	private RobotArm robot;
	private Image image; // For background image
	private final int B_WIDTH = 1037;
    private final int B_HEIGHT = 850;
    //private String currentState;
    
    public JLabel stateLabel;
    public JLabel leakLabel;
    public JLabel fillLabel;
    public JLabel fullBottlesLabel;
    public JLabel emptyBottlesLabel;
    public JLabel technicianLabel;
    public JLabel secHouseLabel;
    public JLabel thirdHouseLabel;
    public JLabel fourthHouseLabel;
    public JLabel fifthHouseLabel;
    public JButton dispense500ozButton;
    public JButton emptyTheDispenserBottleButton;
    public JButton small16ozBtn;
    public JButton medium32ozBtn;
    public JButton large64ozBtn;
    public JButton stopBtn;
    
    private Timer bottChangeTimer; 
	private Timer bottDeliveryTimer; 
	private Timer stateUpdateTimer;
    
	// Default constructor
    public UserInterface() {
    	// Call other constructor with argument
    	this(new AISystem());
    }
    
    // Constructor with parameter
	public UserInterface(AISystem s1) {
		aiSystem = s1; // reference to AISystem that is controlling "it all"
		technician = new Technician(this); // This often shows an error that 
		robot = new RobotArm(this);
		// "The constructor Technician(UserInterface) is undefined. Fixable by
		// clicking on "Project" -> "Clean..." in the taskbar
		bottChangeTimer = new Timer(1000, this); // ~1 sec timer to change bottles
		bottDeliveryTimer = new Timer(2000, technician); // ~2 sec timer to deliver bottles
		stateUpdateTimer = new Timer(1000, robot);
		initUI();
	}

    private void initUI() {
    	
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        
        // Blue state label test
        stateLabel = new JLabel();
        stateLabel.setFont(new Font("Arial", Font.BOLD, 18));
        stateLabel.setBounds(700,330,600,300); // Position of this GUI element via (x, y, width, height)
        stateLabel.setForeground(Color.blue);
        stateLabel.setVisible(false); // Not visible by default 
		add(stateLabel);
		
        // Big red font label that the unit has a leak, toggled visible when needed
        leakLabel = new JLabel("<html> <font color=red> ThirstAid detected a leak, a technician is on their way!</font></html>");
        leakLabel.setFont(new Font("Arial", Font.BOLD, 45));
        leakLabel.setBounds(280,80,600,300); // Position of this GUI element via (x, y, width, height)
        leakLabel.setVisible(false); // Not visible by default 
		add(leakLabel);
		
		// Full bottles label
        fullBottlesLabel = new JLabel("Full bottles: " + aiSystem.getNumFull());
        fullBottlesLabel.setFont(new Font("Arial", Font.BOLD, 18));
        fullBottlesLabel.setBounds(60,210,300,50); 
        fullBottlesLabel.setForeground(Color.black);
		add(fullBottlesLabel);
		
		// Empty bottles label
		emptyBottlesLabel = new JLabel("Empty bottles: " + aiSystem.getNumEmpty());
		emptyBottlesLabel.setFont(new Font("Arial", Font.BOLD, 18));
		emptyBottlesLabel.setBounds(60,250,300,50); 
        emptyBottlesLabel.setForeground(Color.black);
		add(emptyBottlesLabel);
		
		// Label of how full dispenser bottle is
        fillLabel = new JLabel("Current fill: " + aiSystem.getDispBFill() + " oz.");
        fillLabel.setFont(new Font("Arial", Font.BOLD, 18));
        fillLabel.setBounds(60,320,300,50); 
        fillLabel.setForeground(Color.black);
		add(fillLabel);
        
		// Button to dispense 500 oz.
		dispense500ozButton = new JButton("Dispense 500 oz.");
		dispense500ozButton.setBounds(60,390,160,32); 
		// ActionListener for clicking on the button
		dispense500ozButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aiSystem.dispense(500); // Dispense 500 oz per click
				// Check if need to call technician and if to replace dispenser bottle
				aiSystem.checkBottlesAndFill();		
				repaint(); // Refresh GUI elements
			}
		});
        this.add(dispense500ozButton); // Add this button to the JPanel
        
        // Button to empty the dispenser bottle
        emptyTheDispenserBottleButton = new JButton("Empty Current Bottle");
        emptyTheDispenserBottleButton.setBounds(60,430,160,32); 
        emptyTheDispenserBottleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aiSystem.dispense(800);
				aiSystem.checkBottlesAndFill();		
				repaint();
			}
		});
        this.add(emptyTheDispenserBottleButton);
        
        // Button to dispense 16 oz.
        small16ozBtn = new JButton();
        small16ozBtn.setFont(new Font("Arial", Font.BOLD, 18));
        small16ozBtn.setBounds(420,337,23,32); 
        small16ozBtn.setContentAreaFilled(false);
        small16ozBtn.setBorderPainted(false);
        small16ozBtn.setBorder(new LineBorder(Color.black));
        small16ozBtn.setForeground(Color.black);
        // This creates the black rectangular when user clicks on translucent button
        small16ozBtn.addMouseListener(new MouseAdapter(){
        	@Override
		    public void mousePressed(MouseEvent e) {
        		small16ozBtn.setBorderPainted(true);
		    }
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	small16ozBtn.setBorderPainted(false);
		    }
		});
        small16ozBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aiSystem.dispense(16);
				aiSystem.checkBottlesAndFill();			
				repaint();
			}
		});
        this.add(small16ozBtn);
        
        // Button to dispense 32 oz.
        medium32ozBtn = new JButton();
        medium32ozBtn.setFont(new Font("Arial", Font.BOLD, 18));
        medium32ozBtn.setBounds(484,337,23,32); 
        medium32ozBtn.setContentAreaFilled(false);
        medium32ozBtn.setBorderPainted(false);
        medium32ozBtn.setBorder(new LineBorder(Color.black));
        medium32ozBtn.setForeground(Color.black);
        medium32ozBtn.addMouseListener(new MouseAdapter(){
        	@Override
		    public void mousePressed(MouseEvent e) {
        		medium32ozBtn.setBorderPainted(true);
		    }
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	medium32ozBtn.setBorderPainted(false);
		    }
		});
        medium32ozBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aiSystem.dispense(32);
				aiSystem.checkBottlesAndFill();		
				repaint();
			}
		});
        this.add(medium32ozBtn);
        
        // Button to dispense 64 oz.
        large64ozBtn = new JButton();
        large64ozBtn.setFont(new Font("Arial", Font.BOLD, 18));
        large64ozBtn.setBounds(546,335,25,35); 
        large64ozBtn.setContentAreaFilled(false);
        large64ozBtn.setBorderPainted(false);
        large64ozBtn.setBorder(new LineBorder(Color.black));
        large64ozBtn.setForeground(Color.black);
        large64ozBtn.addMouseListener(new MouseAdapter(){
        	@Override
		    public void mousePressed(MouseEvent e) {
        		large64ozBtn.setBorderPainted(true);
		    }
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	large64ozBtn.setBorderPainted(false);
		    }
		});
        large64ozBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aiSystem.dispense(64);
				aiSystem.checkBottlesAndFill();		
				repaint();
			}
		});
        this.add(large64ozBtn);
        
        // Stop button that should stop the dispenser while pouring
        // Not applicable in current simulation
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
        
        // Red colored Label saying technician is on his way
        technicianLabel = new JLabel("New bottles are on their way.");
        technicianLabel.setFont(new Font("Arial", Font.BOLD, 14));
        technicianLabel.setBounds(40,470,220,32); 
        technicianLabel.setForeground(Color.red);
        technicianLabel.setVisible(false);
		add(technicianLabel);

		// Label for second house
		secHouseLabel = new JLabel();
        secHouseLabel.setFont(new Font("Arial", Font.BOLD, 18));
        secHouseLabel.setBounds(20,550,300,80); 
        secHouseLabel.setForeground(Color.black);
		add(secHouseLabel);
		
		// Label for third house
		thirdHouseLabel = new JLabel();
		thirdHouseLabel.setFont(new Font("Arial", Font.BOLD, 18));
        thirdHouseLabel.setBounds(170,550,300,80); 
        thirdHouseLabel.setForeground(Color.black);
		add(thirdHouseLabel);
		
		// Label for fourth house
		fourthHouseLabel = new JLabel();
		fourthHouseLabel.setFont(new Font("Arial", Font.BOLD, 18));
        fourthHouseLabel.setBounds(20,650,300,80); 
        fourthHouseLabel.setForeground(Color.black);
		add(fourthHouseLabel);
		
		// Label for fifth house
		fifthHouseLabel = new JLabel();
		fifthHouseLabel.setFont(new Font("Arial", Font.BOLD, 18));
        fifthHouseLabel.setBounds(170,650,300,80); 
        fifthHouseLabel.setForeground(Color.black);
		add(fifthHouseLabel);
		
        this.setLayout(null);
       
        ImagePanel();
    }
    
    // Function for background image
	public void ImagePanel() {
        ImageIcon ii = new ImageIcon("src/resources/ThirstAid2000.JPG");
        image = ii.getImage(); 
    } 
	
	// Fct for starting the change bottle timer
	public void startBottChangeTimer() {
		//bottChangeTimer.start();
		stateUpdateTimer.start();
		stateLabel.setVisible(true);
		stateLabel.setText("Next Operation: moveArm(0)");
	}
	
	public void doneRotating() {
		bottChangeTimer.start();
		stateLabel.setVisible(false);
		repaint();
		stateUpdateTimer.restart();
		stateUpdateTimer.stop();
		robot.resetStateInt();
		
		
	}
	
	// Fct for starting the deliver-new-bottles timer (for technician)
	public void startBottDelTimer() {
		bottDeliveryTimer.start();
	}
	
	// Fct to re-initialize the AISystem with a fresh set of bottles
	// Might want to replace/edit this when we have the Traveling Salesman
	public void bottlesDelivered() {
		aiSystem.initSystem();
		aiSystem.randNum = 5; // 5 is outside the random integers generated
		bottDeliveryTimer.restart();
		bottDeliveryTimer.stop();
		technicianLabel.setVisible(false);
		repaint();
	}
	
	// Do all the things necesarry when leak fixed
	public void leakFixed() {
		leakLabel.setVisible(false);
		repaint();
		bottDeliveryTimer.restart();
		bottDeliveryTimer.stop();
	}
	
	public void stateChanged() {
		leakLabel.setVisible(false);
		repaint();
		stateUpdateTimer.restart();
		stateUpdateTimer.stop();
	}
	
	public boolean getTechLeakDetected() {
		return technician.leakDetected;
	}
	
	public void setTechLeakDetected() {
		technician.leakDetected = true;
	}
	
	public int getNumEmptyBott() {
		return aiSystem.getNumEmpty();
	}
	
	
	// This function gets called by "repaint()" and is very important for keeping the GUI updated
	@Override 
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this); // Background gets drawn (again)
		
		fullBottlesLabel.setText("Full bottles: " + aiSystem.getNumFull());
		emptyBottlesLabel.setText("Empty bottles: " + aiSystem.getNumEmpty());
		fillLabel.setText("Current fill: " + aiSystem.getDispBFill() + " oz.");
		aiSystem.checkRandNum4Houses();
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	// This function gets triggered by the UI's timer
	@Override
	public void actionPerformed(ActionEvent e) {
		aiSystem.changeBottles();
		aiSystem.checkBottlesAndFill();
		aiSystem.checkLeaks();
        repaint();
        bottChangeTimer.restart();
        bottChangeTimer.stop();
	        
	}
	
}