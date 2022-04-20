package hardware;

public class RobotArm {
	
	private String stateOfBottles[] = new String[5];
	private int emptyBottleCounter;
	
	// constructor
	RobotArm(){
		// initial state
		stateOfBottles[0] = "full";
		stateOfBottles[1] = "full";
		stateOfBottles[2] = "full";
		stateOfBottles[3] = "full";
		stateOfBottles[4] = "null";
		
		emptyBottleCounter = 0;
	}
	
	// resets the robot arm to initial state 1 (4 full bottles)
	// TODO: do we need one each for 2/3 bottles?
	public void technicianRestock() {
		// basically resets to initial state
		stateOfBottles[0] = "full"; // except this one may already be partially used
		stateOfBottles[1] = "full";
		stateOfBottles[2] = "full";
		stateOfBottles[3] = "full";
		stateOfBottles[4] = "null";
		
		emptyBottleCounter = 0;
	}
	
	// call this when the current bottle runs empty, replace it and rotate all the bottles accordingly
	public void rotateBottles() {
		
		// [0] should be "empty" at the start of this rotation
		stateOfBottles[0] = "empty";
		
		// [4] should always be "null" in any initial state for this function, so we can overwrite it first
		stateOfBottles[4] = stateOfBottles[0];
		stateOfBottles[0] = stateOfBottles[1];
		stateOfBottles[1] = stateOfBottles[2];
		stateOfBottles[2] = stateOfBottles[3];
		stateOfBottles[3] = stateOfBottles[4]; // which has been overwritten with empty [0] already
											  // so [3] should be empty now
		stateOfBottles[4] = "null";
		
		emptyBottleCounter ++;
	}
	
	public void printStateArray() {
		System.out.println("***************");
		for (int i = 0; i < 5; i++) {
			System.out.println("[" + i + "]" + stateOfBottles[i]);
		}
		System.out.println("***************");
	}
	
	// local testing
//	public static void main(String args[]) {
//		RobotArm r1 = new RobotArm();
//		System.out.println("start");
//		r1.printStateArray();
//		System.out.println(r1.emptyBottleCounter);
//		System.out.println("1 rotate");
//		r1.rotateBottles();
//		r1.printStateArray();
//		System.out.println(r1.emptyBottleCounter);
//		System.out.println("2 rotate");
//		r1.rotateBottles();
//		r1.printStateArray();
//		System.out.println(r1.emptyBottleCounter);
//		System.out.println("3 rotate");
//		r1.rotateBottles();
//		r1.printStateArray();
//		System.out.println(r1.emptyBottleCounter);
//		r1.technicianRestock();
//		r1.printStateArray();
//		System.out.println(r1.emptyBottleCounter);
//		Bottle b2 = new Bottle();
//		
//	}
}
