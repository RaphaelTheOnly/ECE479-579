package hardware;

public class RobotArm {
	
	private Bottle stateOfBottles[] = new Bottle[5];
	private int emptyBottleCounter;
	
	private boolean handEmpty;
	private int armPosition;
	private int vacantPosition;
	private int nextVacantPosition;
	private boolean isIdle;
	private boolean holding;
	private String contentsOfHolding;
	
	// constructor
	RobotArm(){
		// initial state
		stateOfBottles[0] = new Bottle(100);
		stateOfBottles[1] = new Bottle(100);
		stateOfBottles[2] = new Bottle(100);
		stateOfBottles[3] = new Bottle(100);
		stateOfBottles[4] = new Bottle(-1);
		
		emptyBottleCounter = 0;
		handEmpty = true;
		armPosition = 99; // idle position
		vacantPosition = 4;
		nextVacantPosition = 99; // intentionally set out of range
		isIdle = true;
		holding = false;
		contentsOfHolding = "";
	}
	
	// resets the robot arm to initial state 1 (4 full bottles)
	// TODO: do we need one each for 2/3 bottles?
	public void technicianRestock() {
		// basically resets to initial state
		stateOfBottles[0] = new Bottle(100); // except this one may already be partially used
		stateOfBottles[1] = new Bottle(100);
		stateOfBottles[2] = new Bottle(100);
		stateOfBottles[3] = new Bottle(100);
		stateOfBottles[4] = new Bottle(-1);
		
		emptyBottleCounter = 0;
	}
	
//	// call this when the current bottle runs empty, replace it and rotate all the bottles accordingly
//	public void rotateBottlesOld() {
//		
//		// [0] should be "empty" at the start of this rotation
//		stateOfBottles[0] = "empty";
//		
//		// [4] should always be "null" in any initial state for this function, so we can overwrite it first
//		stateOfBottles[4] = stateOfBottles[0];
//		stateOfBottles[0] = stateOfBottles[1];
//		stateOfBottles[1] = stateOfBottles[2];
//		stateOfBottles[2] = stateOfBottles[3];
//		stateOfBottles[3] = stateOfBottles[4]; // which has been overwritten with empty [0] already
//											  // so [3] should be empty now
//		stateOfBottles[4] = "vacant";
//		
//		emptyBottleCounter ++;
//	}
	
	public void rotateBottles() {
		
		stateOfBottles[0].setStatus("empty");
		
		if (isIdle) {
			isIdle = false;
			while (!isIdle) {
				moveArm(0);
				if (vacantPosition != 0 && handEmpty && armPosition == 0) {
					pickupBottle(0);
				}
				moveArm(4);
				if (holding && vacantPosition == 4) {
					placeBottle(4);
				}
				moveArm(1);
				if (vacantPosition != 1 && handEmpty && armPosition == 1) {
					pickupBottle(1);
				}
				moveArm(0);
				if (holding && vacantPosition == 0) {
					placeBottle(0); // this is where install() would happen
				}
				moveArm(2);
				if (vacantPosition != 2 && handEmpty && armPosition == 2) {
					pickupBottle(2);
				}
				moveArm(1);
				if (holding && vacantPosition == 1) {
					placeBottle(1);
				}
				moveArm(3);
				if (vacantPosition != 3 && handEmpty && armPosition == 3) {
					pickupBottle(3);
				}
				moveArm(2);
				if (holding && vacantPosition == 2) {
					placeBottle(2);
				}
				moveArm(4);
				if (vacantPosition != 4 && handEmpty && armPosition == 4) {
					pickupBottle(4);
				}
				moveArm(3);
				if (holding && vacantPosition == 3) {
					placeBottle(3);
				}
				isIdle = true;
			}
		}
		armPosition = 99; // reset to idle position
		stateOfBottles[4] = new Bottle(-1); // reset to proper condition
		emptyBottleCounter ++;
	}
	
	// move arm TO position x [0 - 4]
	// Block world logic:
	// P & D: can be called for all states
	// A: armPosition(x)
	public void moveArm(int x) {
		armPosition = x;
	}
	
	// Block world logic:
	// P & D: ~vacant(x) && handEmpty && armPosition(x)
	// A: holding, contentsOfHolding(x), vacant(x)
	public void pickupBottle(int x) {
		// add list
		holding = true;
		contentsOfHolding = stateOfBottles[x].getStatus();
		nextVacantPosition = x; // don't want to overwrite the vacant position until bottle is placed.
								// - while bottle is held, there are 2 vacant positions.
		// delete list
		handEmpty = false;
	}
	
	// Block world logic:
	// P & D: holding && vacant(x)
	// A: handEmpty, contentsAssigned(x)
	public void placeBottle(int x) {
		//add list
		handEmpty = true;
		stateOfBottles[x].setStatus(contentsOfHolding);
		
		// delete list
		vacantPosition = nextVacantPosition;
		nextVacantPosition = 99; // reset to out of range
		holding = false;
		contentsOfHolding = "";
	}
	
	public void printStateArray() {
		System.out.println("***************");
		for (int i = 0; i < 5; i++) {
			System.out.println("[" + i + "]" + stateOfBottles[i].getStatus());
		}
		System.out.println("***************");
	}
	
	// local testing
	public static void main(String args[]) {
		RobotArm r1 = new RobotArm();
		System.out.println("start");
		r1.printStateArray();
		System.out.println(r1.emptyBottleCounter);
		System.out.println("1 rotate");
		r1.rotateBottles();
		r1.printStateArray();
		System.out.println(r1.emptyBottleCounter);
		System.out.println("2 rotate");
		r1.rotateBottles();
		r1.printStateArray();
		System.out.println(r1.emptyBottleCounter);
		System.out.println("3 rotate");
		r1.rotateBottles();
		r1.printStateArray();
		System.out.println(r1.emptyBottleCounter);
		r1.technicianRestock();
		r1.printStateArray();
		System.out.println(r1.emptyBottleCounter);
		System.out.println("contents of hold: " + r1.contentsOfHolding);
		//Bottle b2 = new Bottle();
		
	}
}
