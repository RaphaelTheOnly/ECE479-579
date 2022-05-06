package software;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;

import gui.UserInterface;
import hardware.*;
	
// The AISystem class controls all primary behavior related to the AI.
public class AISystem extends JFrame {
	  private TSP tsp;
	  private UserInterface ui; // Instance of the UserInterface that the AISystem can refer to
	  private Bottle dispBottle; // Bottle in the dispenser, so the one currently used
	  private ArrayList<Bottle> fullBottles; // Indexable list of full bottles
	  private ArrayList<Bottle> emtyBottles; // Indexable list of empty bottles
	  private RobotArm robot;
	  private int numFull; // Number of full bottles in our unit
	  private int numEmpty; // Number of empty bottles in our unit
	  
	  // Default Constructor
	  public AISystem() { 
		  initSystem(); // a function
	  }
	  
	  // Initializes the ArrayLists of full and empty bottles
	  public void initSystem() {
		  // Check if there's a bottle in dispenser and how full it is
		  boolean dispExist = false; // No bottle in dispenser
		  if (dispExist == true) { // If there's already a bottle in dispenser, 
			  int dispFill = 80;  // then determine how full it is
			  dispBottle = new Bottle(dispFill); 
		  } 
		  else dispBottle = null; // If no bottle in dispenser, initialize it as non-existent (null)
		  
		  // This part will also be replaced when the Traveling Salesman fills the system initially
		  // But for now, simulate a search of how many bottle are full and how many empty 
		  // For now: 3 full, 2 empty 
		  numFull = 4; 
		  fullBottles = new ArrayList<Bottle>(); 
		  for (int i = 0; i < numFull; i++) { // Fill the ArrayList with 4 full bottles
			  Bottle fullBot = new Bottle(100);
			  fullBottles.add(fullBot); 
		  }
		  
		  numEmpty = 0; // No empty bottles found
		  emtyBottles = new ArrayList<Bottle>(); 
		  for (int i = 0; i < numEmpty; i++) { 
			  Bottle mtyBot = new Bottle(0); 
			  emtyBottles.add(mtyBot); 
		  }
		  
		  if (numFull > 0 && dispBottle == null) { // If no bottle in dispenser, put one in
			  //put the top full into dispenser
			  fullBottles.remove(0); 
			  dispBottle = new Bottle(100); 
			  numFull = fullBottles.size(); 
		  } 
		  else if (numFull > 0 && dispBottle.getOunces() < 10) {
			  //replace dispenser bottle if less than 10 onces in it
			  ReplaceBottle(); 
		  } 
		  //else nothing changes
		  createInitialTSPUI(); // Launches the TSP windows first
		  //createMainUI(); // Launches the ThirstAid 2000 window first
	  }
	  
	  // Call the TSP class for the traveling salesman AIPS
	  private void createInitialTSPUI() {
		  tsp = new TSP(this); // Create instance of UserInterface
		  
		  add(tsp); // UserInterface is a JPanel and it gets added here to the AISystem's JFrame
		  
		  setTitle("Initial Traveling Salesman Approach"); // Title of the GUI window
		  setSize(800, 500); // Width, Height of window
		  
		  setLocationRelativeTo(null); 
		  setResizable(false);
		  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  }
	  
	  // Create a window for the main GUI that has all the dispenser buttons and the events
	  public void createMainUI(ArrayList<Integer> sequence) {
		  tsp.setVisible(false); // make invisible the TSP window
		  this.remove(tsp); // remove the TSP window
		  ui = new UserInterface(this, sequence); // Create instance of UserInterface 
		  
		  add(ui); // UserInterface is a JPanel and it gets added here to the AISystem's JFrame
		  
		  setTitle("ThirstAI"); // Title of the GUI window
		  setSize(1037, 850); // Width, Height of window
		  
		  setLocationRelativeTo(null); 
		  setResizable(false);
		  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  }
	  
