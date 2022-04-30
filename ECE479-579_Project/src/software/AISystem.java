package software;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

import gui.UserInterface;
import hardware.*;
import people.Technician;
	
/*
 * The System class controls all primary behavior related to the AI.
 */
public class AISystem extends JFrame {
	  private UserInterface ui;
	  private Bottle dispBottle; 
	  public Technician technician;
	  private ArrayList<Bottle> fullBottles; 
	  private ArrayList<Bottle> emtyBottles;
	  private int numFull; 
	  private int numEmpty;
	  public int randNum;
	  
	  
	  public AISystem() { 
		  
		  randNum = 5; 
		  initSystem();
		  ui = new UserInterface(this);
		  technician = new Technician(ui);
		  
		  add(ui);
		  
		  setTitle("ThirstAI"); 
		  setSize(1037, 850);
		  
		  setLocationRelativeTo(null); 
		  setResizable(false);
		  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  
	  }
	  
	  public void initSystem() {
		  
		  //check if there's a bottle in dispenser and its fill 
		  boolean dispExist = false; 
		  if (dispExist == true) { 
			  int dispFill = 80; 
			  dispBottle = new Bottle(dispFill); 
		  } 
		  else dispBottle = null;
		  
		  
		  //do a search of how many bottle are full and how many empty 
		  //for now: 3 full, 2 empty 
		  numFull = 4; 
		  fullBottles = new ArrayList<Bottle>(); 
		  for (int i = 0; i < numFull; i++) { 
			  Bottle fullBot = new Bottle(100);
			  fullBottles.add(fullBot); 
		  }
		  
		  numEmpty = 0; 
		  emtyBottles = new ArrayList<Bottle>(); 
		  for (int i = 0; i < numEmpty; i++) { 
			  Bottle mtyBot = new Bottle(0); 
			  emtyBottles.add(mtyBot); 
		  }
		  
		  if (numFull > 0 && dispBottle == null) { 
			  //put the top full into dispenser
			  fullBottles.remove(0); 
			  dispBottle = new Bottle(100); 
			  numFull = fullBottles.size(); 
		  } 
		  else if (numFull > 0 && dispBottle.getOunces() < 10) {
			  //replace dispBottle 
			  replaceDispB(); 
		  } 
		  //else nothing changes
	  }
	  
	  private void replaceDispB() { 
		  //System.out.println("in replace");
		  emtyBottles.add(dispBottle); 
		  fullBottles.remove(0); 
		  dispBottle = new Bottle(100); 
		  numFull = fullBottles.size(); 
		  numEmpty = emtyBottles.size();
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
	  
	  public void dispense(int input) { 
		  dispBottle.dispense(input); 
	  }
	  
	  public void changeBottles() { 
		  //System.out.println("in changeBottles()");	  
		  replaceDispB(); 
	  }
	  
	  public void checkFillNBottles() {
	    	System.out.println("Empty bottles are: " + getNumEmpty());
	    	if (getNumEmpty() == 3) {
	    		System.out.println("in 3 empty");
	    		ui.technLabel.setVisible(true);
	    		ui.startBottDelTimer();
	    		
	    		// Create a random # for the other households
	    		Random rand = new Random();
	    		randNum = rand.nextInt(4); //Assign the Random Double value in randomnum variable
	    		System.out.println("Random Int value: "+ randNum);
	    		ui.repaint();
	    	}
	    	
	    	if (getDispBFill() == 0) {
				//System.out.println("DispFill == 0: " + getDispBFill());
				ui.startBottChangeTimer();
			}
	    }
	  
	  public void checkRandNum4Houses() {
		//System.out.println("b4 if's for rand, randomNum = " +randNum);
			if (randNum == 0) {
				ui.secHouseLabel.setText("<html>Second House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.thiHouseLabel.setText("<html>Third House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.fouHouseLabel.setText("<html>Fourth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.fifHouseLabel.setText("<html>Fifth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
			}
			else if (randNum == 1) {
				ui.secHouseLabel.setText("<html>Second House:<br>&nbsp &nbsp &nbsp &nbsp <font color=green> good </font> </html>");
				ui.thiHouseLabel.setText("<html>Third House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.fouHouseLabel.setText("<html>Fourth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.fifHouseLabel.setText("<html>Fifth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
			}
			else if (randNum == 2) {
				ui.secHouseLabel.setText("<html>Second House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.thiHouseLabel.setText("<html>Third House:<br>&nbsp &nbsp &nbsp &nbsp <font color=green> good </font> </html>");
				ui.fouHouseLabel.setText("<html>Fourth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.fifHouseLabel.setText("<html>Fifth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
			}
			else if (randNum == 3) {
				ui.secHouseLabel.setText("<html>Second House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.thiHouseLabel.setText("<html>Third House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
				ui.fouHouseLabel.setText("<html>Fourth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=green> good </font> </html>");
				ui.fifHouseLabel.setText("<html>Fifth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=red> need </font> </html>");
			}
			else {
				ui.secHouseLabel.setText("<html>Second House:<br>&nbsp &nbsp &nbsp &nbsp <font color=green> good </font> </html>");
				ui.thiHouseLabel.setText("<html>Third House:<br>&nbsp &nbsp &nbsp &nbsp <font color=green> good </font> </html>");
				ui.fouHouseLabel.setText("<html>Fourth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=green> good </font> </html>");
				ui.fifHouseLabel.setText("<html>Fifth House:<br>&nbsp &nbsp &nbsp &nbsp <font color=green> good </font> </html>");
			}
			
			if (getNumFull() > 1) {
				ui.technLabel.setVisible(false);
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
