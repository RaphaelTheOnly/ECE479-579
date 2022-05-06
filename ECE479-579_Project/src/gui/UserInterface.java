package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.Border;
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
	private ArrayList<Integer> sequence;
	private char[] bestPath;
	private int temperature;
    private int randNum; // Random integer Used to decide which house needs delivery
    //private String currentState;
    
    public JLabel temperatureLabel;
    public JLabel introToStateLabel;
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
    public JButton stateOnOffBtn;
    public JButton dispense500ozButton;
    public JButton emptyTheDispenserBottleButton;
    public JButton small16ozBtn;
    public JButton medium32ozBtn;
    public JButton large64ozBtn;
    public JButton stopBtn;
    
    //private Timer bottChangeTimer; 
    private Timer temperatureTime; 
	private Timer stateUpdateTimer;
	private Timer bottDeliveryTimer;
    
	// Default constructor
    public UserInterface() {
    	// Call other constructor with argument
    	this(new AISystem(), new ArrayList<Integer>());
    }
    
    // Constructor with parameters
	public UserInterface(AISystem s1, ArrayList<Integer> sequence) {
		aiSystem = s1; // reference to AISystem that is controlling "it all"
		technician = new Technician(this); // This often shows an error that 
		robot = new RobotArm(this);
		this.sequence = sequence;
		temperature = 79;
		randNum = 5; // 5 is not one of the generated random numbers
		// "The constructor Technician(UserInterface) is undefined. Fixable by
		// clicking on "Project" -> "Clean..." in the taskbar
		temperatureTime = new Timer(80, this);
		stateUpdateTimer = new Timer(1000, robot);
		bottDeliveryTimer = new Timer(2000, technician); // ~2 sec timer to deliver bottles
		char places[] = {'S', 'A', 'B', 'C', 'D', 'E'};
 		// print the minimum weighted Hamiltonian Cycle
 		StringBuilder sb = new StringBuilder();
        for(int i : sequence) {
        	if(i < 7) {
        		sb.append(places[i]);
        	}
        }
        String bestPathStr = sb.toString();
        bestPath = bestPathStr.toCharArray();
		
		initUI();
	}

	// Initialize the main GUI
    private void initUI() {
    	
        setFocusable(true);
        setBackground(Color.BLACK);
        
        // Dispenser Bottle Temperature label
        temperatureLabel = new JLabel("<html>Temperature: <font color=blue>" + temperature + " F</font></html>");
        temperatureLabel.setFont(new Font("Arial", Font.BOLD, 18));
        temperatureLabel.setBounds(60,140,280,50); 
 		add(temperatureLabel);
 		
        // State Iteration On/Off Button
        stateOnOffBtn = new JButton("<html><font color=black> Switch State <br> Display On/Off </font> </html>");
        stateOnOffBtn.setBounds(750,290,140,52); 
        //stateOnOffBtn.setFont(new Font("Arial", Font.BOLD, 18));
        stateOnOffBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				robot.switchOnOffStateDisplay();
				repaint();
			}
		});
        this.add(stateOnOffBtn);
        
        // State Display label
 		introToStateLabel = new JLabel("State Representation for Robot Arm");
 		introToStateLabel.setFont(new Font("Arial", Font.BOLD, 16));
 		introToStateLabel.setBounds(690,340,280,50); 
 		introToStateLabel.setVisible(false); // Not visible by default 
 		add(introToStateLabel);
 		
        // State label
        stateLabel = new JLabel();
        //stateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        stateLabel.setBounds(690,380,280,75); // Position of this GUI element via (x, y, width, height)
        stateLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        stateLabel.setOpaque(true);
        stateLabel.setBackground(Color.lightGray);
        stateLabel.setVisible(false); // Not visible by default 
		add(stateLabel);
		
        // Big red font label that the unit has a leak, toggled visible when needed
        leakLabel = new JLabel("<html><font color=red>&nbsp Alarm! ThirstAid detected a leak!<br>&nbsp A technician is on their way!</font></html>");
        leakLabel.setFont(new Font("Arial", Font.BOLD, 36));
        leakLabel.setBounds(270,70,600,140); // Position of this GUI element via (x, y, width, height)
        leakLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        leakLabel.setOpaque(true);
        leakLabel.setBackground(Color.pink);
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
        secHouseLabel.setBounds(40,550,300,80); 
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
        fourthHouseLabel.setBounds(40,650,300,80); 
        fourthHouseLabel.setForeground(Color.black);
		add(fourthHouseLabel);
		
		// Label for fifth house
		fifthHouseLabel = new JLabel();
		fifthHouseLabel.setFont(new Font("Arial", Font.BOLD, 18));
        fifthHouseLabel.setBounds(170,650,300,80); 
        fifthHouseLabel.setForeground(Color.black);
		add(fifthHouseLabel);
		
        this.setLayout(null);
        startTemperatureTimer();
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
		if (!robot.skipStateDisplay) {
			temperatureTime.stop();
			temperature = 42;
			stateLabel.setVisible(true);
			introToStateLabel.setVisible(true);
			stateLabel.setText("<html><font color=black size=5>&nbsp MoveArmEmpty(idle,0)</font><br><font color=darkgray size=4>&nbsp &nbsp P & D: armPos(idle), HE <br>&nbsp &nbsp A: armPos(0)</font></html>");
		}
	}
	
	// Fct for starting the temperature timer
	public void startTemperatureTimer() {
		temperature = 79;
		repaint();
		temperatureTime.start();
	}
	
	// Stop the temperature timer
	public void stopTemperatureTimer() {
		temperature = 42;
		repaint();
		temperatureTime.stop();
	}
	
	// Call this function after the RobotArm is done cycling through all the shelves and bottles
	public void doneRotating() {
		//bottChangeTimer.start();
		introToStateLabel.setVisible(false);
		stateLabel.setVisible(false);
		aiSystem.ReplaceBottle();
		aiSystem.checkBottlesAndFill();
		aiSystem.checkLeaks();
        repaint();
        stateUpdateTimer.restart();
		stateUpdateTimer.stop();
		robot.resetStateInt();
		startTemperatureTimer();
	}
	
	// Fct for starting the deliver-new-bottles timer (for technician)
	public void startBottDelTimer() {
		bottDeliveryTimer.start();
	}
	
	// Generate the random number that chooses which other house needs a bottle delivery
	public void generateRandNum() {
		// Create a random number to simulate the other households
		Random rand = new Random();
		randNum = rand.nextInt(4); // Next integer between 0 and 3 I think
		System.out.println("Random Int value: "+ randNum);
	}
	
	// Simulate the other houses needing bottles delivered
	// This function gets called every time the GUI is "repainted"
	public void checkRandNum4Houses() {
		  // If the random integer from above is 0, then all need delivery. Other if-clauses leave out one of them.
		  System.out.println("randNum = " +randNum);
			if (randNum == 0) {
				secHouseLabel.setText("<html>House " + Character.toString(bestPath[1]) + ":<br>&nbsp &nbsp <font color=red> need </font> </html>");
				thirdHouseLabel.setText("<html>House " + Character.toString(bestPath[2]) + ":<br>&nbsp &nbsp <font color=red> need </font> </html>");
				fourthHouseLabel.setText("<html>House " + Character.toString(bestPath[3]) + ":<br>&nbsp &nbsp <font color=red> need </font> </html>");
				fifthHouseLabel.setText("<html>House" + Character.toString(bestPath[4]) + ":<br>&nbsp &nbsp <font color=red> need </font> </html>");
			}
			else if (randNum == 1) {
				secHouseLabel.setText("<html>House " + Character.toString(bestPath[1]) + ":<br>&nbsp &nbsp <font color=green> good </font> </html>");
				thirdHouseLabel.setText("<html>House " + Character.toString(bestPath[2]) + ":<br>&nbsp &nbsp <font color=red> need </font> </html>");
				fourthHouseLabel.setText("<html>House " + Character.toString(bestPath[3]) + ":<br>&nbsp &nbsp <font color=red> need </font> </html>");
				fifthHouseLabel.setText("<html>House " + Character.toString(bestPath[4]) + ":<br>&nbsp &nbsp <font color=red> need </font> </html>");
			}
			else if (randNum == 2) {
				secHouseLabel.setText("<html>House " + Character.toString(bestPath[1]) + ":<br>&nbsp &nbsp <font color=red> need </font> </html>");
				thirdHouseLabel.setText("<html>House " + Character.toString(bestPath[2]) + ":<br>&nbsp &nbsp <font color=green> good </font> </html>");
				fourthHouseLabel.setText("<html>House " + Character.toString(bestPath[3]) + ":<br>&nbsp &nbsp <font color=red> need </font> </html>");
				fifthHouseLabel.setText("<html>House " + Character.toString(bestPath[4]) + ":<br>&nbsp &nbsp <font color=red> need </font> </html>");
			}
			else if (randNum == 3) {
				secHouseLabel.setText("<html>House " + Character.toString(bestPath[1]) + ":<br>&nbsp &nbsp <font color=red> need </font> </html>");
				thirdHouseLabel.setText("<html>House " + Character.toString(bestPath[2]) + ":<br>&nbsp &nbsp <font color=red> need </font> </html>");
				fourthHouseLabel.setText("<html>House " + Character.toString(bestPath[3]) + ":<br>&nbsp &nbsp <font color=green> good </font> </html>");
				fifthHouseLabel.setText("<html>House " + Character.toString(bestPath[4]) + ":<br>&nbsp &nbsp <font color=red> need </font> </html>");
			}
			else {
				secHouseLabel.setText("<html>House " + Character.toString(bestPath[1]) + ":<br>&nbsp &nbsp <font color=green> good </font> </html>");
				thirdHouseLabel.setText("<html>House " + Character.toString(bestPath[2]) + ":<br>&nbsp &nbsp <font color=green> good </font> </html>");
				fourthHouseLabel.setText("<html>House " + Character.toString(bestPath[3]) + ":<br>&nbsp &nbsp <font color=green> good </font> </html>");
				fifthHouseLabel.setText("<html>House " + Character.toString(bestPath[4]) + ":<br>&nbsp &nbsp <font color=green> good </font> </html>");
			}
	  }
	
	// Fct to re-initialize the AISystem with a fresh set of bottles
	// Might want to replace/edit this when we have the Traveling Salesman
	public void bottlesDelivered() {
		aiSystem.restartSystem();
		randNum = 5; // 5 is outside the random integers generated
		bottDeliveryTimer.restart();
		bottDeliveryTimer.stop();
		technicianLabel.setVisible(false);
		repaint();
		startTemperatureTimer();
	}
	
	// Do all the things necessary when leak fixed
	public void leakFixed() {
		leakLabel.setVisible(false);
		repaint();
		bottDeliveryTimer.restart();
		bottDeliveryTimer.stop();
	}
	
	// Return boolean value whether the technician still thinks there is a leak
	public boolean getTechLeakDetected() {
		return technician.leakDetected;
	}
	
	// Tell the technician that the appliance detected a leak
	public void setTechLeakDetected() {
		technician.leakDetected = true;
	}
	
	// Return the number of empty bottles that the AISystem is keeping track of
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
		temperatureLabel.setText("<html>Temperature: <font color=blue>" + temperature + " F</font></html>");
		checkRandNum4Houses();
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	// This function gets triggered by the UserInterface's timer
	@Override
	public void actionPerformed(ActionEvent e) {
		if (temperature == 42) {
			temperatureTime.restart();
	        temperatureTime.stop();
		}
		else {
			temperature--;
			temperatureLabel.setText("<html>Temperature: <font color=blue>" + temperature + " F</font></html>");
	        repaint();
		}        
	}
}