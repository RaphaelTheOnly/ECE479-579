package hardware;

public class Bottle {
	// max volume is 6 gallons = 768 ounces
	private int ouncesRemaining;
	private boolean isEmpty;
    
	// constructor
	Bottle() {
		ouncesRemaining = 768;
		isEmpty = false;
	}
	
	// getter for how many ounces left in bottle
	public int getOunces() {
		return ouncesRemaining;
	}
	
	// calculate percentage fullness of bottle
	public int howFull() {
		double percentFull = (ouncesRemaining / 768.0);
		percentFull = percentFull * 100.0;
		int integerPercentage = (int) percentFull; // will floor down to nearest int value
		
		if (integerPercentage == 0 && getOunces() > 0) {
			integerPercentage = 1; // display 1% if there is a little bit of water left
		}
		
		// TODO if = 0, isEmpty = true, replace bottle
		
		return integerPercentage;
	}
	
	public void dispense(int vol) {
		ouncesRemaining -= vol;
		if (ouncesRemaining <= 0) {
			ouncesRemaining = 0;
			isEmpty = true;
		}
	}
	
    public void printHowFull() {
    	System.out.println("Bottle is " + howFull() + "% full");
    }
    
    // local testing
//    public static void main(String args[]) {
//    	System.out.println("Hello");
//    	Bottle b1 = new Bottle();
//    	System.out.println("ounces in b1: " + b1.getOunces());
//    	b1.dispense(100);
//    	System.out.println("ounces in b1: " + b1.getOunces());
//    	b1.printHowFull();
//    	b1.dispense(968);
//    	System.out.println("ounces in b1: " + b1.getOunces());
//    	b1.printHowFull();
//    	
//    }
}