	  // Restart the system once the technician is done replacing the bottles
	  public void restartSystem() {
		  boolean dispExist = false; // No bottle in dispenser
		  if (dispExist == true) { // If there's already a bottle in dispenser, 
			  int dispFill = 80;  // then determine how full it is
			  dispBottle = new Bottle(dispFill); 
		  } 
		  else dispBottle = null; // If no bottle in dispenser, initialize it as non-existent (null)
		  
		  // This part will also be replaced when the Traveling Salesman fills the system initially
		  // But for now, simulate a search of how many bottle are full and how many empty 
		  // For now: 3 full, 2 empty 
		  numFull = 4; 
		  fullBottles = new ArrayList<Bottle>(); 
		  for (int i = 0; i < numFull; i++) { // Fill the ArrayList with 4 full bottles
			  Bottle fullBot = new Bottle(100);
			  fullBottles.add(fullBot); 
		  }
		  
		  numEmpty = 0; // No empty bottles found
		  emtyBottles = new ArrayList<Bottle>(); 
		  for (int i = 0; i < numEmpty; i++) { 
			  Bottle mtyBot = new Bottle(0); 
			  emtyBottles.add(mtyBot); 
		  }
		  
		  if (numFull > 0 && dispBottle == null) { // If no bottle in dispenser, put one in
			  //put the top full into dispenser
			  fullBottles.remove(0); 
			  dispBottle = new Bottle(100); 
			  numFull = fullBottles.size(); 
		  } 
		  else if (numFull > 0 && dispBottle.getOunces() < 10) {
			  //replace dispenser bottle if less than 10 onces in it
			  ReplaceBottle(); 
		  }
	  }
	  
	  // Replace the bottle in dispenser
	  public void ReplaceBottle() {
		  emtyBottles.add(dispBottle); 
		  fullBottles.remove(0); 
		  dispBottle = new Bottle(100); 
		  numFull = fullBottles.size(); 
		  numEmpty = emtyBottles.size();
	  }
	  
	  // Call the technician to replace the bottles
	  public void Replenish() {
			ui.technicianLabel.setVisible(true);
			ui.startBottDelTimer();
			ui.repaint();
	  }
	  
	  // Give notice that a leak was detected and a technician is on his way
	  public void Alarm() { 
		    ui.leakLabel.setVisible(true);
			ui.setTechLeakDetected();
			ui.startBottDelTimer();
	  }
	  
	  public void dispense(int input) { 
		  dispBottle.dispense(input); 
	  }

	  public int getNumFull() { 
		  return numFull;
	  }
	  
	  public int getNumEmpty() { 
		  return numEmpty; 
	  }
	  
	  public int getDispBFill() { 
		  return dispBottle.getOunces(); 
	  }
	  
	  // Check if there are 3 empty bottles, if yes, call technician
	  // Also check if dispenser bottle is empty, if yes, replace it
	  public void checkBottlesAndFill() {
	    	System.out.println("Number of empty bottles is: " + getNumEmpty());
	    	if (getNumEmpty() == 3) {
	    		ui.generateRandNum();
	    		Replenish();
	    	}
	    	// If dispenser bottle empty, replace it
	    	if (getDispBFill() == 0) {
	    		// This timer below triggers the actionPerformed fct in UserInterface
				ui.startBottChangeTimer(); 
	    		//RobotArm robot = new RobotArm(ui);
			}
	  }
	  
	// Simulate a sensor on the floor of the ThirstAid appliance that check for moisture
	  public void checkLeaks() {
	    	// Create a random # for the leak
	    	Random rand = new Random();
			int randInt = rand.nextInt(9); //Assign the Random Double value in randomnum variable
			//System.out.println("Random Leak value: " + randInt);
			if (randInt == 0 && ui.getTechLeakDetected() == false) {
				System.out.println("Leak Detected!");
				Alarm();
			}
			//else nothing happens
	  }
}
