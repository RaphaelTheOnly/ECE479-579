package software;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.swing.*;

import gui.UserInterface;
import hardware.*;

/*
 * The System class controls all primary behavior related to the AI.
 */
public class AISystem extends JFrame  {
	
	private Bottle dispBottle;
	private int dispBFill;
	private ArrayList<Bottle> fullBottles;
	private ArrayList<Bottle> emtyBottles;
	private int numFull;
	private int numEmpty;
	
    public AISystem() {
    	initSystem();
    	
    	add(new UserInterface(this));

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
    	
    	
    	//do a seach of how many bottle are full and how many empty
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
		System.out.println("in replace");
		emtyBottles.add(dispBottle);
		fullBottles.remove(0);
		dispBottle = new Bottle(100);
		numFull = fullBottles.size();
		numEmpty = emtyBottles.size();
		
	}
	public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
        	AISystem ex = new AISystem();
            ex.setVisible(true);
        });
        
        //Timer timer = new Timer();
    	//timer.schedule(new Difficulty(), 0, 5000);
		
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
		System.out.println("in changeBottles()");
		replaceDispB();
	}
}
