package hardware;

import java.util.Random;

public class Bottle {
	// max volume is 6 gallons = 768 ounces
	private int ouncesRemaining;
	private boolean isEmpty;
	private boolean lessThanQuarterFull;
	private String status;
    
	// constructor
	public Bottle() {
		this(100);
	}
	
	// constructor
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
		
	// getter for how many ounces left in bottle
	public int getOunces() {
		return ouncesRemaining;
	}
	
	public boolean getIsEmpty() {
		return isEmpty;
	}
	
	public boolean getIsLessThanQuarterFull() {
		return lessThanQuarterFull;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String newStatus) {
		this.status = newStatus;
	}
	
	// calculate percentage fullness of bottle
	public int howFull() {
		double percentFull = (ouncesRemaining / 768.0);
		percentFull = percentFull * 100.0;
		int integerPercentage = (int) percentFull; // will floor down to nearest int value
		
		if (integerPercentage == 0 && getOunces() > 0) {
			integerPercentage = 1; // display 1% if there is a little bit of water left
		}
		return integerPercentage;
	}
	
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
			// TODO if = 0, isEmpty = true, replace bottle
		}
	}
	
	public void randomDispense() {
		Random x = new Random();
		int randomVolume = x.nextInt(48) + 20; // 48 can change, just seemed like a reasonable cap per dispense use
		System.out.println("Dispensing ounces: " + randomVolume);
		dispense(randomVolume);
	}
	
    public void printHowFull() {
    	System.out.println("Bottle is " + howFull() + "% full");
    }
    
    // local testing
    public static void main(String args[]) {
    	System.out.println("Hello");
    	Bottle b1 = new Bottle();
    	System.out.println("ounces in b1: " + b1.getOunces());
    	System.out.println("Status: " + b1.status);
    	b1.dispense(100);
    	System.out.println("ounces in b1: " + b1.getOunces());
    	b1.printHowFull();
    	b1.dispense(968);
    	System.out.println("ounces in b1: " + b1.getOunces());
    	b1.printHowFull();
    	Bottle b2 = new Bottle(-1);
    	System.out.println("Status of b2: " + b2.status);
    	
    }
}
