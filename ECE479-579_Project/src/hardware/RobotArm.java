package hardware;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.UserInterface;

public class RobotArm implements ActionListener {
	
	private Bottle stateOfBottles[] = new Bottle[5];
	private int emptyBottleCounter;
	
	private int stateInt;
	private boolean handEmpty;
	private int armPosition;
	private int vacantPosition;
	private int nextVacantPosition;
	private boolean isIdle;
	private boolean holding;
	private String contentsOfHolding;
	private Bottle tempBottle;
	private UserInterface ui;
	//private String nextOperation;
	
	// constructor
	public RobotArm(){
		this(new UserInterface());
	}
	
	public RobotArm(UserInterface ui) {
		this.ui = ui;
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
		stateInt = 0;
	}
	
	// resets the robot arm to initial state 1 (4 full bottles)
	public void technicianRestock(Bottle inUseBottle) {
		// basically resets to initial state
		stateOfBottles[0] = inUseBottle; // except this one may already be partially used
		stateOfBottles[1] = new Bottle(100);
		stateOfBottles[2] = new Bottle(100);
		stateOfBottles[3] = new Bottle(100);
		stateOfBottles[4] = new Bottle(-1);
		
		emptyBottleCounter = 0;
	}
	
	public void callTechnician3() {
		// TODO
	}
	
	public void resetStateInt() {
		stateInt = 0;
	}
	
	// call this when the current bottle runs empty, replace it and rotate all the bottles accordingly
	public void rotateBottles() {
		if (stateInt == 0) {
			ui.stateLabel.setText("Next Operation: PickupBottle(0)");
			stateInt++;
			//ui.stateUpdateTimer.stop();
		}
		else if (stateInt == 1) {
			ui.stateLabel.setText("Next Operation: MoveArm(4)");
			stateInt++;
		}
		else if (stateInt == 2) {
			ui.stateLabel.setText("Next Operation: PlaceBottle(4)");
			stateInt++;
		}
		else if (stateInt == 3) {
			ui.stateLabel.setText("Next Operation: MoveArm(1)");
			stateInt++;
		}
		else if (stateInt == 4) {
			ui.stateLabel.setText("Next Operation: PickupBottle(1)");
			stateInt++;
		}
		else if (stateInt == 5) {
			ui.stateLabel.setText("Next Operation: MoveArm(0)");
			stateInt++;
		}
		else if (stateInt == 6) {
			ui.stateLabel.setText("Next Operation: PlaceBottle(0)");
			stateInt++;
		}
		else if (stateInt == 7) {
			ui.stateLabel.setText("Next Operation: MoveArm(2)");
			stateInt++;
		}
		else if (stateInt == 8) {
			ui.stateLabel.setText("Next Operation: PickupBottle(2)");
			stateInt++;
		}
		else if (stateInt == 9) {
			ui.stateLabel.setText("Next Operation: MoveArm(1)");
			stateInt++;
		}
		else if (stateInt == 10) {
			ui.stateLabel.setText("Next Operation: PlaceBottle(1)");
			stateInt++;
		}
		else if (stateInt == 11) {
			ui.stateLabel.setText("Next Operation: MoveArm(3)");
			stateInt++;
		}
		else if (stateInt == 12) {
			ui.stateLabel.setText("Next Operation: PickupBottle(3)");
			stateInt++;
		}
		else if (stateInt == 13) {
			ui.stateLabel.setText("Next Operation: MoveArm(2)");
			stateInt++;
		}
		else if (stateInt == 14) {
			ui.stateLabel.setText("Next Operation: PlaceBottle(2)");
			stateInt++;
		}
		else if (stateInt == 15) {
			ui.stateLabel.setText("Next Operation: MoveArm(4)");
			stateInt++;
		}
		else if (stateInt == 16) {
			ui.stateLabel.setText("Next Operation: PickupBottle(4)");
			stateInt++;
		}
		else if (stateInt == 17) {
			ui.stateLabel.setText("Next Operation: MoveArm(3)");
			stateInt++;
		}
		else if (stateInt == 18) {
			ui.stateLabel.setText("Next Operation: PlaceBottle(3)");
			stateInt++;
		}
		else if (stateInt == 19) {
			ui.stateLabel.setText("Go idle");
			stateInt++;
		}
		else {
			ui.doneRotating();
		}
		
//		if (isIdle) {
//			isIdle = false;
//			while (!isIdle) {
//				moveArm(0);
//				if (vacantPosition != 0 && handEmpty && armPosition == 0) {
//					pickupBottle(0);
//				}
//				moveArm(4);
//				if (holding && vacantPosition == 4) {
//					placeBottle(4);
//				}
//				moveArm(1);
//				if (vacantPosition != 1 && handEmpty && armPosition == 1) {
//					pickupBottle(1);
//				}
//				moveArm(0);
//				if (holding && vacantPosition == 0) {
//					placeBottle(0); // this is where install() would happen
//				}
//				moveArm(2);
//				if (vacantPosition != 2 && handEmpty && armPosition == 2) {
//					pickupBottle(2);
//				}
//				moveArm(1);
//				if (holding && vacantPosition == 1) {
//					placeBottle(1);
//				}
//				moveArm(3);
//				if (vacantPosition != 3 && handEmpty && armPosition == 3) {
//					pickupBottle(3);
//				}
//				moveArm(2);
//				if (holding && vacantPosition == 2) {
//					placeBottle(2);
//				}
//				moveArm(4);
//				if (vacantPosition != 4 && handEmpty && armPosition == 4) {
//					pickupBottle(4);
//				}
//				moveArm(3);
//				if (holding && vacantPosition == 3) {
//					placeBottle(3);
//				}
//				isIdle = true;
//			}
//		}
//		armPosition = 99; // reset to idle position
//		stateOfBottles[4] = new Bottle(-1); // reset to proper condition
//		emptyBottleCounter ++;
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
		tempBottle = stateOfBottles[x];
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
		stateOfBottles[x]= tempBottle; 
		//stateOfBottles[x].setStatus(contentsOfHolding);
		
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		ui.stateLabel.setVisible(true);
		rotateBottles();
	}
	
	// local testing
	public static void main(String args[]) {
		RobotArm r1 = new RobotArm();
		int days = 1;
		while(days < 11) { // 10 days
			System.out.println("**********");
			System.out.println("Day " + days);
			System.out.println("**********");
			for (int i = 0; i < 10; i++) { // 10 uses per day
				r1.stateOfBottles[0].randomDispense();
				System.out.println(" - ounces left: " + r1.stateOfBottles[0].getOunces());
				if (r1.stateOfBottles[0].getIsEmpty()) {
					r1.rotateBottles();
					System.out.println("Rotating...");
					if (r1.emptyBottleCounter >= 2 && r1.stateOfBottles[0].getIsLessThanQuarterFull()) {
						r1.callTechnician3();
					}
				}
			}
			days++;
		}
	}
}
