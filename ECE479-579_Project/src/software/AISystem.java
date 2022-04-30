package software;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

import gui.UserInterface;
import hardware.*;
	
/*
 * The System class controls all primary behavior related to the AI.
 */
public class AISystem extends JFrame {
	  private UserInterface ui; // Instance of the UserInterface that the AISystem can refer to
	  private Bottle dispBottle; // Bottle in the dispenser, so the one currently used
	  private ArrayList<Bottle> fullBottles; // Indexable list of full bottles
	  private ArrayList<Bottle> emtyBottles; // Indexable list of empty bottles
	  private int numFull; // Number of full bottles in our unit
	  private int numEmpty; // Number of empty bottles in our unit
	  public int randNum; // Random integer Used to decide which house needs delivery
	  
	  // Default Constructor
	  public AISystem() { 
		  randNum = 5; // 5 is not one of the generated random numbers
		  initSystem(); // a function
		  ui = new UserInterface(this); // Create instance of UserInterface

		  add(ui); // UserInterface is a JPanel and it gets added here to the AISystem's JFrame
		  
		  setTitle("ThirstAI"); // Title of the GUI window
		  setSize(1037, 850); // Width, Height of window
		  
		  setLocationRelativeTo(null); 
		  setResizable(false);
		  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  
	  }
	  
	  // To Be Replaced by a function that gets called from the Traveling Salesman
	  // So far this class is instantiating the bottles
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
			  replaceDispB(); 
		  } 
		  //else nothing changes
	  }
	  
	  // Replace the bottle in dispenser
	  private void replaceDispB() {
		  emtyBottles.add(dispBottle); 
		  fullBottles.remove(0); 
		  dispBottle = new Bottle(100); 
		  numFull = fullBottles.size(); 
		  numEmpty = emtyBottles.size();
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
	  
	  public void changeBottles() {   
		  replaceDispB(); 
	  }
	  
	  // Check if there are 3 empty bottles, if yes, call technician
	  // Also check if dispenser bottle is empty, if yes, replace it
	  public void checkBottlesAndFill() {
	    	System.out.println("Number of empty bottles is: " + getNumEmpty());
	    	if (getNumEmpty() == 3) {
	    		callTechnician();
	    	}
	    	
	    	// If dispenser bottle empty, replace it
	    	if (getDispBFill() == 0) {
	    		// This timer below triggers the actionPerformed fct in UserInterface
				ui.startBottChangeTimer(); 
			}
	  }
	  
	  // Call the technician to replace the bottles
	  public void callTechnician() {
			ui.technicianLabel.setVisible(true); // Make the technician label visible
			ui.startBottDelTimer(); // Start the technician timer (~2 sec)
			
			// Create a random number to simulate the other households
			Random rand = new Random();
			randNum = rand.nextInt(4); // Next integer between 0 and 3 I think
			System.out.println("Random Int value: "+ randNum);
			ui.repaint(); // Refresh all the GUI elements to make sure they are up to date
	  }
	  
	  // Simulate the other houses needing bottles delivered
	  // This function gets called from UserInterface every time the GUI is "repainted"
	  public void checkRandNum4Houses() {
		  // If the random integer from above is 0, then all need delivery. Other if-clauses leave out one of them.
			if (randNum == 0) {
				ui.secHouseLabel.setText("<html>Second House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.thirdHouseLabel.setText("<html>Third House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.fourthHouseLabel.setText("<html>Fourth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.fifthHouseLabel.setText("<html>Fifth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
			}
			else if (randNum == 1) {
				ui.secHouseLabel.setText("<html>Second House:<br>&nbsp &nbsp &nbsp &nbsp <font color=green> good </font> </html>");
				ui.thirdHouseLabel.setText("<html>Third House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.fourthHouseLabel.setText("<html>Fourth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.fifthHouseLabel.setText("<html>Fifth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
			}
			else if (randNum == 2) {
				ui.secHouseLabel.setText("<html>Second House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.thirdHouseLabel.setText("<html>Third House:<br>&nbsp &nbsp &nbsp &nbsp <font color=green> good </font> </html>");
				ui.fourthHouseLabel.setText("<html>Fourth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.fifthHouseLabel.setText("<html>Fifth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
			}
			else if (randNum == 3) {
				ui.secHouseLabel.setText("<html>Second House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.thirdHouseLabel.setText("<html>Third House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.fourthHouseLabel.setText("<html>Fourth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=green> good </font> </html>");
				ui.fifthHouseLabel.setText("<html>Fifth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
			}
			else {
				ui.secHouseLabel.setText("<html>Second House:<br>&nbsp &nbsp &nbsp &nbsp <font color=green> good </font> </html>");
				ui.thirdHouseLabel.setText("<html>Third House:<br>&nbsp &nbsp &nbsp &nbsp <font color=green> good </font> </html>");
				ui.fourthHouseLabel.setText("<html>Fourth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=green> good </font> </html>");
				ui.fifthHouseLabel.setText("<html>Fifth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=green> good </font> </html>");
			}
	  }
	  
	  public void checkLeaks() {
	    	// Simulate a sensor on the floor of the ThirstAid appliance that check for moisture
	    	// Create a random # for the leak
	    	Random rand = new Random();
			int randInt = rand.nextInt(9); //Assign the Random Double value in randomnum variable
			System.out.println("Random Leak value: " + randInt);
			if (randInt == 0 && ui.getTechLeakDetected() == false) {
				System.out.println("Leak Detected!");
				ui.leakLabel.setVisible(true);
				ui.setTechLeakDetected();
				ui.startBottDelTimer();
			}
			//else nothing happens
	    }
}
