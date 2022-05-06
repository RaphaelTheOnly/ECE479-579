package hardware;

import java.util.Random;

public class Bottle {
	private int ouncesRemaining; // max volume is 6 gallons = 768 ounces
	private boolean isEmpty;
	private boolean lessThanQuarterFull;
	private String status;
    
	// Default constructor
	public Bottle() {
		this(100);
	}
	
	// Parameterized constructor
	public Bottle(int percentFull) {
		if (percentFull == -1) { // initiate a bottle with -1 to establish the "null bottle"
			status = "vacant";
		}
		else {
			ouncesRemaining = 768 * percentFull / 100;
			if (ouncesRemaining < 192) {
				lessThanQuarterFull = true;
			}
			else lessThanQuarterFull = false;
			if (ouncesRemaining > 0) {
				isEmpty = false;
				status = "full";
			}
			else {
				isEmpty = true;
				status = "empty";
			}
		}
	}
		
	// Getter for how many ounces left in bottle
	public int getOunces() {
		return ouncesRemaining;
	}
	
	// Getter for if the bottle is empty
	public boolean getIsEmpty() {
		return isEmpty;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String newStatus) {
		this.status = newStatus;
	}
	
	// Function to calculate the percentage-fullness of the bottle
	public int howFull() {
		double percentFull = (ouncesRemaining / 768.0);
		percentFull = percentFull * 100.0;
		int integerPercentage = (int) percentFull; // will floor down to nearest int value
		
		if (integerPercentage == 0 && getOunces() > 0) {
			integerPercentage = 1; // display 1% if there is a little bit of water left
		}
		return integerPercentage;
	}
	
	// Function to dispense a certain amount of water from the bottle
	public void dispense(int vol) {
		// TODO if dispensing more than ouncesRemaining, limit the dispense 
		ouncesRemaining -= vol;
		status = "in use";
		if (ouncesRemaining < 192) {
			lessThanQuarterFull = true;
		}
		if (ouncesRemaining <= 0) {
			ouncesRemaining = 0;
			isEmpty = true;
			status = "empty";
		}
	}
}
